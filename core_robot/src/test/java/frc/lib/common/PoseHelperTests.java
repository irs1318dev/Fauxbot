package frc.lib.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import frc.lib.helpers.PoseHelpers;
import frc.lib.helpers.Triple;

public class PoseHelperTests
{
    // @Test
    public void testPoseExponential()
    {
        // test the in-place pose exponential
        Triple<Double, Double, Double> value = new Triple<Double, Double, Double>(5.0, 5.0, 90.0);
        PoseHelpers.inversePoseExponential(value);

        Assertions.assertEquals(5.0 / 2.0 * Math.PI, value.getFirst(), 1E-8);
        Assertions.assertEquals(0.0, value.getSecond(), 1E-8);
        Assertions.assertEquals(90.0, value.getThird(), 1E-8);

        // Make sure computed twist gives back original end pose
        PoseHelpers.poseExponential(value);
        Assertions.assertEquals(5.0, value.getFirst(), 1E-8);
        Assertions.assertEquals(5.0, value.getSecond(), 1E-8);
        Assertions.assertEquals(90, value.getThird(), 1E-8);
    }

    // @Test
    public void testPoseExponentialRoundtrip()
    {
        // test the in-place pose exponential
        for (double angle = -90.0; angle < 90.0; angle += 5.0)
        {
            for (double dx = -10.0; dx <= 10.0; dx += 0.5)
            {
                for (double dy = -10.0; dy <= 10.0; dy += 0.5)
                {
                    Triple<Double, Double, Double> value = new Triple<Double, Double, Double>(dx, dy, angle);
                    PoseHelpers.inversePoseExponential(value);

                    // Make sure computed twist gives back original end pose
                    PoseHelpers.poseExponential(value);
                    Assertions.assertEquals(dx, value.getFirst(), 1E-6);
                    Assertions.assertEquals(dy, value.getSecond(), 1E-6);
                    Assertions.assertEquals(angle, value.getThird(), 1E-6);            
                }
            }
        }
    }
}
