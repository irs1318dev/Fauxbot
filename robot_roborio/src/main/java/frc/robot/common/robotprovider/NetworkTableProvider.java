package frc.robot.common.robotprovider;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkTableProvider implements INetworkTableProvider
{
    @Override
    public double getSmartDashboardNumber(String key)
    {
        NetworkTableEntry entry = SmartDashboard.getEntry(key);
        if (entry != null && entry.getType() == NetworkTableType.kDouble)
        {
            return entry.getDouble(0.0);
        }

        return 0.0;
    }

    @Override
    public boolean getSmartDashboardBoolean(String key)
    {
        NetworkTableEntry entry = SmartDashboard.getEntry(key);
        if (entry != null && entry.getType() == NetworkTableType.kBoolean)
        {
            return entry.getBoolean(false);
        }

        return false;
    }

    @Override
    public String getSmartDashboardString(String key)
    {
        NetworkTableEntry entry = SmartDashboard.getEntry(key);
        if (entry != null && entry.getType() == NetworkTableType.kString)
        {
            return entry.getString("");
        }

        return "";
    }
}