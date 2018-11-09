package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MotorBase extends ActuatorBase
{
    private final DoubleProperty currentPowerProperty;

    public MotorBase(int port)
    {
        ActuatorManager.set(port, this);

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
