package frc.lib.helpers;
import frc.robot.*;

/**
 * Helper class to help solve problem where we want something to take the shortest path to face a certain angle, where facing backwards towards that angle is just as good as facing forwards.
 */
public class AnglePair
{
    private double angle;
    private boolean swapDirection;

    /**
     * Initializes a new instance of the AnglePair class.
     * @param angle value to apply
     * @param swapDirection value to apply
     */
    public AnglePair(double angle, boolean swapDirection)
    {
        this.angle = angle;
        this.swapDirection = swapDirection;
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
        if (!Helpers.WithinRange(desiredAngle, -180.0, 180.0))
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

        if (!Helpers.WithinRange(currentAngle, .0, 360.0))
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
     * Get the angle from the pair
     * @return the angle
     */
    public double getAngle()
    {
        return this.angle;
    }

    /**
     * Get whether the direction is swapped
     * @return true if the direction is swapped, otherwise false
     */
    public boolean getSwapDirection()
    {
        return this.swapDirection;
    }
}
