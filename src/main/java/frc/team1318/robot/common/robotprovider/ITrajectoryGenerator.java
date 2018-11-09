package frc.team1318.robot.common.robotprovider;

public interface ITrajectoryGenerator
{
    PathPlan generate(TrajectoryWaypoint... waypoints);
}