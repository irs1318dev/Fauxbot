package frc.lib.helpers;

/**
 * Helper class to provide linear interpolation.
 * Basically, given a 1-dimensional space (e.g. points along an axis) with samples taken at various points, calculates the weighted average of any location between those samples
 * See: https://en.wikipedia.org/wiki/Linear_interpolation
 */
public class LinearInterpolator
{
    private final double[] samplePoints;
    private final double[] samples;

    /**
     * Initializes a new instance of the LinearInterpolator class.
     * @param samplePoints array of locations where samples were taken
     * @param samples array of samples taken at the provided locations
     */
    public LinearInterpolator(double[] samplePoints, double[] samples)
    {
        ExceptionHelpers.Assert(samplePoints != null && samples.length != 0, "samplePoints cannot be null or empty!");
        ExceptionHelpers.Assert(samples != null, "samples cannot be null!");
        ExceptionHelpers.Assert(samples.length == samplePoints.length, "samples length (%d) doesn't match sample points length (%d)", samples.length, samplePoints.length);

        this.samplePoints = samplePoints;
        this.samples = samples;
    }

    /**
     * Retrieve the best sample for the given position/location
     * @param value position/location
     * @return value using linear interpolation of the samples closest to value
     */
    public double sample(double value)
    {
        int idx1 = 0;
        int idx2 = -1;
        for (int i = 1; i < this.samplePoints.length; i++)
        {
            if (this.samplePoints[i] > value)
            {
                idx2 = i;
                break;
            }
            else if (this.samplePoints[i] <= value)
            {
                idx1 = i;
            }
        }

        if (idx2 == -1)
        {
            // case: grab the closest sample at the beginnings/ends
            return this.samples[idx1];
        }
        else
        {
            // linear interpolation between idx1 and idx2 (weighted average of the two samples based on the distance from the sample points)
            double samplePoint1 = this.samplePoints[idx1];
            double samplePoint2 = this.samplePoints[idx2];

            double sample1 = this.samples[idx1];
            double sample2 = this.samples[idx2];

            return (1.0 / (samplePoint2 - samplePoint1)) * (sample1 * (samplePoint2 - value) + sample2 * (value - samplePoint1));
        }
    }
}
