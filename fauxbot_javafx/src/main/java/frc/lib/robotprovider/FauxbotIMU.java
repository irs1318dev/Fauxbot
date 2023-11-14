package frc.lib.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotIMU extends FauxbotSensorBase
{
    private final DoubleProperty angleProperty;

    public FauxbotIMU()
    {
        this.angleProperty = new SimpleDoubleProperty();
    }

    double get()
    {
        return this.angleProperty.get();
    }

    public void set(double value)
    {
        this.angleProperty.setValue(value);
    }

    public DoubleProperty getProperty()
    {
        return this.angleProperty;
    }
}