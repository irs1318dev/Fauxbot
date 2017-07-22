package org.usfirst.frc.team1318.robot.common.wpilib;

public interface IEncoder
{
    double getRate();
    double getDistance();
    int get();
    void setDistancePerPulse(double distancePerPulse);
    void reset();
}
