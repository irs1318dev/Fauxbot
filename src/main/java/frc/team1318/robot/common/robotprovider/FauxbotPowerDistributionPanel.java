package frc.team1318.robot.common.robotprovider;

public class FauxbotPowerDistributionPanel implements IPowerDistributionPanel
{
    public FauxbotPowerDistributionPanel()
    {
    }

    public FauxbotPowerDistributionPanel(int module)
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
