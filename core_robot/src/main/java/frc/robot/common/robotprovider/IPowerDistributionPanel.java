package frc.robot.common.robotprovider;

public interface IPowerDistributionPanel
{
    double getBatteryVoltage();
    double getCurrent(int pdpChannel);
}
