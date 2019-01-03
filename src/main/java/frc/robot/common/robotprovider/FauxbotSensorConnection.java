package frc.robot.common.robotprovider;

public class FauxbotSensorConnection
{
    public enum SensorConnector
    {
        DigitalInput,
        AnalogInput,
        CAN;
    }

    private final SensorConnector connector;
    private final int port;

    public FauxbotSensorConnection(SensorConnector connector, int port)
    {
        this.connector = connector;
        this.port = port;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof FauxbotSensorConnection))
        {
            return false;
        }

        FauxbotSensorConnection otherConnection = (FauxbotSensorConnection)other;
        return this.connector == otherConnection.connector && this.port == otherConnection.port;
    }

    @Override
    public int hashCode()
    {
        return (this.connector.hashCode() * 0x10000) + this.port;
    }

    @Override
    public String toString()
    {
        return this.connector + ": " + this.port;
    }
}