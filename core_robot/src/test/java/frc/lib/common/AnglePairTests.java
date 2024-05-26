package frc.lib.common;

import frc.lib.helpers.AnglePair;
import frc.lib.helpers.Helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnglePairTests
{
    // @Test
    public void checkClosestAngleReversible()
    {
        for (double goal = -180.0; goal <= 180.0; goal += 1.0)
        {
            for (double multiplier = -3.0; multiplier <= 3.0; multiplier += 1.0)
            {
                double expected = goal + multiplier * 360.0;
                for (double offset = -179.0; offset <= 179.0; offset += 1.0)
                {
                    AnglePair pair = AnglePair.getClosestAngle(goal, expected + offset, true);
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

    // @Test
    public void checkClosestAngleForwardOnly()
    {
        for (double goal = -180.0; goal <= 180.0; goal += 1.0)
        {
            for (double multiplier = -3.0; multiplier <= 3.0; multiplier += 1.0)
            {
                double expected = goal + multiplier * 360.0;
                for (double offset = -179.0; offset <= 179.0; offset += 1.0)
                {
                    AnglePair pair = AnglePair.getClosestAngle(goal, expected + offset, false);
                    Assertions.assertEquals(
                        expected,
                        pair.getAngle(),
                        String.format("%f %f %f", goal, multiplier, offset));

                    Assertions.assertEquals(false, pair.getSwapDirection());
                }
            }
        }
    }

    // @Test
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

    // @Test
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

    // @Test
    public void checkGetClosestAngleAbsolute()
    {
        for (double desired = -179.999; desired < 180.0; desired += 0.2)
        {
            // from -92 to 92:
            for (double difference = -91.9; difference < 92.0; difference += 0.2)
            {
                double current = Helpers.updateAngleRange360(desired + difference);
                double desired360 = Helpers.updateAngleRange360(desired);
                AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, false, true);
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
                AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, false, true);
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
                    AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, false, true);
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
                AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, true, true);
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
                AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, true, true);
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
                AnglePair result = AnglePair.getClosestAngleAbsolute(desired, current, true, true);
                Assertions.assertEquals(true, result.getSwapDirection());
                Assertions.assertEquals(desired360, result.getAngle(), 0.00001, String.format("%f %f %f %f", desired, difference, current, result.getAngle()));
                Assertions.assertTrue(desired360 >= 0.0);
                Assertions.assertTrue(desired360 <= 360.0);
            }
        }
    }
}