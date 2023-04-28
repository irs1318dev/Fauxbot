package frc.lib.robotprovider;

public interface ITrajectory
{
    double getDuration();
    TrajectoryState get(double time);
}
