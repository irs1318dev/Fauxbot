package org.usfirst.frc.team1318.robot.common.wpilibmocks;

public interface IEncoder
{
    double getRate();
    double getDistance();
    int get();
    void setDistancePerPulse(double distancePerPulse);
    void reset();
}
