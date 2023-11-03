package frc.lib.robotprovider;

public class FauxbotPigeon2 extends FauxbotSensorBase implements IPigeon2
{
    private double angle;

    public FauxbotPigeon2(int deviceNumber)
    {
        this.angle = 0.0;
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), deviceNumber), this);
    }

    public void getYawPitchRoll(double[] ypr_deg)
    {
        ypr_deg[0] = this.angle;
    }

    public void getRawGyro(double[] xyz_dps)
    {
    }

    public void setYaw(double angleDeg)
    {
        this.angle = angleDeg;
    }

    public void setYPRUpdatePeriod(int timeoutMs)
    {
    }

    public void setGyroUpdatePeriod(int timeoutMs)
    {
    }
}