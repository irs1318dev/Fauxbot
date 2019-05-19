package frc.robot.common.robotprovider;

public class FauxbotNetworkTableProvider implements INetworkTableProvider
{
    public double getSmartDashboardNumber(String key)
    {
        return 0.0;
    }

    public boolean getSmartDashboardBoolean(String key)
    {
        return false;
    }

    public String getSmartDashboardString(String key)
    {
        return "";
    }
}
