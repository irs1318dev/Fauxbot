package frc.lib.robotprovider;

public interface IPathPlanner
{
    /**
     * Load a trajectory that was created by the external PathPlanner tool
     * @param name of the file (without the .path)
     * @param maxVelocity in inches per second
     * @param maxAcceleration in inches per second squared
     * @return trajectory to follow
     */
    public ITrajectory loadTrajectory(String name, double maxVelocity, double maxAcceleration);

    /**
     * Load a trajectory that was created by the external PathPlanner tool
     * @param name of the file (without the .path)
     * @param maxVelocity in inches per second
     * @param maxAcceleration in inches per second squared
     * @param reversed whether to return the path in reversed direction
     * @return trajectory to follow
     */
    public ITrajectory loadTrajectory(String name, double maxVelocity, double maxAcceleration, boolean reversed);

    /**
     * Build a trajectory involving the provided waypoints with the provided velocity/acceleration constraints
     * @param maxVelocity in inches per second
     * @param maxAcceleration in inches per second
     * @param firstWaypoint the starting position
     * @param secondWaypoint the second waypoint
     * @param otherWaypoints any subsequent waypoints
     * @return trajectory to follow
     */
    public ITrajectory buildTrajectory(
        double maxVelocity,
        double maxAcceleration,
        PathPlannerWaypoint firstWaypoint,
        PathPlannerWaypoint secondWaypoint,
        PathPlannerWaypoint... otherWaypoints);
}
