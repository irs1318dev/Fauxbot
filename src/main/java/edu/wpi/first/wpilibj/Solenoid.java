package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Solenoid extends ActuatorBase
{
    private DoubleProperty currentValueProperty;

    public Solenoid(int port)
    {
        this(0, port);
    }

    public Solenoid(int moduleNumber, int port)
    {
        ActuatorManager.set(port, this);

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
}
