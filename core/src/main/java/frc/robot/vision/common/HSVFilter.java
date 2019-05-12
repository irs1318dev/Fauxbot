package frc.robot.vision.common;

import frc.robot.common.robotprovider.*;

public class HSVFilter
{
    private static final int IMGPROC_COLOR_BGR2HSV = 40;

    private final IOpenCVProvider provider;

    private final IScalar lowerBound;
    private final IScalar upperBound;

    /**
     * Initializes a new instance of the HSVFilter class.
     * @param lowerBound of HSV to filter
     * @param upperBound of HSV to filter
     */
    public HSVFilter(IOpenCVProvider provider, IScalar lowerBound, IScalar upperBound)
    {
        this.provider = provider;

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Filter the provided frame for HSVs within the provider bounds.
     * @param frame to filter
     * @return a matrix of 1s and 0s based on whether the pixel is within the provided HSV range or not, respectively.
     */
    public IMat filterHSV(IMat frame)
    {
        IMat sourceBGR = frame.clone();
        provider.cvtColor(sourceBGR, frame, HSVFilter.IMGPROC_COLOR_BGR2HSV);
        sourceBGR.release();

        IMat sourceHSV = frame.clone();
        provider.inRange(sourceHSV, this.lowerBound, this.upperBound, frame);
        sourceHSV.release();

        return frame;
    }
}
