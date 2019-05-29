package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotEncoder extends FauxbotSensorBase implements IEncoder
{
    private final DoubleProperty valueProperty;
    private final FauxbotTimer timer;
    private double distancePerPulse;
    private double prevTime;
    private int currTicks;
    private double currRate;

    public FauxbotEncoder(int channelA, int channelB)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();
        this.currTicks = 0;
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
        this.currTicks = 0;
        this.currRate = 0.0;

        FauxbotSensorManager.set(connection, this);
    }

    public double getRate()
    {
        return this.currRate;
    }

    public double getDistance()
    {
        return this.currTicks * this.distancePerPulse;
    }

    public int get()
    {
        return this.currTicks;
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        this.distancePerPulse = distancePerPulse;
    }

    public void reset()
    {
        this.valueProperty.set(0.0);
        this.currTicks = 0;
        this.currRate = 0.0;
    }

    public void set(int value)
    {
        double currTime = this.timer.get();

        this.currRate = (value - this.valueProperty.get()) / (currTime - this.prevTime);
        this.currTicks = (int)value;
        this.valueProperty.set(value);

        this.prevTime = currTime;
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }

    public void setRate(double value)
    {
        double currTime = this.timer.get();
        this.currTicks += value * (currTime - this.prevTime);
        this.currRate = value;
        this.valueProperty.set(value);
        this.prevTime = currTime;
    }
}
