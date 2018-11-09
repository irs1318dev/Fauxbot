package frc.team1318.robot.common.robotprovider;

public interface IPowerDistributionPanel
{
    double getBatteryVoltage();
    double getCurrent(int pdpChannel);
}
