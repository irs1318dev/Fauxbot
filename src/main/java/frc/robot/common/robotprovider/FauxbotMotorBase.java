package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotMotorBase extends FauxbotActuatorBase implements IMotor
{
    protected final DoubleProperty currentPowerProperty;

    public FauxbotMotorBase()
    {
        this.currentPowerProperty = new SimpleDoubleProperty();
        this.currentPowerProperty.set(0.0);
    }

    public void set(double newValue)
    {
        this.currentPowerProperty.set(newValue);
    }

    public double get()
    {
        return this.currentPowerProperty.get();
    }

    public DoubleProperty getProperty()
    {
        return this.currentPowerProperty;
    }
}
