package frc.lib.robotprovider;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FauxbotCounter extends FauxbotSensorBase implements ICounter
{
    private final IntegerProperty countProperty;

    public FauxbotCounter(int port)
    {
        this.countProperty = new SimpleIntegerProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    public int get()
    {
        return this.countProperty.getValue();
    }

    public void reset()
    {
        this.countProperty.setValue(0);
    }

    public void set(int newValue)
    {
        this.countProperty.setValue(newValue);
    }

    public IntegerProperty getProperty()
    {
        return this.countProperty;
    }
}
