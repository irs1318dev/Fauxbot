package frc.lib.robotprovider;

public class FauxbotActuatorConnection
{
    public enum ActuatorConnector
    {
        CAN,
        PWM,
        Relay,
        PCM0A,
        PCM0B,
        PCM1A,
        PCM1B;
    }

    private final ActuatorConnector connector;
    private final int port;

    public FauxbotActuatorConnection(ActuatorConnector connector, int port)
    {
        this.connector = connector;
        this.port = port;
    }

    public int getPort()
    {
        return this.port;
    }

    public ActuatorConnector getConnector()
    {
        return this.connector;
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof FauxbotActuatorConnection))
        {
            return false;
        }

        FauxbotActuatorConnection otherConnection = (FauxbotActuatorConnection)other;
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