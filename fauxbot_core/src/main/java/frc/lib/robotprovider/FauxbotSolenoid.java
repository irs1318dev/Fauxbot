package frc.lib.robotprovider;

public class FauxbotSolenoid extends FauxbotActuatorBase implements ISolenoid
{
    private boolean currentValue;

    public FauxbotSolenoid(PneumaticsModuleType moduleType, int port)
    {
        this(0, moduleType, port);
    }

    public FauxbotSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int port)
    {
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber), port), this);

        this.currentValue = false;
    }

    public void set(boolean on)
    {
        synchronized (this)
        {
            this.currentValue = on;
        }
    }

    public boolean get()
    {
        synchronized (this)
        {
            return this.currentValue;
        }
    }

    private FauxbotActuatorConnection.ActuatorConnector getModule(int moduleNumber)
    {
        if (moduleNumber == 0)
        {
            return FauxbotActuatorConnection.ActuatorConnector.PCM0A;
        }
        else if (moduleNumber == 1)
        {
            return FauxbotActuatorConnection.ActuatorConnector.PCM1A;
        }

        throw new RuntimeException("unexpected module number: " + moduleNumber);
    }
}