package frc.robot.common.robotprovider;

public interface ITrajectory
{
    int length();
    ISegment get(int currentSegmentIndex);
}