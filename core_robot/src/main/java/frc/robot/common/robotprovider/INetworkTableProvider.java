package frc.robot.common.robotprovider;

public interface INetworkTableProvider
{
    INetworkTableEntry getNumberSlider(String title, double initialValue);
    <V> ISendableChooser<V> getSendableChooser();
    <V> void addChooser(String name, ISendableChooser<V> chooser);
    double getSmartDashboardNumber(String key);
    boolean getSmartDashboardBoolean(String key);
    String getSmartDashboardString(String key);
}
