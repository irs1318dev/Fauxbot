package frc.lib.helpers;

/**
 * Helper class to provide bilinear interpolation.
 * Basically, given a 2-dimensional space (e.g. an x/y grid) with samples taken at various points, calculates the weighted average of any location between those samples
 * See: https://en.wikipedia.org/wiki/Bilinear_interpolation
 */
public class BilinearInterpolator
{
    private final double[] xSamplePoints;
    private final double[] ySamplePoints;

    private final double[][] samples;

    /**
     * Initializes a new instance of the BilinearInterpolator class.
     * @param xSamplePoints array of locations where samples were taken along the x axis
     * @param ySamplePoints array of locations where samples were taken along the y axis
     * @param samples 2-dimensional array of samples taken at the provided x and y locations
     */
    public BilinearInterpolator(double[] xSamplePoints, double[] ySamplePoints, double[][] samples)
    {
        ExceptionHelpers.Assert(xSamplePoints != null && xSamplePoints.length != 0, "xSamplePoints cannot be null!");
        ExceptionHelpers.Assert(ySamplePoints != null && ySamplePoints.length != 0, "ySamplePoints cannot be null!");
        ExceptionHelpers.Assert(samples != null, "samples cannot be null!");

        this.xSamplePoints = xSamplePoints;
        this.ySamplePoints = ySamplePoints;

        this.samples = samples;

        this.verifySamples();
    }

    private void verifySamples()
    {
        ExceptionHelpers.Assert(this.samples.length == this.xSamplePoints.length, "x samples length (%d) doesn't match x sample points length (%d)", this.samples.length, this.xSamplePoints.length);
        for (int x = 0; x < this.xSamplePoints.length; x++)
        {
            ExceptionHelpers.Assert(this.samples[x].length == this.ySamplePoints.length, "y samples length (%d) doesn't match y sample points length (%d) on row %d", this.samples[x].length, this.ySamplePoints.length, x);
        }
    }

    /**
     * Retrieve the best sample for the given position/location
     * @param x position/location
     * @param y position/location
     * @return value using bilinear interpolation of the samples closest to x/y
     */
    public double sample(double x, double y)
    {
        int row1 = 0;
        int row2 = -1;
        for (int i = 1; i < this.xSamplePoints.length; i++)
        {
            if (this.xSamplePoints[i] > x)
            {
                row2 = i;
                break;
            }
            else if (this.xSamplePoints[i] <= x)
            {
                row1 = i;
            }
        }

        int col1 = 0;
        int col2 = -1;
        for (int i = 1; i < this.ySamplePoints.length; i++)
        {
            if (this.ySamplePoints[i] > y)
            {
                col2 = i;
                break;
            }
            else if (this.ySamplePoints[i] <= y)
            {
                col1 = i;
            }
        }

        if (row2 == -1 && col2 == -1)
        {
            // case: grab the closest sample at the beginnings/ends
            return this.samples[row1][col1];
        }
        else if (row2 == -1)
        {
            // linear interpolation in the y direction at an x end
            double y1 = this.ySamplePoints[col1];
            double y2 = this.ySamplePoints[col2];

            double sample1 = this.samples[row1][col1];
            double sample2 = this.samples[row1][col2];

            return (1.0 / (y2 - y1)) * (sample1 * (y2 - y) + sample2 * (y - y1));
        }
        else if (col2 == -1)
        {
            // linear interpolation in the x direction at a y end
            double x1 = this.xSamplePoints[row1];
            double x2 = this.xSamplePoints[row2];

            double sample1 = this.samples[row1][col1];
            double sample2 = this.samples[row2][col1];

            return (1.0 / (x2 - x1)) * (sample1 * (x2 - x) + sample2 * (x - x1));
        }
        else
        {
            // bilinear interpolation
            double x1 = this.xSamplePoints[row1];
            double x2 = this.xSamplePoints[row2];
            double y1 = this.ySamplePoints[col1];
            double y2 = this.ySamplePoints[col2];

            double sample11 = this.samples[row1][col1];
            double sample12 = this.samples[row1][col2];
            double sample21 = this.samples[row2][col1];
            double sample22 = this.samples[row2][col2];

            return (1.0 / ((x2 - x1) * (y2 - y1))) *
                (sample11 * (x2 - x) * (y2 - y) +
                 sample21 * (x - x1) * (y2 - y) +
                 sample12 * (x2 - x) * (y - y1) +
                 sample22 * (x - x1) * (y - y1));
        }
    }
}
