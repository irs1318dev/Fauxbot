package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class AnalogInput extends SensorBase
{
    private final DoubleProperty valueProperty;

    public AnalogInput(int port)
    {
        this.valueProperty = new SimpleDoubleProperty();
        SensorManager.set(port, this);
    }

    /**
     * gets the current value of the sensor
     * @return the current measurement from the sensor
     */
    public double get()
    {
        return this.valueProperty.doubleValue();
    }

    public void set(double newValue)
    {
        this.valueProperty.setValue(newValue);
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }
}
