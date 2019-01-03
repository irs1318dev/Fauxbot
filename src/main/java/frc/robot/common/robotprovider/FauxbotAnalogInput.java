package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotAnalogInput extends FauxbotSensorBase implements IAnalogInput
{
    private final DoubleProperty valueProperty;

    public FauxbotAnalogInput(int port)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.AnalogInput, port), this);
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

    public double getVoltage()
    {
        return this.valueProperty.doubleValue();
    }
}
