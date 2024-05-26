package frc.lib.robotprovider;

public class FauxbotEncoder extends FauxbotSensorBase implements IEncoder
{
    private final FauxbotTimer timer;
    private double value;
    private double rate;
    private double distancePerPulse;
    private double prevTime;

    public FauxbotEncoder(int channelA, int channelB)
    {
        this.value = 0.0;
        this.rate = 0.0;
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), channelA), this);
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), channelB), null);
    }

    FauxbotEncoder(FauxbotSensorConnection connection)
    {
        this.value = 0.0;
        this.rate = 0.0;
        this.timer = new FauxbotTimer();
        this.timer.start();
        this.distancePerPulse = 1.0;
        this.prevTime = this.timer.get();

        FauxbotSensorManager.set(connection, this);
    }

    public double getRate()
    {
        synchronized (this)
        {
            return this.rate;
        }
    }

    public double getDistance()
    {
        synchronized (this)
        {
            return this.value * this.distancePerPulse;
        }
    }

    public int get()
    {
        synchronized (this)
        {
            return (int)this.value;
        }
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        this.distancePerPulse = distancePerPulse;
    }

    public void reset()
    {
        synchronized (this)
        {
            this.value = 0.0;
            this.rate = 0.0;
            this.prevTime = this.timer.get();
        }
    }

    public void set(double newValue)
    {
        synchronized (this)
        {
            double currTime = this.timer.get();
            double prevValue = this.value;

            this.value = newValue;
            this.rate = (newValue - prevValue) / (currTime - this.prevTime);

            this.prevTime = currTime;
        }
    }

    public void setRate(double newValue)
    {
        synchronized (this)
        {
            double currTime = this.timer.get();
            double currTicks = this.value + newValue * (currTime - this.prevTime);
            this.rate = newValue;
            this.value = currTicks;
            this.prevTime = currTime;
        }
    }
}
