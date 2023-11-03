package frc.lib.robotprovider;

public class FauxbotDoubleSolenoid extends FauxbotActuatorBase implements IDoubleSolenoid
{
    private double currentValue;

    public FauxbotDoubleSolenoid(PneumaticsModuleType moduleType, int forwardPort, int reversePort)
    {
        this(0, moduleType, forwardPort, reversePort);
    }

    public FauxbotDoubleSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int forwardPort, int reversePort)
    {
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, true), forwardPort), this);
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, false), reversePort), null);

        this.currentValue = 0.0;
    }

    public void set(DoubleSolenoidValue value)
    {
        if (value == DoubleSolenoidValue.Off)
        {
            this.currentValue = 0.0;
        }
        else if (value == DoubleSolenoidValue.Forward)
        {
            this.currentValue = 1.0;
        }
        else if (value == DoubleSolenoidValue.Reverse)
        {
            this.currentValue = -1.0;
        }
    }

    public DoubleSolenoidValue get()
    {
        if (this.currentValue > 0.0)
        {
            return DoubleSolenoidValue.Forward;
        }

        if (this.currentValue < 0.0)
        {
            return DoubleSolenoidValue.Reverse;
        }

        return DoubleSolenoidValue.Off;
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
