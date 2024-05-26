package frc.lib.robotprovider;

public class FauxbotCANCoder extends FauxbotSensorBase implements ICANCoder
{
    private final FauxbotTimer timer;
    private double value;
    private double rate;
    private double prevTime;

    public FauxbotCANCoder(int deviceNumber)
    {
        this.value = 0.0;
        this.rate = 0.0;
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), deviceNumber), this);
    }

    FauxbotCANCoder(FauxbotSensorConnection connection)
    {
        this.value = 0.0;
        this.rate = 0.0;
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(connection, this);
    }

    public double getPosition()
    {
        return this.value;
    }

    public double getVelocity()
    {
        return this.rate;
    }

    public double getAbsolutePosition()
    {
        return this.value;
    }

    public void setPosition(double newPosition)
    {
        double currTime = this.timer.get();
        double prevValue = this.value;

        this.value = newPosition;
        this.rate = (newPosition - prevValue) / (currTime - this.prevTime);

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
}
