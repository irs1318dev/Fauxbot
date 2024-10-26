package frc.lib.robotprovider;

/**
 * Represents a rotation goal that the robot should achieve as a part of running through a trajectory
 */
public class PathPlannerRotationTarget implements IPathPlannerGoal
{
    public final double orientation;
    public final double percentage;

    /**
     * Creates an instance of the PathPlannerRotationTarget class instructing the robot to face the specified orientation at the percentage between the previous waypoint and the subsequent one.
     * @param orientation in degrees
     * @param percentage of the way between the previous waypoint and the subsequent one to reach the orientation, from (0.0, 1.0) non-inclusive
     */
    public PathPlannerRotationTarget(double orientation, double percentage)
    {
        this.orientation = orientation;
        this.percentage = percentage;
    }
}