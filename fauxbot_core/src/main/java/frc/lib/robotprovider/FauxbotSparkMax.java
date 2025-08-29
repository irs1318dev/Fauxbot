package frc.lib.robotprovider;

public class FauxbotSparkMax extends FauxbotSparkBase implements ISparkMax
{
    public FauxbotSparkMax(int deviceId, SparkMotorType motorType)
    {
        super(deviceId, motorType);
    }
}
