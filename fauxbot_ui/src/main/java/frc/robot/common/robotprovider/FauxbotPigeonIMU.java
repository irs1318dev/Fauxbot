package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotPigeonIMU extends FauxbotSensorBase implements IPigeonIMU
{
    private final DoubleProperty angleProperty;

    public FauxbotPigeonIMU(int deviceNumber)
    {
        this.angleProperty = new SimpleDoubleProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, deviceNumber), this);
    }

    public int getYawPitchRoll(double[] ypr_deg)
    {
        ypr_deg[0] = this.angleProperty.getValue();
        return 0;
    }

    public int setYaw(double angleDeg)
    {
        this.angleProperty.setValue(angleDeg);
        return 0;
    }

    public PigeonState getState()
    {
        return PigeonState.Ready;
    }

    public void enterTemperatureCalibrationMode()
    {
    }

    public DoubleProperty getProperty()
    {
        return this.angleProperty;
    }
}