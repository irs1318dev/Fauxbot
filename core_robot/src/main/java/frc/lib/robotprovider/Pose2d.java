package frc.lib.robotprovider;

/**
 * A representation of a pose in 2 dimensions
 */
public class Pose2d extends Point2d
{
    public double angle;

    /**
     * A pose in 2 dimensions.
     * @param x typically in inches
     * @param y typically in inches
     * @param angle typically in degrees
     */
    public Pose2d(double x, double y, double angle)
    {
        super(x, y);
        this.angle = angle;
    }
}
