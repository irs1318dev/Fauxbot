package frc.team1318.robot.common.robotprovider;

public interface ITrajectory
{
    int length();
    ISegment get(int currentSegmentIndex);
}