package frc.robot.vision.pipelines;

import frc.robot.common.robotprovider.*;
import frc.robot.vision.VisionConstants;
import frc.robot.vision.common.ContourHelper;
import frc.robot.vision.common.HSVFilter;
import frc.robot.vision.common.ImageUndistorter;
import frc.robot.vision.common.VisionProcessingState;

public class HSVCenterPipeline implements ICentroidVisionPipeline
{
    private final ITimer timer;
    private final IOpenCVProvider openCVProvider;
    private final boolean shouldUndistort;
    private final ImageUndistorter undistorter;
    private final HSVFilter hsvFilter;

    private final IVideoStream frameInput;
    private final IVideoStream hsvOutput;

    // measured values
    private IPoint largestCenter;
    private Double measuredAngleX;

    // FPS Measurement
    private long analyzedFrameCount;
    private double lastMeasuredTime;
    private double lastFpsMeasurement;

    // active status
    private volatile VisionProcessingState processingState;
    private volatile boolean streamEnabled;

    /**
     * Initializes a new instance of the HSVCenterPipeline class.
     * @param timer to use for any timing purposes
     * @param shouldUndistort whether to undistort the image or not
     */
    public HSVCenterPipeline(
        ITimer timer,
        IRobotProvider provider,
        boolean shouldUndistort)
    {
        this.shouldUndistort = shouldUndistort;

        this.openCVProvider = provider.getOpenCVProvider();
        this.undistorter = new ImageUndistorter(this.openCVProvider);
        IScalar lowFilter = this.openCVProvider.newScalar(VisionConstants.LIFECAM_HSV_FILTER_LOW_V0, VisionConstants.LIFECAM_HSV_FILTER_LOW_V1, VisionConstants.LIFECAM_HSV_FILTER_LOW_V2);
        IScalar highFilter = this.openCVProvider.newScalar(VisionConstants.LIFECAM_HSV_FILTER_HIGH_V0, VisionConstants.LIFECAM_HSV_FILTER_HIGH_V1, VisionConstants.LIFECAM_HSV_FILTER_HIGH_V2);
        this.hsvFilter = new HSVFilter(this.openCVProvider, lowFilter, highFilter);

        this.largestCenter = null;
        this.measuredAngleX = null;

        this.analyzedFrameCount = 0;
        this.timer = timer;
        this.lastMeasuredTime = this.timer.get();

        this.processingState = VisionProcessingState.Disabled;

        this.frameInput = provider.getMJPEGStream("center.input", VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);

        if (VisionConstants.DEBUG &&
            VisionConstants.DEBUG_OUTPUT_FRAMES)
        {
            this.hsvOutput =  provider.getMJPEGStream("center.hsv", VisionConstants.LIFECAM_CAMERA_RESOLUTION_X, VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y);
        }
        else
        {
            this.hsvOutput = null;
        }
    }

    /**
     * Process a single image frame
     * @param frame image to analyze
     */
    @Override
    public void process(IMat image)
    {
        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_SAVE_FRAMES &&
                this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
            {
                this.openCVProvider.imwrite(
                    String.format("%simage%d-1.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount),
                    image);
            }
        }

        if (this.getStreamMode() ||
            (VisionConstants.DEBUG && VisionConstants.DEBUG_OUTPUT_FRAMES))
        {
            this.frameInput.putFrame(image);
        }

        if (this.getMode() == VisionProcessingState.Disabled)
        {
            return;
        }

        this.analyzedFrameCount++;
        if (VisionConstants.DEBUG &&
            VisionConstants.DEBUG_PRINT_OUTPUT &&
            this.analyzedFrameCount % VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL == 0)
        {
            double now = this.timer.get();
            double elapsedTime = now - this.lastMeasuredTime;

            this.lastFpsMeasurement = ((double)VisionConstants.DEBUG_FPS_AVERAGING_INTERVAL) / elapsedTime;
            this.lastMeasuredTime = this.timer.get();
        }

        // first, undistort the image.
        IMat undistortedImage;
        if (this.shouldUndistort)
        {
            image = this.undistorter.undistortFrame(image);
        }

        // save the undistorted image for possible output later...
        if (this.shouldUndistort)
        {
            undistortedImage = image.clone();
        }
        else
        {
            undistortedImage = image;
        }

        // second, filter HSV
        image = this.hsvFilter.filterHSV(image);
        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_SAVE_FRAMES &&
                this.analyzedFrameCount % VisionConstants.DEBUG_FRAME_OUTPUT_GAP == 0)
            {
                this.openCVProvider.imwrite(
                    String.format("%simage%d-2.hsvfiltered.jpg", VisionConstants.DEBUG_OUTPUT_FOLDER, this.analyzedFrameCount),
                    image);
            }

            if (VisionConstants.DEBUG_OUTPUT_FRAMES)
            {
                this.hsvOutput.putFrame(image);
            }
        }

        // third, find the largest contour.
        IMatOfPoint largestContour = ContourHelper.findLargestContour(this.openCVProvider, image, VisionConstants.CONTOUR_MIN_AREA);

        if (largestContour == null)
        {
            if (VisionConstants.DEBUG &&
                VisionConstants.DEBUG_PRINT_OUTPUT &&
                VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                System.out.println("could not find any contour");
            }
        }

        // fourth, find the center of mass for the largest two contours
        IPoint largestCenterOfMass = null;
        if (largestContour != null)
        {
            largestCenterOfMass = ContourHelper.findCenterOfMass(this.openCVProvider, largestContour);
            largestContour.release();
        }

        if (VisionConstants.DEBUG)
        {
            if (VisionConstants.DEBUG_PRINT_OUTPUT &&
                VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
            {
                if (largestCenterOfMass == null)
                {
                    System.out.println("couldn't find the center of mass!");
                }
                else
                {
                    System.out.println(String.format("Center of mass: %f, %f", largestCenterOfMass.getX(), largestCenterOfMass.getY()));
                }
            }
        }

        // finally, record the centers of mass
        this.largestCenter = largestCenterOfMass;

        undistortedImage.release();

        if (this.largestCenter != null)
        {
            double xOffsetMeasured = this.largestCenter.getX() - VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH;
            this.measuredAngleX = Math.atan(xOffsetMeasured / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_X) * VisionConstants.RADIANS_TO_ANGLE;
        }
        else
        {
            this.measuredAngleX = null;
        }
    }

    public boolean isActive()
    {
        return this.getMode() != VisionProcessingState.Disabled;
    }

    public IPoint getCenter()
    {
        return this.largestCenter;
    }

    public Double getDesiredAngleX()
    {
        return 0.0;
    }

    public Double getMeasuredAngleX()
    {
        return this.measuredAngleX;
    }

    public Double getRobotDistance()
    {
        return null;
    }

    public double getFps()
    {
        return this.lastFpsMeasurement;
    }

    public void setMode(VisionProcessingState state)
    {
        synchronized (this)
        {
            this.processingState = state;
        }
    }

    public void setStreamMode(boolean isEnabled)
    {
        synchronized (this)
        {
            this.streamEnabled = isEnabled;
        }
    }

    protected boolean getStreamMode()
    {
        synchronized (this)
        {
            return this.streamEnabled;
        }
    }

    protected VisionProcessingState getMode()
    {
        synchronized (this)
        {
            return this.processingState;
        }
    }
}
