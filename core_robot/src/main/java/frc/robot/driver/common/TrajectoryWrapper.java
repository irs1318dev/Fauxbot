package frc.robot.driver.common;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import frc.robot.common.Helpers;
import frc.robot.common.robotprovider.ITrajectory;
import frc.robot.common.robotprovider.TrajectoryState;

public class TrajectoryWrapper implements ITrajectory
{
    private final Trajectory wrappedTrajectory;

    public TrajectoryWrapper(Trajectory wrappedTrajectory)
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
            -pose.getY(),
            pose.getX(),
            pose.getHeading() * Helpers.RADIANS_TO_DEGREES,
            -vel.getY(),
            vel.getX(),
            vel.getHeading() * Helpers.RADIANS_TO_DEGREES);
    }
}