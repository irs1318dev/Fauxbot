package frc.lib.robotprovider;

public class FauxbotDoubleSolenoid extends FauxbotActuatorBase implements IDoubleSolenoid
{
    private DoubleSolenoidValue currentValue;

    public FauxbotDoubleSolenoid(PneumaticsModuleType moduleType, int forwardPort, int reversePort)
    {
        this(0, moduleType, forwardPort, reversePort);
    }

    public FauxbotDoubleSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int forwardPort, int reversePort)
    {
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, true), forwardPort), this);
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, false), reversePort), null);

        this.currentValue = DoubleSolenoidValue.Off;
    }

    public void set(DoubleSolenoidValue value)
    {
        synchronized (this)
        {
            this.currentValue = value;
        }
    }

    public DoubleSolenoidValue get()
    {
        synchronized (this)
        {
            return this.currentValue;
        }
    }

    private FauxbotActuatorConnection.ActuatorConnector getModule(int moduleNumber, boolean isA)
    {
        if (moduleNumber == 0)
        {
            return isA ? FauxbotActuatorConnection.ActuatorConnector.PCM0A : FauxbotActuatorConnection.ActuatorConnector.PCM0B;
        }
        else if (moduleNumber == 1)
        {
            return isA ? FauxbotActuatorConnection.ActuatorConnector.PCM1A : FauxbotActuatorConnection.ActuatorConnector.PCM1B;
        }

        throw new RuntimeException("unexpected module number: " + moduleNumber);
    }
}
