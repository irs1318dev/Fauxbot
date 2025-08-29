package frc.lib.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import frc.lib.helpers.AnglePair;
import frc.lib.helpers.Helpers;

public class HelpersTests
{
    @Test
    public void testPolarConversion_Origin()
    {
        double result = Helpers.convertToPolarAngle(0.0, 0.0);
        Assertions.assertEquals(-1.0, result, "expect origin to return -1");
    }

    @Test
    public void testPolarConversion_xAxis()
    {
        double result = Helpers.convertToPolarAngle(0.2, 0.0);
        Assertions.assertEquals(0.0, result, "expect x-axis to return 0 degrees");
    }

    @Test
    public void testPolarConversion_negXAxis()
    {
        double result = Helpers.convertToPolarAngle(-0.4, 0.0);
        Assertions.assertEquals(180.0, result, "expect negative x-axis to return 180 degrees");
    }

    @Test
    public void testPolarConversion_yAxis()
    {
        double result = Helpers.convertToPolarAngle(0.0, 0.75);
        Assertions.assertEquals(90.0, result, "expect y-axis to return 90 degrees");
    }

    @Test
    public void testPolarConversion_negYAxis()
    {
        double result = Helpers.convertToPolarAngle(0.0, -0.75);
        Assertions.assertEquals(270.0, result, "expect y-axis to return 270 degrees");
    }

    @Test
    public void testPolarConversion_quadrant1Center()
    {
        double result = Helpers.convertToPolarAngle(0.5, 0.5);
        Assertions.assertEquals(45.0, result, "expect Quadrant I to return 45 degrees");
    }

    @Test
    public void testPolarConversion_quadrant2Center()
    {
        double result = Helpers.convertToPolarAngle(-1.0, 1.0);
        Assertions.assertEquals(135.0, result, "expect Quadrant II to return 135 degrees");
    }

    @Test
    public void testPolarConversion_quadrant3Center()
    {
        double result = Helpers.convertToPolarAngle(-0.75, -0.75);
        Assertions.assertEquals(225.0, result, "expect Quadrant III to return 225 degrees");
    }

    @Test
    public void testPolarConversion_quadrant4Center()
    {
        double result = Helpers.convertToPolarAngle(0.1, -0.1);
        Assertions.assertEquals(315.0, result, "expect Quadrant IV to return 315 degrees");
    }

    @Test
    public void testPolarConversion_nearAngle()
    {
        double result = Helpers.convertToPolarAngle(0.0000001, 0.1);
        Assertions.assertEquals(90.0, result, 0.01, "expect near the y axis to return near 90 degrees");
    }

    @Test
    public void checkClosestAngleReversible()
    {
        for (double goal = -180.0; goal <= 180.0; goal += 1.0)
        {
            for (double multiplier = -3.0; multiplier <= 3.0; multiplier += 1.0)
            {
                double expected = goal + multiplier * 360.0;
                for (double offset = -179.0; offset <= 179.0; offset += 1.0)
                {
                    AnglePair pair = Helpers.getClosestAngle(goal, expected + offset, true);
                    if (offset < -90.0)
                    {
                        Assertions.assertEquals(
                            expected - 180.0,
                            pair.getAngle(),
                            String.format("%f %f %f", goal, multiplier, offset));

                        Assertions.assertEquals(true, pair.getSwapDirection());
                    }
                    else if (offset <= 90.0)
                    {
                        Assertions.assertEquals(
                            expected,
                            pair.getAngle(),
                            String.format("%f %f %f", goal, multiplier, offset));

                        Assertions.assertEquals(false, pair.getSwapDirection());
                    }
                    else
                    {
                        Assertions.assertEquals(
                            expected + 180.0,
                            pair.getAngle(),
                            String.format("%f %f %f", goal, multiplier, offset));

                        Assertions.assertEquals(true, pair.getSwapDirection());
                    }
                }
            }
        }
    }

    @Test
    public void checkClosestAngleForwardOnly()
    {
        for (double goal = -180.0; goal <= 180.0; goal += 1.0)
        {
            for (double multiplier = -3.0; multiplier <= 3.0; multiplier += 1.0)
            {
                double expected = goal + multiplier * 360.0;
                for (double offset = -179.0; offset <= 179.0; offset += 1.0)
                {
                    AnglePair pair = Helpers.getClosestAngle(goal, expected + offset, false);
                    Assertions.assertEquals(
                        expected,
                        pair.getAngle(),
                        String.format("%f %f %f", goal, multiplier, offset));

                    Assertions.assertEquals(false, pair.getSwapDirection());
                }
            }
        }
    }

    @Test
    public void checkUpdateAngleRange360()
    {
        for (double mult = -3.0; mult <= 3.0; mult += 1.0)
        {
            for (double expected = 0.0; expected < 360.0; expected += 0.02)
            {
                Assertions.assertEquals(expected, Helpers.updateAngleRange360(expected + mult * 360.0), 0.0000001);
            }
        }
    }

    @Test
    public void checkUpdateAngleRange180()
    {
        for (double mult = -3.0; mult <= 3.0; mult += 1.0)
        {
            for (double expected = -179.9; expected < 180.0; expected += 0.02)
            {
                Assertions.assertEquals(expected, Helpers.updateAngleRange180(expected + mult * 360.0), 0.0000001);
            }
        }
    }

    @Test
    public void checkGetClosestAngleAbsolute()
    {
        for (double desired = -179.999; desired < 180.0; desired += 0.2)
        {
            // from -92 to 92:
            for (double difference = -91.9; difference < 92.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, false, true);
                Assertions.assertEquals(false, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }

            // from -180 to -92:
            for (double difference = -179.9; difference < -92.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired + 180.0);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, false, true);
                Assertions.assertEquals(true, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }

            // from 92 to 180:
            for (double difference = 92.1; difference < 180.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired + 180.0);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, false, true);
                Assertions.assertEquals(true, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }
        }

        for (double desired = -179.999; desired < 180.0; desired += 0.2)
        {
            // from -88 to 88:
            for (double difference = -87.9; difference < 88.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, true, true);
                Assertions.assertEquals(false, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }

            // from -180 to -88:
            for (double difference = -179.9; difference < -88.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired + 180.0);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, true, true);
                Assertions.assertEquals(true, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }

            // from 88 to 180:
            for (double difference = 88.1; difference < 180.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired + 180.0);
                AnglePair result = Helpers.getClosestAngleAbsolute(desired, current, true, true);
                Assertions.assertEquals(true, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }
        }
    }

    @Test
    public void testWithinAbsoluteAngleRangeCrossZero()
    {
        for (double value = 0.0; value <= 90.0; value += 0.5)
        {
            Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(value, 270.0, 90.0));
        }

        for (double value = 90.5; value < 270.0; value += 0.5)
        {
            Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(value, 270.0, 90.0));
        }

        for (double value = 270.0; value <= 360.0; value += 0.5)
        {
            Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(value, 270.0, 90.0));
        }

        // at the edge
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(90.0, 270.0, 90.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(270.0, 270.0, 90.0));

        // near the edge
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(270.01, 270.0, 90.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(89.99, 270.0, 90.0));

        // over the edge
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(90.01, 270.0, 90.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(269.99, 270.0, 90.0));

        // in the middle of the valid range
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(0.01, 270.0, 90.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(0.0, 270.0, 90.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(359.99, 270.0, 90.0));

        // halfway from the valid range
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(179.99, 270.0, 90.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(180.00, 270.0, 90.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(180.01, 270.0, 90.0));
    }

    @Test
    public void testWithinAbsoluteAngleRangeDontCrossZero()
    {
        for (double value = 0.0; value < 90.0; value += 0.5)
        {
            Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(value, 90.0, 270.0));
        }

        for (double value = 90.0; value <= 270.0; value += 0.5)
        {
            Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(value, 90.0, 270.0));
        }

        for (double value = 270.5; value < 360.0; value += 0.5)
        {
            Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(value, 90.0, 270.0));
        }

        // at the edge
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(90.0, 90.0, 270.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(270.0, 90.0, 270.0));

        // near the edge
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(90.01, 90.0, 270.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(269.99, 90.0, 270.0));

        // over the edge
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(89.99, 90.0, 270.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(270.01, 90.0, 270.0));

        // in the middle of the valid range
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(179.99, 90.0, 270.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(180.0, 90.0, 270.0));
        Assertions.assertTrue(Helpers.withinAbsoluteAngleRange(180.01, 90.0, 270.0));

        // halfway from the valid range
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(0.01, 90.0, 270.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(0.0, 90.0, 270.0));
        Assertions.assertFalse(Helpers.withinAbsoluteAngleRange(359.99, 90.0, 270.0));
    }

    @Test
    public void testEnforceAbsoluteAngleRangeCrossZero()
    {
        for (double value = 0.0; value <= 90.0; value += 0.5)
        {
            Assertions.assertEquals(value, Helpers.enforceAbsoluteAngleRange(value, 270.0, 90.0), 0.0001);
        }

        for (double value = 90.5; value < 180.0; value += 0.5)
        {
            Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(value, 270.0, 90.0), 0.0001);
        }

        for (double value = 180.5; value < 270.0; value += 0.5)
        {
            Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(value, 270.0, 90.0), 0.0001);
        }

        for (double value = 270.0; value <= 360.0; value += 0.5)
        {
            Assertions.assertEquals(value, Helpers.enforceAbsoluteAngleRange(value, 270.0, 90.0), 0.0001);
        }

        // at the edge
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(90.0, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(270.0, 270.0, 90.0), 0.0001);

        // near the edge
        Assertions.assertEquals(270.01, Helpers.enforceAbsoluteAngleRange(270.01, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(89.99, Helpers.enforceAbsoluteAngleRange(89.99, 270.0, 90.0), 0.0001);

        // over the edge
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(90.01, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(269.99, 270.0, 90.0), 0.0001);

        // in the middle of the valid range
        Assertions.assertEquals(0.01, Helpers.enforceAbsoluteAngleRange(0.01, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(0.0, Helpers.enforceAbsoluteAngleRange(0.0, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(359.99, Helpers.enforceAbsoluteAngleRange(359.99, 270.0, 90.0), 0.0001);

        // halfway from the valid range
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(179.99, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(180.00, 270.0, 90.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(180.01, 270.0, 90.0), 0.0001);
    }

    @Test
    public void testEnforceAbsoluteAngleRangeDontCrossZero()
    {
        for (double value = 0.1; value < 90.0; value += 0.5)
        {
            Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(value, 90.0, 270.0), 0.0001);
        }

        for (double value = 90.0; value <= 270.0; value += 0.5)
        {
            Assertions.assertEquals(value, Helpers.enforceAbsoluteAngleRange(value, 90.0, 270.0), 0.0001);
        }

        for (double value = 270.5; value < 360.0; value += 0.5)
        {
            Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(value, 90.0, 270.0), 0.0001);
        }

        // at the edge
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(90.0, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(270.0, 90.0, 270.0), 0.0001);

        // near the edge
        Assertions.assertEquals(90.01, Helpers.enforceAbsoluteAngleRange(90.01, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(269.99, Helpers.enforceAbsoluteAngleRange(269.99, 90.0, 270.0), 0.0001);

        // over the edge
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(89.99, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(270.01, 90.0, 270.0), 0.0001);

        // in the middle of the valid range
        Assertions.assertEquals(179.99, Helpers.enforceAbsoluteAngleRange(179.99, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(180.0, Helpers.enforceAbsoluteAngleRange(180.0, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(180.01, Helpers.enforceAbsoluteAngleRange(180.01, 90.0, 270.0), 0.0001);

        // halfway from the valid range
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(0.01, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(90.0, Helpers.enforceAbsoluteAngleRange(0.0, 90.0, 270.0), 0.0001);
        Assertions.assertEquals(270.0, Helpers.enforceAbsoluteAngleRange(359.99, 90.0, 270.0), 0.0001);
    }
}