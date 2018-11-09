package frc.team1318.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotEncoder extends FauxbotSensorBase implements IEncoder
{
    private final DoubleProperty valueProperty;
    private double distancePerPulse;

    public FauxbotEncoder(int channelA, int channelB)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.distancePerPulse = 1.0;

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelA), this);
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelB), null);
    }

    FauxbotEncoder(FauxbotSensorConnection connection)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.distancePerPulse = 1.0;

        FauxbotSensorManager.set(connection, this);
    }

    public double getRate()
    {
        return this.valueProperty.get();
    }

    public double getDistance()
    {
        return this.valueProperty.get() * this.distancePerPulse;
    }

    public int get()
    {
        return (int)this.valueProperty.get();
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        this.distancePerPulse = distancePerPulse;
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
