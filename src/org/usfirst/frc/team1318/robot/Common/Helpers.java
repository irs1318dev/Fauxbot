package org.usfirst.frc.team1318.robot.Common;

public class Helpers
{
    public static double EnforceRange(double value, double minValue, double maxValue)
    {
        if (value > maxValue)
        {
            return maxValue;
        }
        else if (value < minValue)
        {
            return minValue;
        }

        return value;
    }
}
