package frc.lib.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import frc.lib.helpers.BilinearInterpolator;

public class BilinearInterpolationTests
{
    // @Test
    public void checkSimpleBilinearSystem()
    {
        double[] xSamplePoints = new double[] { 0.0, 1.0, 2.0 };
        double[] ySamplePoints = new double[] { 0.0, 1.0, 2.0 };
        double[][] samples = new double[][]
        {
            { 0.0, 1.0, 2.0 },
            { 1.0, 2.0, 3.0 },
            { 2.0, 3.0, 4.0 }
        };

        BilinearInterpolator interpolator = new BilinearInterpolator(xSamplePoints, ySamplePoints, samples);

        Assertions.assertEquals(0.0, interpolator.sample(0.0, 0.0));
        Assertions.assertEquals(0.5, interpolator.sample(0.0, 0.5));
        Assertions.assertEquals(1.0, interpolator.sample(1.0, 0.0));
        Assertions.assertEquals(1.5, interpolator.sample(0.5, 1.0));
        Assertions.assertEquals(1.0, interpolator.sample(0.5, 0.5));
        Assertions.assertEquals(1.0, interpolator.sample(0.0, 1.0));
        Assertions.assertEquals(2.0, interpolator.sample(1.0, 1.0));
        Assertions.assertEquals(2.0, interpolator.sample(0.0, 2.0));
        Assertions.assertEquals(2.0, interpolator.sample(2.0, 0.0));
        Assertions.assertEquals(2.0, interpolator.sample(0.5, 1.5));
    }
}
