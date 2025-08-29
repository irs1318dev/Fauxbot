package frc.lib.helpers;

import frc.robot.TuningConstants;

/**
 * Helper functions and constants
 */
public class Helpers
{
    // Conversion constants...
    public static final double DEGREES_TO_RADIANS = (Math.PI / 180.0f);
    public static final double RADIANS_TO_DEGREES = (180.0f / Math.PI);
    public static final double INCHES_PER_METER = 39.37;
    public static final double METERS_PER_INCH = 0.0254;
    public static final double FEET_PER_METER = 3.2808399;
    public static final double METERS_PER_FOOT = 0.3048;
    public static final double KILOGRAMS_PER_POUND = 0.45359237;
    public static final double POUNDS_PER_KILOGRAM = 2.20462262;
    public static final double GRAVITY_INCH_PER_SQ_SECOND = 386.22047244094; // in/s^2
    private static final double ROUGH_EQUALS_DEFAULT = 0.0001;

    /**
     * Check whether the two values are roughly equal
     * @param value1 first value
     * @param value2 value to check against
     * @param range acceptable diference to be declared equal
     * @return true if roughly equal, otherwise false
     */
    public static boolean roughEquals(double value1, double value2)
    {
        return Helpers.roughEquals(value1, value2, Helpers.ROUGH_EQUALS_DEFAULT);
    }

    /**
     * Check whether the two values are roughly equal
     * @param value1 first value
     * @param value2 value to check against
     * @param range acceptable diference to be declared equal
     * @return true if roughly equal, otherwise false
     */
    public static boolean roughEquals(double value1, double value2, double range)
    {
        return Math.abs(value1 - value2) <= range;
    }

    /**
     * Clamps the value to be within the supported range
     * @param value value to clamp
     * @param minValue minimum value in the supported range
     * @param maxValue maximum value in the supported range
     * @return value clamped to one between min and max
     */
    public static double enforceRange(double value, double minValue, double maxValue)
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

    /**
     * Checks if the provided value is within the provided range
     * @param value value to check
     * @param minValue minimum value in the supported range
     * @param maxValue maximum value in the supported range
     * @return true if the value is between min and max, otherwise false
     */
    public static boolean withinRange(double value, double minValue, double maxValue)
    {
        return value >= minValue && value <= maxValue;
    }

    /**
     * Checks if the provided value is within a small delta of the expected value
     * @param actualValue actual value to check
     * @param expectedValue expected value to compare against
     * @param acceptableDelta allowable difference to be considered within the range
     * @return true if the actual value is within the acceptable range from the expected, otherwise false
     */
    public static boolean withinDelta(double actualValue, double expectedValue, double acceptableDelta)
    {
        return actualValue >= expectedValue - acceptableDelta &&
            actualValue <= expectedValue + acceptableDelta;
    }

    /**
     * Returns the trigonometric tangent of an angle.
     * @param angle in degrees.
     * @return the tangent of the argument (opposite length over adjacent length).
     */
    public static double tand(double angle)
    {
        return Math.tan(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arctangent.
     * @param ratio of the opposite side over the adjacent side
     * @return the arctangent angle in degrees between -90 and 90
     */
    public static double atand(double ratio)
    {
        return Math.atan(ratio) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Returns the trigonometric arctangent.
     * @param opposite side
     * @param adjacent side
     * @return the arctangent angle in degrees between -180 and 180
     */
    public static double atan2d(double opposite, double adjacent)
    {
        return Math.atan2(opposite, adjacent) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Returns the trigonometric sine of an angle.
     * @param angle in degrees.
     * @return the sine of the argument (opposite length over hypotenuse length).
     */
    public static double sind(double angle)
    {
        return Math.sin(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arcsine.
     * @param ratio of the opposite side over the hypotenuse side
     * @return the arcsine angle in degrees between -90 and 90
     */
    public static double asind(double ratio)
    {
        return Math.asin(ratio) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Returns the trigonometric cosine of an angle.
     * @param angle in degrees.
     * @return the cosine of the argument (adjacent length over hypotenuse length).
     */
    public static double cosd(double angle)
    {
        return Math.cos(angle * Helpers.DEGREES_TO_RADIANS);
    }

    /**
     * Returns the trigonometric arccosine.
     * @param ratio of the adjacent side over the hypotenuse side
     * @return the arccosine angle in degrees between -90 and 90
     */
    public static double acosd(double ratio)
    {
        return Math.acos(ratio) * Helpers.RADIANS_TO_DEGREES;
    }

    /**
     * Clamps the value to be within the supported range of angles between 0 and 360 degrees
     * @param value value to clamp
     * @param startValue starting value in the supported range
     * @param endValue ending value in the supported range
     * @return value clamped between start and end
     */
    public static double enforceAbsoluteAngleRange(double value, double startValue, double endValue)
    {
        // allow -0.0
        if (value < 0.0 && value > -0.0001)
        {
            value = 0.0;
        }

        ExceptionHelpers.Assert(value >= 0.0 && value <= 360.0, "value must be between [0, 360].  Actual: %f", value);

        ExceptionHelpers.Assert(startValue >= 0.0 && startValue <= 360.0, "startValue must be between [0, 360].  Actual: %f", startValue);
        ExceptionHelpers.Assert(endValue >= 0.0 && endValue <= 360.0, "endValue must be between [0, 360].  Actual: %f", endValue);

        // if the range doesn't cross 360/0, we can do a normal range enforcement
        if (startValue < endValue)
        {
            if (Helpers.withinRange(value, startValue, endValue))
            {
                return value;
            }
        }
        else
        {
            // valid range is: startValue to 360, and 0 to endValue
            if ((value >= startValue && value <= 360.0) ||
                (value >= 0.0 && value <= endValue))
            {
                return value;
            }
        }

        // need to see how far away we are from the start/end
        double distanceFromStart = Helpers.updateAngleRange180(startValue - value);
        double distanceFromEnd = Helpers.updateAngleRange180(value - endValue);

        if (Math.abs(distanceFromStart) <= Math.abs(distanceFromEnd))
        {
            return startValue;
        }
        else
        {
            return endValue;
        }
    }

    /**
     * Checks if the provided value is within the provided range of angles between 0 and 360 degrees
     * @param value value to check
     * @param startValue starting value in the supported range
     * @param endValue ending value in the supported range
     * @return true if the value is between start and end, otherwise false
     */
    public static boolean withinAbsoluteAngleRange(double value, double startValue, double endValue)
    {
        ExceptionHelpers.Assert(value >= 0.0 && value <= 360.0, "value must be between [0, 360].  Actual: %f", value);

        ExceptionHelpers.Assert(startValue >= 0.0 && startValue <= 360.0, "startValue must be between [0, 360].  Actual: %f", startValue);
        ExceptionHelpers.Assert(endValue >= 0.0 && endValue <= 360.0, "endValue must be between [0, 360].  Actual: %f", endValue);

        // if the range doesn't cross 360/0, we can do a normal range enforcement
        if (startValue < endValue)
        {
            return Helpers.withinRange(value, startValue, endValue);
        }

        // valid range is: startValue to 360, and 0 to endValue
        return (value >= startValue && value <= 360.0) ||
            (value >= 0.0 && value <= endValue);
    }

    /**
     * Get the closest angle equivalent to desiredAngle from current angle, swapping directions if it is closer
     * Through infinite range
     * Note: prefers the same direction if equivalent
     * @param desiredAngle desired angle in degrees (between -180 and 180)
     * @param currentAngle current angle in degrees (any value)
     * @return pair containing closest angle fitting desired angle from current angle in degrees
     */
    public static AnglePair getClosestAngle(double desiredAngle, double currentAngle, boolean allowReverse)
    {
        // if (TuningConstants.THROW_EXCEPTIONS && 
        //     !Helpers.WithinRange(desiredAngle, -180.0, 180.0))
        // {
        //     throw new RuntimeException(String.format("expect desiredAngle to be between (-180, 180). actual %f", desiredAngle));
        // }

        // get the difference in degrees between -180 and 180
        double difference = Helpers.updateAngleRange180(desiredAngle - currentAngle);

        if (allowReverse)
        {
            if (difference < -90.0)
            {
                return new AnglePair(currentAngle + difference + 180.0, true);
            }
            else if (difference > 90.0)
            {
                return new AnglePair(currentAngle + difference - 180.0, true);
            }
        }

        return new AnglePair(currentAngle + difference, false);
    }

    /**
     * Get the closest angle equivalent to desiredAngle from current angle, swapping directions if it is closer,
     * within range of [0, 360)
     * Note: prefers the same direction if equivalent
     * @param desiredAngle desired angle in degrees (between -180 and 180)
     * @param currentAngle current angle in degrees (between 0 and 360)
     * @param currentReversed whether we are currently driving in the reverse direction
     * @param allowReverse whether to allow driving in the reverse or not
     * @return pair containing closest angle fitting desired angle from current angle in degrees
     */
    public static AnglePair getClosestAngleAbsolute(double desiredAngle, double currentAngle, boolean currentReversed, boolean allowReverse)
    {
        if (!Helpers.withinRange(desiredAngle, -180.0, 180.0))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(String.format("expect desiredAngle to be between (-180, 180). actual %f", desiredAngle));
            }
            else
            {
                System.err.println(String.format("expect desiredAngle to be between (-180, 180). actual %f", desiredAngle));
            }
        }

        if (!Helpers.withinRange(currentAngle, .0, 360.0))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(String.format("expect currentAngle to be between (0, 360). actual %f", currentAngle));
            }
            else
            {
                System.err.println(String.format("expect currentAngle to be between (0, 360). actual %f", currentAngle));
            }
        }

        // change range to be [0, 360)
        if (desiredAngle < 0.0)
        {
            desiredAngle += 360.0;
        }

        if (!allowReverse)
        {
            return new AnglePair(desiredAngle, false);
        }

        // get the difference in degrees between -180 and 180
        double difference = Helpers.updateAngleRange180(desiredAngle - currentAngle);

        if (currentReversed)
        {
            if (difference < -88.0 || difference > 88.0)
            {
                return new AnglePair((desiredAngle + 180.0) % 360.0, true);
            }
        }
        else
        {
            if (difference < -92.0 || difference > 92.0)
            {
                return new AnglePair((desiredAngle + 180.0) % 360.0, true);
            }
        }

        return new AnglePair(desiredAngle, false);
    }

    /**
     * Returns angle in the range (-180, 180]
     * @param angle in some large range
     * @return angle capped between -180 and 180
     */
    public static double updateAngleRange180(double angle)
    {
        // get the difference in degrees between -360 and 360
        double twoLoopAngle = angle % 360.0;

        // change the range from -180 to 180
        if (twoLoopAngle <= -180.0)
        {
            return twoLoopAngle + 360.0;
        }
        else if (twoLoopAngle > 180.0)
        {
            return twoLoopAngle - 360.0;
        }

        return twoLoopAngle;
    }

    /**
     * Returns angle in the range [0,360)
     * @param angle in some large range
     * @return angle capped between 0 and 360
     */
    public static double updateAngleRange360(double angle)
    {
        // get the difference in degrees between -360 and 360 using modulo
        double twoLoopAngle = angle % 360.0;

        // change the range from -180 to 180
        if (twoLoopAngle < 0.0)
        {
            return twoLoopAngle + 360.0;
        }
        else
        {
            return twoLoopAngle;
        }
    }

    public static boolean anglePairWithinDelta(double value1, boolean isSwapped1, double value2, boolean isSwapped2, double acceptableDelta)
    {
        if (isSwapped1)
        {
            value1 = Helpers.updateAngleRange180(value1 + 180.0);
        }
        
        if (isSwapped2)
        {
            value2 = Helpers.updateAngleRange180(value2 + 180.0);
        }

        return Helpers.withinDelta(value1, value2, acceptableDelta);
    }

    /**
     * Convert from cartesian coordinates (x, y) to polar angle (along positive x-axis is 0, increasing counter-clockwise)
     * @param x coordinate
     * @param y coordinate
     * @return polar angle (theta), or -1 if at (0, 0)
     */
    public static double convertToPolarAngle(double x, double y)
    {
        // special case for (0, 0)
        if (x == 0.0 && y == 0.0)
        {
            return -1.0;
        }

        // handle horizontal lines
        if (x == 0.0)
        {
            if (y > 0.0)
            {
                return 90.0;
            }
            else
            {
                return 270.0;
            }
        }

        // handle vertical lines
        if (y == 0.0)
        {
            if (x > 0.0)
            {
                return 0.0;
            }
            else
            {
                return 180.0;
            }
        }

        if (x > 0.0 && y > 0.0)
        {
            // quadrant I
            return Helpers.atand(y / x);
        }
        else if (x < 0.0 && y > 0.0)
        {
            // quadrant II
            return Helpers.atand(y / x) + 180.0;
        }
        else if (x < 0.0 && y < 0.0)
        {
            // quadrant III
            return Helpers.atand(y / x) + 180.0;
        }
        else // if (x > 0.0 && y < 0.0)
        {
            // quadrant IV
            return Helpers.atand(y / x) + 360.0;
        }
    }

    /**
     * Calculate the distance of the link in a triange opposite the provided angle, with the two adjacent lengths known
     * @param adjacent1 first length adjacent to the provided angle
     * @param adjacent2 second length adjacent to the provided angle
     * @param angle in degrees
     * @return length of the link opposite the provided angle
     */
    public static double calculateLawOfCosinesDistance(double adjacent1, double adjacent2, double angle)
    {
        double oppositeSquared = adjacent1 * adjacent1 + adjacent2 * adjacent2 - 2.0 * adjacent1 * adjacent2 * Helpers.cosd(angle);
        if (oppositeSquared < 0.0)
        {
            return Math.sqrt(-oppositeSquared);
        }

        return Math.sqrt(oppositeSquared);
    }

    /**
     * Calculate the angle between a pair of adjacent lines with the length in the triangle known
     * @param adjacent1 first length adjacent to the desired angle
     * @param adjacent2 second length adjacent to the desired angle
     * @param opposite length of the line opposite to the desired angle
     * @return angle of the value in degrees
     */
    public static double calculateLawOfCosinesAngle(double adjacent1, double adjacent2, double opposite)
    {
        return (double)Helpers.calculateLawOfCosinesAngle(false, adjacent1, adjacent2, opposite);
    }

    /**
     * Calculate the angle between a pair of adjacent lines with the length in the triangle known
     * @param adjacent1 first length adjacent to the desired angle
     * @param adjacent2 second length adjacent to the desired angle
     * @param opposite length of the line opposite to the desired angle
     * @return angle of the value in degrees
     */
    public static Double calculateLawOfCosinesAngleOrNull(double adjacent1, double adjacent2, double opposite)
    {
        return Helpers.calculateLawOfCosinesAngle(true, adjacent1, adjacent2, opposite);
    }

    /**
     * Calculate the angle between a pair of adjacent lines with the length in the triangle known
     * @param nullIfInvalid whether to return null if the angle is impossible, or to default to 0/180 degrees
     * @param adjacent1 first length adjacent to the desired angle
     * @param adjacent2 second length adjacent to the desired angle
     * @param opposite length of the line opposite to the desired angle
     * @return angle of the value in degrees
     */
    private static Double calculateLawOfCosinesAngle(boolean nullIfInvalid, double adjacent1, double adjacent2, double opposite)
    {
        double angle;
        double cosineAngle = (adjacent1 * adjacent1 + adjacent2 * adjacent2 - opposite * opposite) / (2.0 * adjacent1 * adjacent2);
        if (cosineAngle > 1.0)
        {
            // be forgiving of values that are just _very slightly_ off regardless of whether we are returning null for invalid angles...
            if (cosineAngle <= 1.00001)
            {
                return 0.0;
            }

            return nullIfInvalid ? null : 0.0;
        }
        else if (cosineAngle < -1.0)
        {
            // be forgiving of values that are just _very slightly_ off regardless of whether we are returning null for invalid angles...
            if (cosineAngle >= -1.00001)
            {
                return 180.0;
            }

            return nullIfInvalid ? null : 180.0;
        }

        angle = Helpers.acosd((adjacent1 * adjacent1 + adjacent2 * adjacent2 - opposite * opposite) / (2.0 * adjacent1 * adjacent2));
        return angle;
    }
}
