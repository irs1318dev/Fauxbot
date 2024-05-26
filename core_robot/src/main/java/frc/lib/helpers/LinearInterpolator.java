package frc.lib.helpers;

public class LinearInterpolator
{
    private final double[] samplePoints;
    private final double[] samples;

    public LinearInterpolator(double[] samplePoints, double[] samples)
    {
        ExceptionHelpers.Assert(samplePoints != null && samples.length != 0, "samplePoints cannot be null or empty!");
        ExceptionHelpers.Assert(samples != null, "samples cannot be null!");
        ExceptionHelpers.Assert(samples.length == samplePoints.length, "samples length (%d) doesn't match sample points length (%d)", samples.length, samplePoints.length);

        this.samplePoints = samplePoints;
        this.samples = samples;
    }

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
