package frc.lib.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotSolenoid extends FauxbotActuatorBase implements ISolenoid
{
    private DoubleProperty currentValueProperty;

    public FauxbotSolenoid(PneumaticsModuleType moduleType, int port)
    {
        this(0, moduleType, port);
    }

    public FauxbotSolenoid(int moduleNumber, PneumaticsModuleType moduleType, int port)
    {
        FauxbotActuatorManager.set(new FauxbotActuatorConnection(this.getModule(moduleNumber), port), this);

        this.currentValueProperty = new SimpleDoubleProperty();
        this.currentValueProperty.set(0.0);
    }

    public void set(boolean on)
    {
        if (on)
        {
            this.currentValueProperty.set(1.0);
        }
        else
        {
            this.currentValueProperty.set(0.0);
        }
    }

    public DoubleProperty getProperty()
    {
        return this.currentValueProperty;
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