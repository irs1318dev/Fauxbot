package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotDoubleSolenoid extends FauxbotActuatorBase implements IDoubleSolenoid
{
    private DoubleProperty currentValueProperty;

    public FauxbotDoubleSolenoid(int forwardPort, int reversePort)
    {
        this(0, forwardPort, reversePort);
    }

    public FauxbotDoubleSolenoid(int moduleNumber, int forwardPort, int reversePort)
    {
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, true), forwardPort), this);
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber, false), reversePort), null);

        this.currentValueProperty = new SimpleDoubleProperty();
        this.currentValueProperty.set(0.0);
    }

    public void set(DoubleSolenoidValue value)
    {
        if (value == DoubleSolenoidValue.Off)
        {
            this.currentValueProperty.set(0.0);
        }
        else if (value == DoubleSolenoidValue.Forward)
        {
            this.currentValueProperty.set(1.0);
        }
        else if (value == DoubleSolenoidValue.Reverse)
        {
            this.currentValueProperty.set(-1.0);
        }
    }

    public DoubleSolenoidValue get()
    {
        if (this.currentValueProperty.get() > 0.0)
        {
            return DoubleSolenoidValue.Forward;
        }

        if (this.currentValueProperty.get() < 0.0)
        {
            return DoubleSolenoidValue.Reverse;
        }

        return DoubleSolenoidValue.Off;
    }

    public DoubleProperty getProperty()
    {
        return this.currentValueProperty;
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
