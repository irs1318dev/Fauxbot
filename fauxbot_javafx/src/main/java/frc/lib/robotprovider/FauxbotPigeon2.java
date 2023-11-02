package frc.lib.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotPigeon2 extends FauxbotSensorBase implements IPigeon2
{
    private final DoubleProperty angleProperty;

    public FauxbotPigeon2(int deviceNumber)
    {
        this.angleProperty = new SimpleDoubleProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), deviceNumber), this);
    }

    public void getYawPitchRoll(double[] ypr_deg)
    {
        ypr_deg[0] = this.angleProperty.getValue();
    }

    public void getRawGyro(double[] xyz_dps)
    {
    }

    public void setYaw(double angleDeg)
    {
        this.angleProperty.setValue(angleDeg);
    }

    public void setYPRUpdatePeriod(int timeoutMs)
    {
    }

    public void setGyroUpdatePeriod(int timeoutMs)
    {
    }

    public DoubleProperty getProperty()
    {
        return this.angleProperty;
    }
}