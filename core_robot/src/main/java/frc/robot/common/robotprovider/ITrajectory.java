package frc.robot.common.robotprovider;

public interface ITrajectory
{
    double getDuration();
    TrajectoryState get(double time);
}
