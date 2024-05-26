package frc.lib.robotprovider;

public class FauxbotPigeonIMU extends FauxbotIMU implements IPigeonIMU
{
    public FauxbotPigeonIMU(int deviceNumber)
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

    public PigeonState getState()
    {
        return PigeonState.Ready;
    }

    public void enterTemperatureCalibrationMode()
    {
    }
}