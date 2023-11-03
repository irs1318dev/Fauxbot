package frc.lib.robotprovider;

public class FauxbotPigeonIMU extends FauxbotSensorBase implements IPigeonIMU
{
    private double angle;

    public FauxbotPigeonIMU(int deviceNumber)
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

    public PigeonState getState()
    {
        return PigeonState.Ready;
    }

    public void enterTemperatureCalibrationMode()
    {
    }
}