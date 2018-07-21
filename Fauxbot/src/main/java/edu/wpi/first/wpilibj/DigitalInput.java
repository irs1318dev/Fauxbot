package edu.wpi.first.wpilibj;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DigitalInput extends SensorBase
{
    private final BooleanProperty isSetProperty;

    public DigitalInput(int port)
    {
        this.isSetProperty = new SimpleBooleanProperty();
        SensorManager.set(port, this);
    }

    /**
     * gets the current value of the sensor
     * @return true if the sensor is set (closed), false otherwise (open)
     */
    public boolean get()
    {
        return this.isSetProperty.getValue();
    }

    public void set(boolean newValue)
    {
        this.isSetProperty.setValue(newValue);
    }

    public BooleanProperty getProperty()
    {
        return this.isSetProperty;
    }
}
