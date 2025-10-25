package frc.lib.robotprovider;

public class FauxbotCANRange extends FauxbotSensorBase implements ICANRange
{
    public FauxbotCANRange(int deviceNumber)
    {
        this(deviceNumber, null);
    }

    public FauxbotCANRange(int deviceNumber, String canbus)
    {
        FauxbotSensorManager.set(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), deviceNumber), this);
    }

    FauxbotCANRange(FauxbotSensorConnection connection)
    {
        FauxbotSensorManager.set(connection, this);
    }

    public void setFovParams(double centerX, double centerY, double rangeX, double rangeY)
    {
    }

    public void setProximityParams(double proximityThreshold, double proximityHysteresis, double minSignalStrengthForValidMeasurement)
    {
    }

    public void setToFParams(double updateFrequency, CANRangeUpdateMode updateMode)
    {
    }

    public double getDistance()
    {
        return 0.0;
    }

    public double getDistanceStdDev()
    {
        return 0.0;
    }

    public Boolean getIsDetected()
    {
        return null;
    }
}
