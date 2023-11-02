package frc.lib.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotDutyCycle extends FauxbotSensorBase implements IDutyCycle
{
    private final DoubleProperty dutyCycleProperty;

    public FauxbotDutyCycle(int port)
    {
        this.dutyCycleProperty = new SimpleDoubleProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    public double getOutput()
    {
        return this.dutyCycleProperty.getValue();
    }

    public int getFrequency()
    {
        return 0;
    }

    public void set(double newValue)
    {
        this.dutyCycleProperty.setValue(newValue);
    }

    public DoubleProperty getProperty()
    {
        return this.dutyCycleProperty;
    }
}
