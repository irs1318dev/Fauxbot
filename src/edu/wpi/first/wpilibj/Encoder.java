package edu.wpi.first.wpilibj;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Encoder extends SensorBase
{
    private final DoubleProperty valueProperty;

    public Encoder(int channelA, int channelB)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);

        SensorManager.set(channelA, this);
        SensorManager.set(channelB, null);
    }

    public double getRate()
    {
        return this.valueProperty.get();
    }

    public double getDistance()
    {
        return this.valueProperty.get();
    }

    public int get()
    {
        return (int)this.valueProperty.get();
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
    }

    public void reset()
    {
        this.set(0.0);
    }

    public void set(double value)
    {
        this.valueProperty.set(value);
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }
}
