package org.usfirst.frc.team1318.robot.common.wpilibmocks;

public interface IPowerDistributionPanel
{
    double getBatteryVoltage();
    double getCurrent(int pdpChannel);
}
