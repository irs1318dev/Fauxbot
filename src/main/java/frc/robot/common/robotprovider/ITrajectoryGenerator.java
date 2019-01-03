package frc.robot.common.robotprovider;

public interface ITrajectoryGenerator
{
    PathPlan generate(TrajectoryWaypoint... waypoints);
}