package frc.team1318.robot.common.robotprovider;

public interface ITimer
{
    void start();
    void stop();
    double get();
    void reset();
}
