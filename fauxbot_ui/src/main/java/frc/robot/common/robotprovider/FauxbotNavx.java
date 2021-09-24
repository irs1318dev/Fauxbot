package frc.robot.common.robotprovider;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class FauxbotNavx extends FauxbotSensorBase implements INavx
{
    private final DoubleProperty angleProperty;

    public FauxbotNavx()
    {
        this.angleProperty = new SimpleDoubleProperty();
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.NavX, 0), this);
    }

    public boolean isConnected()
    {
        return true;
    }

    public double getAngle()
    {
        return -1.0 * this.angleProperty.getValue();
    }

    public double getPitch()
    {
        return -1.0 * this.angleProperty.getValue();
    }

    public double getRoll()
    {
        return -1.0 * this.angleProperty.getValue();
    }

    public double getYaw()
    {
        return -1.0 * this.angleProperty.getValue();
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
        this.angleProperty.setValue(0);
    }

    public void resetDisplacement()
    {
    }

    public void set(double value)
    {
        this.angleProperty.setValue(value);
    }

    public DoubleProperty getProperty()
    {
        return this.angleProperty;
    }
}