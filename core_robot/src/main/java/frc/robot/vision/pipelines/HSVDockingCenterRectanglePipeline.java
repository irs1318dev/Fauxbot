package frc.robot.vision.pipelines;

import frc.robot.common.robotprovider.*;
import frc.robot.vision.*;
import frc.robot.vision.common.*;

import java.util.*;

public class HSVDockingCenterRectanglePipeline implements ICentroidVisionPipeline
{
    private final ITimer timer;
    private final VisionCalculations calc;
    private final IOpenCVProvider openCVProvider;
    private final boolean shouldUndistort;
    private final ImageUndistorter undistorter;
    private final HSVFilter hsvFilter;

    private final IVideoStream frameInput;
    private final IVideoStream hsvOutput;

    // measured values
    private IPoint dockingMarkerCenter;

    // need to be calculated
    private Double measuredAngleX;
    private Double desiredAngleX;
    private Double distanceFromRobot;

    // FPS Measurement
    private long analyzedFrameCount;
    private double lastMeasuredTime;
    private double lastFpsMeasurement;

    // active status
    private volatile VisionProcessingState processingState;
    private volatile boolean streamEnabled;

    /**
     * Initializes a new instance of the HSVDockingCenterRectanglePipeline class.
     * @param timer to use for any timing purposes
     * @param shouldUndistort whether to undistort the image or not
     */
    public HSVDockingCenterRectanglePipeline(
        ITimer timer,
        IRobotProvider provider,
        boolean shouldUndistort)
    {
        this.shouldUndistort = shouldUndistort;

        this.calc = provider.getVisionCalculations();
        this.openCVProvider = provider.getOpenCVProvider();
        this.undistorter = new ImageUndistorter(this.openCVProvider);
        IScalar lowFilter = this.openCVProvider.newScalar(VisionConstants.LIFECAM_HSV_FILTER_LOW_V0, VisionConstants.LIFECAM_HSV_FILTER_LOW_V1, VisionConstants.LIFECAM_HSV_FILTER_LOW_V2);
        IScalar highFilter = this.openCVProvider.newScalar(VisionConstants.LIFECAM_HSV_FILTER_HIGH_V0, VisionConstants.LIFECAM_HSV_FILTER_HIGH_V1, VisionConstants.LIFECAM_HSV_FILTER_HIGH_V2);
        this.hsvFilter = new HSVFilter(this.openCVProvider, lowFilter, highFilter);

        this.dockingMarkerCenter = null;

        this.measuredAngleX = null;
        this.desiredAngleX = null;
        this.distanceFromRobot = null; 

        this.analyzedFrameCount = 0;
        this.timer = timer;
        this.lastMeasuredTime = this.timer.get();

        this.processingState = VisionProcessingState.Disabled;
        this.streamEnabled = true;

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
        if (this.shouldUndistort)
        {
            image = this.undistorter.undistortFrame(image);
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

        List<IRotatedRect> rectangles = findRectangles(image);
        image.release();
        if (VisionConstants.DEBUG &&
            VisionConstants.DEBUG_PRINT_OUTPUT &&
            VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
        {
            for (int i = 0; i < rectangles.size(); i++)
            {
                IRotatedRect rectangle = rectangles.get(i);
                System.out.println("R_" + i + " : " + Arrays.toString(rectangle.getRawValues()));
            }
        }

        List<Set<IRotatedRect>> groupedRects = calc.groupRotatedRect(rectangles);
        if (VisionConstants.DEBUG &&
            VisionConstants.DEBUG_PRINT_OUTPUT &&
            VisionConstants.DEBUG_PRINT_ANALYZER_DATA)
        {
            int setNum = 0;
            for (Set<IRotatedRect> group : groupedRects)
            {
                List<IRotatedRect> groupList = new ArrayList<IRotatedRect>();
                groupList.addAll(group);
                for (int i = 0; i < groupList.size(); i++)
                {
                    IRotatedRect rectangle = groupList.get(i);
                    System.out.println("S_" + setNum + "_R_" + i + " : " + Arrays.toString(rectangle.getRawValues()));
                }

                setNum++;
            }
        }

        Set<IRotatedRect> row = this.calc.pickRow(groupedRects, VisionResult.LOW_TARGET);
        List<RectanglePair> pairs = this.calc.pairRectangles(row);
        RectanglePair pair = this.calc.pickPreferredPair(pairs, VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH, VisionConstants.VISION_CONSIDERATION_DISTANCE_RANGE);

        // Docking Calculations
        if (pair == null)
        {
            this.dockingMarkerCenter = null;
            this.desiredAngleX = null;
            this.measuredAngleX = null;

            this.distanceFromRobot = null;

            return;
        }

        this.dockingMarkerCenter = pair.getCenter();
        this.measuredAngleX = pair.getMeasuredAngleX();
        this.distanceFromRobot = this.calc.getDistanceFromRobot(pair);
        this.desiredAngleX = this.calc.getDesiredAngleX(pair);
    }

    public List<IRotatedRect> findRectangles(IMat image)
    {
        List<IMatOfPoint> contours = ContourHelper.getAllContours(openCVProvider, image, VisionConstants.CONTOUR_MIN_AREA);

        List<IRotatedRect> rotatedRect = new ArrayList<IRotatedRect>(contours.size());
        for (IMatOfPoint contour : contours)
        {
            rotatedRect.add(this.openCVProvider.minAreaRect(this.openCVProvider.convertToMatOfPoints2f(contour)));
            contour.release();
        }

        return rotatedRect;
    }

    public boolean isActive()
    {
        return this.getMode() != VisionProcessingState.Disabled;
    }

    public IPoint getCenter()
    {
        return this.dockingMarkerCenter;
    }

    public Double getDesiredAngleX()
    {
        return this.desiredAngleX;
    }

    public Double getMeasuredAngleX()
    {
        return this.measuredAngleX;
    }

    public Double getRobotDistance()
    {
        return this.distanceFromRobot;
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