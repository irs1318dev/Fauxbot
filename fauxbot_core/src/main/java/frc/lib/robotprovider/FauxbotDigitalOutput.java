package frc.lib.robotprovider;

public class FauxbotDigitalOutput extends FauxbotSensorBase implements IDigitalOutput
{
    private boolean isSet;

    public FauxbotDigitalOutput(int port)
    {
        this.isSet = false;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    /**
     * gets the current value of the sensor
     * @return true if the sensor is set (closed), false otherwise (open)
     */
    public boolean get()
    {
        synchronized (this)
        {
            return this.isSet;
        }
    }

    public void set(boolean newValue)
    {
        synchronized (this)
        {
            this.isSet = newValue;
        }
    }
}
