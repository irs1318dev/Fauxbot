package frc.lib.robotprovider;

public class FauxbotAnalogInput extends FauxbotSensorBase implements IAnalogInput
{
    private double value;

    public FauxbotAnalogInput(int port)
    {
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.AnalogInput, this.getClass(), port), this);
    }

    /**
     * gets the current value of the sensor
     * @return the current measurement from the sensor
     */
    public double get()
    {
        return this.value;
    }

    public void set(double newValue)
    {
        this.value = newValue;
    }

    public double getVoltage()
    {
        return this.value;
    }
}
