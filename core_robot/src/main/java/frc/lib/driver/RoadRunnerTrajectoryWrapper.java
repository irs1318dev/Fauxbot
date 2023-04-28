package frc.lib.driver;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITrajectory;
import frc.lib.robotprovider.TrajectoryState;

public class RoadRunnerTrajectoryWrapper implements ITrajectory
{
    private final Trajectory wrappedTrajectory;

    public RoadRunnerTrajectoryWrapper(Trajectory wrappedTrajectory)
    {
        this.wrappedTrajectory = wrappedTrajectory;
    }

    @Override
    public double getDuration()
    {
        return this.wrappedTrajectory.duration();
    }

    /**
     * Gets the current state of the trajectory at the provided time
     * @param time in seconds since start of trajectory
     */
    @Override
    public TrajectoryState get(double time)
    {
        Pose2d pose = this.wrappedTrajectory.get(time);
        Pose2d vel = this.wrappedTrajectory.velocity(time);
        return new TrajectoryState(
            pose.getX(),
            pose.getY(),
            pose.getHeading() * Helpers.RADIANS_TO_DEGREES,
            vel.getX(),
            vel.getY(),
            vel.getHeading() * Helpers.RADIANS_TO_DEGREES);
    }
}