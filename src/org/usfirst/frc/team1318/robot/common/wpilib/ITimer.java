package org.usfirst.frc.team1318.robot.common.wpilib;

public interface ITimer
{
    void start();
    void stop();
    double get();
    void reset();
}
