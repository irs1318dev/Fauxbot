package frc.lib.robotprovider;

public class FauxbotDigitalInput extends FauxbotSensorBase implements IDigitalInput
{
    private boolean isSet;

    public FauxbotDigitalInput(int port)
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
