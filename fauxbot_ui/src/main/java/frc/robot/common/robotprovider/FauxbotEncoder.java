package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotEncoder extends FauxbotSensorBase implements IEncoder
{
    private final DoubleProperty valueProperty;
    private final DoubleProperty rateProperty;
    private final FauxbotTimer timer;
    private double distancePerPulse;
    private double prevTime;

    public FauxbotEncoder(int channelA, int channelB)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.rateProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelA), this);
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, channelB), null);
    }

    FauxbotEncoder(FauxbotSensorConnection connection)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.rateProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(connection, this);
    }

    public double getRate()
    {
        return this.rateProperty.get();
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
        this.valueProperty.set(0.0);
        this.rateProperty.set(0.0);
        this.prevTime = this.timer.get();
    }

    public void set(double value)
    {
        double currTime = this.timer.get();
        double prevValue = this.valueProperty.get();

        this.valueProperty.set(value);
        this.rateProperty.set((value - prevValue) / (currTime - this.prevTime));

        this.prevTime = currTime;
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }

    public void setRate(double value)
    {
        double currTime = this.timer.get();
        double currTicks = this.valueProperty.get() + value * (currTime - this.prevTime);
        this.rateProperty.set(value);
        this.valueProperty.set(currTicks);
        this.prevTime = currTime;
    }
}
