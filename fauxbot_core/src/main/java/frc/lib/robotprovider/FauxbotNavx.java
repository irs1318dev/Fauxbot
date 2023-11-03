package frc.lib.robotprovider;

public class FauxbotNavx extends FauxbotSensorBase implements INavx
{
    private double angle;

    public FauxbotNavx()
    {
        this.angle = 0.0;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.NavX, this.getClass(), 0), this);
    }

    public boolean isConnected()
    {
        return true;
    }

    public double getAngle()
    {
        return -1.0 * this.angle;
    }

    public double getPitch()
    {
        return -1.0 * this.angle;
    }

    public double getRoll()
    {
        return -1.0 * this.angle;
    }

    public double getYaw()
    {
        return -1.0 * this.angle;
    }

    public double getDisplacementX()
    {
        return 0.0;
    }

    public double getDisplacementY()
    {
        return 0.0;
    }

    public double getDisplacementZ()
    {
        return 0.0;
    }

    public void reset()
    {
        this.angle = 0;
    }

    public void resetDisplacement()
    {
    }

    public void set(double value)
    {
        this.angle = value;
    }
}