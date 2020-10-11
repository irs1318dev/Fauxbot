package frc.robot.common.robotprovider;

public class FauxbotNetworkTableProvider implements INetworkTableProvider
{
    @Override
    public INetworkTableEntry getNumberSlider(String title, double initialValue)
    {
        return null;
    }

    /**
     * Add a sendable chooser to the smart dashboard
     */
    @Override
    public <V> void addChooser(String name, ISendableChooser<V> chooser)
    {
    }

    @Override
    public <V> ISendableChooser<V> getSendableChooser()
    {
        return new FauxbotSendableChooser<V>();
    }

    @Override
    public double getSmartDashboardNumber(String key)
    {
        return 0.0;
    }

    @Override
    public boolean getSmartDashboardBoolean(String key)
    {
        return false;
    }

    @Override
    public String getSmartDashboardString(String key)
    {
        return "";
    }
}
