package frc.lib.robotprovider;

/**
 * Represents the PathPlanner library functionality
 */
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
     * @param maxAngularVelocity in degrees per second
     * @param maxAngularAcceleration in degrees per second
     * @param goalPoints the set of waypoints for translation (and any intermediate holonomic rotations)
     * @return trajectory to follow
     */
    public ITrajectory buildTrajectory(
        double maxVelocity,
        double maxAcceleration,
        double maxAngularVelocity,
        double maxAngularAcceleration,
        IPathPlannerGoal... goalPoints);
}
