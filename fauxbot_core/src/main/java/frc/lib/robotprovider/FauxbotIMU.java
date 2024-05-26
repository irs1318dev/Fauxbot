package frc.lib.robotprovider;

public class FauxbotIMU extends FauxbotSensorBase
{
    private double angle;

    public FauxbotIMU()
    {
        this.angle = 0.0;
    }

    public double get()
    {
        synchronized (this)
        {
            return this.angle;
        }
    }

    public void set(double value)
    {
        synchronized (this)
        {
            this.angle = value;
        }
    }
}