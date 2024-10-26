package frc.lib.robotprovider;

/**
 * The desired state of a robot following a trajectory at some point in time
 */
public class TrajectoryState
{
    public final double xPosition; // in inches
    public final double yPosition; // in inches
    public final double angle; // in degrees
    public final double xVelocity; // in inches per second
    public final double yVelocity; // in inches per second
    public final double angleVelocity; // in degrees per second

    public TrajectoryState(
        double xPosition,
        double yPosition,
        double angle,
        double xVelocity,
        double yVelocity,
        double angleVelocity)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.angle = angle;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.angleVelocity = angleVelocity;
    }
}