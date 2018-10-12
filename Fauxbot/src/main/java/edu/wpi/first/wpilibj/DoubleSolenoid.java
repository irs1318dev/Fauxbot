package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DoubleSolenoid extends ActuatorBase
{
    public enum Value
    {
        kOff, kForward, kReverse;
    }

    private DoubleProperty currentValueProperty;

    public DoubleSolenoid(int forwardPort, int reversePort)
    {
        this(0, forwardPort, reversePort);
    }

    public DoubleSolenoid(int moduleNumber, int forwardPort, int reversePort)
    {
        ActuatorManager.set(forwardPort, this);
        ActuatorManager.set(reversePort, null);

        this.currentValueProperty = new SimpleDoubleProperty();
        this.currentValueProperty.set(0.0);
    }

    public void set(Value value)
    {
        if (value == Value.kOff)
        {
            this.currentValueProperty.set(0.0);
        }
        else if (value == Value.kForward)
        {
            this.currentValueProperty.set(1.0);
        }
        else if (value == Value.kReverse)
        {
            this.currentValueProperty.set(-1.0);
        }
    }

    public Value get()
    {
        if (this.currentValueProperty.get() > 0.0)
        {
            return Value.kForward;
        }

        if (this.currentValueProperty.get() < 0.0)
        {
            return Value.kReverse;
        }

        return Value.kOff;
    }

    public DoubleProperty getProperty()
    {
        return this.currentValueProperty;
    }
}
