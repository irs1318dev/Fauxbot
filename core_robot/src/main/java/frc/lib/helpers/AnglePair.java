package frc.lib.helpers;

public class AnglePair
{
    private double angle;
    private boolean swapDirection;

    /**
     * Initializes a new AnglePair
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
        double difference = Helpers.updateAngleRange(desiredAngle - currentAngle);

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

    public double getAngle()
    {
        return this.angle;
    }

    public boolean getSwapDirection()
    {
        return this.swapDirection;
    }
}
