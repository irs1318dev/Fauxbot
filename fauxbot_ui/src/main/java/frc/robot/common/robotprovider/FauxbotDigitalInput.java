package frc.robot.common.robotprovider;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FauxbotDigitalInput extends FauxbotSensorBase implements IDigitalInput
{
    private final BooleanProperty isSetProperty;

    public FauxbotDigitalInput(int port)
    {
        this.isSetProperty = new SimpleBooleanProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, port), this);
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
