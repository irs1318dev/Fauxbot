package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class MotorBase
{
    private final DoubleProperty currentPowerProperty;

    public MotorBase(int port)
    {
        this.currentPowerProperty = new SimpleDoubleProperty();
        this.currentPowerProperty.set(0.0);
        MotorManager.set(port, this);
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
