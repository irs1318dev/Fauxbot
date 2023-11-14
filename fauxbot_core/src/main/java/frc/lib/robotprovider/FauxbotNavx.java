package frc.lib.robotprovider;

public class FauxbotNavx extends FauxbotIMU implements INavx
{
    public FauxbotNavx()
    {
        super();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.NavX, this.getClass(), 0), this);
    }

    public boolean isConnected()
    {
        return true;
    }

    public double getAngle()
    {
        return -1.0 * this.get();
    }

    public double getPitch()
    {
        return -1.0 * this.get();
    }

    public double getRoll()
    {
        return -1.0 * this.get();
    }

    public double getYaw()
    {
        return -1.0 * this.get();
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
        this.set(0);
    }

    public void resetDisplacement()
    {
    }
}