package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotEncoder extends FauxbotSensorBase implements IEncoder
{
    private final DoubleProperty valueProperty;
    private final FauxbotTimer timer;
    private double distancePerPulse;
    private double prevTime;
    private double currRate;

    public FauxbotEncoder(int channelA, int channelB)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();
        this.currRate = 0.0;

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelA), this);
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelB), null);
    }

    FauxbotEncoder(FauxbotSensorConnection connection)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();
        this.currRate = 0.0;

        FauxbotSensorManager.set(connection, this);
    }

    public double getRate()
    {
        return this.currRate;
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
        this.currRate = 0.0;
    }

    public void set(double value)
    {
        double currTime = this.timer.get();

        this.currRate = (value - this.valueProperty.get()) / (currTime - this.prevTime);
        this.valueProperty.set(value);

        this.prevTime = currTime;
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }
}
