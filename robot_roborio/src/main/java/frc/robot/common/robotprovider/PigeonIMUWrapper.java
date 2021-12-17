package frc.robot.common.robotprovider;

import com.ctre.phoenix.sensors.*;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

public class PigeonIMUWrapper implements IPigeonIMU
{
    private final PigeonIMU wrappedObject;

    public PigeonIMUWrapper(int deviceNumber)
    {
        this.wrappedObject = new PigeonIMU(deviceNumber);
    }

    public int getYawPitchRoll(double[] ypr_deg)
    {
        return this.wrappedObject.getYawPitchRoll(ypr_deg).value;
    }

    public int setYaw(double angleDeg)
    {
        return this.wrappedObject.setYaw(angleDeg).value;
    }

    public PigeonState getState()
    {
        PigeonIMU.PigeonState state = this.wrappedObject.getState();
        return PigeonState.getValue(state.value);
    }

    public void enterTemperatureCalibrationMode()
    {
        this.wrappedObject.enterCalibrationMode(CalibrationMode.Temperature);
    }
}