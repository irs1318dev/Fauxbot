package frc.lib.robotprovider;

public class FauxbotSensorConnection
{
    public enum SensorConnector
    {
        DigitalInput,
        AnalogInput,
        CAN,
        NavX;
    }

    private final SensorConnector connector;
    private final Class<?> type;
    private final int port;

    public FauxbotSensorConnection(SensorConnector connector, Class<?> type, int port)
    {
        this.connector = connector;
        this.type = type;
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
        if (this.connector != otherConnection.connector || this.port != otherConnection.port)
        {
            return false;
        }

        if (this.connector == SensorConnector.CAN && !type.equals(otherConnection.type))
        {
            return false;
        }

        return true;
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