package frc.lib.robotprovider;

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

    public double getTotalCurrent()
    {
        return 0.0;
    }

    public double getTotalEnergy()
    {
        return 0.0;
    }

    public double getTotalPower()
    {
        return 0.0;
    }

    public double getTemperature()
    {
        return 0.0;
    }

    public void setSwitchableChannel(boolean enabled)
    {
    }
}
