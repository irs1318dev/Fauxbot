package frc.robot.common.robotprovider;

import edu.wpi.first.networktables.NetworkTableEntry;

public class NetworkTableEntryWrapper implements INetworkTableEntry
{
    final NetworkTableEntry wrappedObject;

    NetworkTableEntryWrapper(NetworkTableEntry object)
    {
        this.wrappedObject = object;
    }

    public double getDouble(double defaultValue)
    {
        return this.wrappedObject.getDouble(defaultValue);
    }
}