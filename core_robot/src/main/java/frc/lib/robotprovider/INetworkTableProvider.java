package frc.lib.robotprovider;

public interface INetworkTableProvider
{
    void startShuffleboardRecording();
    void stopShuffleboardRecording();
    IDoubleSubscriber getNumberSlider(String title, double initialValue);
    <V> ISendableChooser<V> getSendableChooser();
    <V> void addChooser(String name, ISendableChooser<V> chooser);
    IDoubleSubscriber getDoubleSubscriber(String key);
    IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue);
    IBooleanSubscriber getBooleanSubscriber(String key);
    IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue);
    IIntegerSubscriber getIntegerSubscriber(String key);
    IIntegerSubscriber getIntegerSubscriber(String key, int defaultValue);
    IStringSubscriber getStringSubscriber(String key);
    IStringSubscriber getStringSubscriber(String key, String defaultValue);
}
