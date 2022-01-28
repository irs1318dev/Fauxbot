package frc.robot.common.robotprovider;

public class FauxbotPowerDistribution implements IPowerDistribution
{
    public FauxbotPowerDistribution()
    {
    }

    public FauxbotPowerDistribution(int module, PowerDistributionModuleType moduleType)
    {
    }

    public double getBatteryVoltage()
    {
        return 0.0;
    }

    public double getCurrent(int pdpChannel)
    {
        return 0.0;
    }
}
