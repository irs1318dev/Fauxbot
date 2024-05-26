package frc.lib.robotprovider;

public class FauxbotPigeon2 extends FauxbotIMU implements IPigeon2
{
    public FauxbotPigeon2(int deviceNumber)
    {
        super();

        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), deviceNumber), this);
    }

    public void getYawPitchRoll(double[] ypr_deg)
    {
        ypr_deg[0] = this.get();
    }

    public void getRawGyro(double[] xyz_dps)
    {
    }

    public void setYaw(double angleDeg)
    {
        this.set(angleDeg);
    }

    public void setGyroUpdatePeriod(int timeoutMs)
    {
    }

    public void getRollPitchYawRates(double[] xyz_dps)
    {
    }

    public void setYPRUpdateFrequency(double frequencyHz)
    {
    }

    public void setRPYRateUpdateFrequency(double frequencyHz)
    {
    }
}