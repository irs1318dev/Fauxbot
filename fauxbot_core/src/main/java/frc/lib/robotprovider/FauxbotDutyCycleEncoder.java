package frc.lib.robotprovider;

public class FauxbotDutyCycleEncoder extends FauxbotSensorBase implements IDutyCycleEncoder
{
    private double offset;
    private double distancePerRotation;

    private double dutyCycle;

    public FauxbotDutyCycleEncoder(int port)
    {
        this.dutyCycle = 0.0;
        this.offset = 0.0;
        this.distancePerRotation = 1.0;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    public double get()
    {
        return this.getAbsolutePosition() - this.offset;
    }

    public double getDistance()
    {
        return this.get() * this.distancePerRotation;
    }

    public double getAbsolutePosition()
    {
        synchronized (this)
        {
            return this.dutyCycle;
        }
    }

    public int getFrequency()
    {
        return 0;
    }

    public boolean isConnected()
    {
        return true;
    }

    public void setConnectedFrequencyThreshold(int frequency)
    {
    }

    public void setDistancePerRotation(double distancePerRotation)
    {
        this.distancePerRotation = distancePerRotation;
    }

    public void setDutyCycleRange(double min, double max)
    {
    }

    public void setInverted(boolean inverted)
    {
    }

    public void setPositionOffset(double offset)
    {
        this.offset = offset;
    }

    public void reset()
    {
        this.offset = this.dutyCycle;
    }

    public void set(double newValue)
    {
        synchronized (this)
        {
            this.dutyCycle = newValue;
        }
    }
}
