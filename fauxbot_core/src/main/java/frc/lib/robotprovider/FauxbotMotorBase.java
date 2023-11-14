package frc.lib.robotprovider;

public class FauxbotMotorBase extends FauxbotActuatorBase implements IMotor
{
    protected double currentPower;

    public FauxbotMotorBase()
    {
        this.currentPower = 0.0;
    }

    public void set(double newValue)
    {
        synchronized (this)
        {
            this.currentPower = newValue;
        }
    }

    public double get()
    {
        synchronized (this)
        {
            return this.currentPower;
        }
    }
}
