package frc.robot.common.robotprovider;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetworkTableProvider implements INetworkTableProvider
{
    @Override
    public INetworkTableEntry getNumberSlider(String title, double initialValue)
    {
        SmartDashboard.setDefaultNumber(title, initialValue);
        return new NetworkTableEntryWrapper(SmartDashboard.getEntry(title));
    }

    @Override
    public <V> ISendableChooser<V> getSendableChooser()
    {
        return new SendableChooserWrapper<V>();
    }

    @Override
    public <V> void addChooser(String name, ISendableChooser<V> chooser)
    {
        SendableChooserWrapper<V> wrappedChooser = (SendableChooserWrapper<V>)chooser;
        SmartDashboard.putData(name, wrappedChooser.wrappedObject);
    }

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