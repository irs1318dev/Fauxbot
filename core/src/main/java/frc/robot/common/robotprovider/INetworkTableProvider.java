package frc.robot.common.robotprovider;

public interface INetworkTableProvider
{
    double getSmartDashboardNumber(String key);
    boolean getSmartDashboardBoolean(String key);
    String getSmartDashboardString(String key);
}
