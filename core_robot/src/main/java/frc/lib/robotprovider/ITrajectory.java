package frc.lib.robotprovider;

/**
 * Represents a trajectory for motion planning
 */
public interface ITrajectory
{
    /**
     * Retrieve the duration of this trajectory
     * @return duration in seconds
     */
    double getDuration();

    /**
     * Retrieve the desired state at the provided time into the trajectory
     * @param time in seconds
     * @return desired state when following the trajectory
     */
    TrajectoryState get(double time);
}
