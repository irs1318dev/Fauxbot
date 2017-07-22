package org.usfirst.frc.team1318.robot.common.wpilib;

public interface IPowerDistributionPanel
{
    double getBatteryVoltage();
    double getCurrent(int pdpChannel);
}
