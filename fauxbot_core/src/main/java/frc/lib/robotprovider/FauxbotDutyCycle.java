package frc.lib.robotprovider;

public class FauxbotDutyCycle extends FauxbotSensorBase implements IDutyCycle
{
    private double dutyCycle;

    public FauxbotDutyCycle(int port)
    {
        this.dutyCycle = 0.0;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, this.getClass(), port), this);
    }

    public double getOutput()
    {
        return this.dutyCycle;
    }

    public int getFrequency()
    {
        return 0;
    }

    public void set(double newValue)
    {
        this.dutyCycle = newValue;
    }
}
