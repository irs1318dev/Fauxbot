package edu.wpi.first.wpilibj;

public class Encoder extends SensorBase
{
    private double distancePerPulse;

    private double currentRate;
    private double currentDistance;
    private int currentTicks;

    public Encoder(int channelA, int channelB)
    {
        this.reset();
        this.distancePerPulse = 0.0;

        SensorManager.set(channelA * 100 + channelB, this);
    }

    public double getRate()
    {
        return this.currentRate;
    }

    public double getDistance()
    {
        return this.currentDistance;
    }

    public int get()
    {
        return this.currentTicks;
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        this.distancePerPulse = distancePerPulse;
    }

    public void reset()
    {
        this.currentRate = 0.0;
        this.currentDistance = 0.0;
        this.currentTicks = 0;
    }
}
