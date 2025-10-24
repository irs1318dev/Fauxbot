package frc.lib.helpers;

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
