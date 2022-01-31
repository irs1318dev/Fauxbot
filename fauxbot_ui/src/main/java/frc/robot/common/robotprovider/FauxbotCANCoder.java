package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotCANCoder extends FauxbotSensorBase implements ICANCoder
{
    private final DoubleProperty valueProperty;
    private final DoubleProperty rateProperty;
    private final FauxbotTimer timer;
    private double prevTime;

    public FauxbotCANCoder(int deviceNumber)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.rateProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, deviceNumber), this);
    }

    FauxbotCANCoder(FauxbotSensorConnection connection)
    {
        this.valueProperty = new SimpleDoubleProperty(0.0);
        this.rateProperty = new SimpleDoubleProperty(0.0);
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(connection, this);
    }

    public double getPosition()
    {
        return this.valueProperty.get();
    }

    public double getVelocity()
    {
        return this.rateProperty.get();
    }

    public double getAbsolutePosition()
    {
        return this.valueProperty.get();
    }

    public void setPosition(double newPosition)
    {
        double currTime = this.timer.get();
        double prevValue = this.valueProperty.get();

        this.valueProperty.set(newPosition);
        this.rateProperty.set((newPosition - prevValue) / (currTime - this.prevTime));

        this.prevTime = currTime;
    }

    public void configSensorDirection(boolean clockwisePositive)
    {
    }

    public void configAbsoluteRange(boolean useZeroToThreeSixty)
    {
    }

    public void configMagnetOffset(double offsetDegrees)
    {
    }

    public DoubleProperty getProperty()
    {
        return this.valueProperty;
    }
}
