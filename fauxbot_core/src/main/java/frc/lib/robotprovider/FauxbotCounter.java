package frc.lib.robotprovider;

public class FauxbotCounter extends FauxbotSensorBase implements ICounter
{
    private int count;

    public FauxbotCounter(int port)
    {
        this.count = 0;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    public int get()
    {
        synchronized (this)
        {
            return this.count;
        }
    }

    public void reset()
    {
        this.set(0);
    }

    public void set(int newValue)
    {
        synchronized (this)
        {
            this.count = newValue;
        }
    }
}
