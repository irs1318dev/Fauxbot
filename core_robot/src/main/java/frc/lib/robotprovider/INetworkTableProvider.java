package frc.lib.robotprovider;

public interface INetworkTableProvider
{
    void startShuffleboardRecording();
    void stopShuffleboardRecording();
    IDoubleSubscriber getNumberSlider(String title, double initialValue);
    IIntegerSubscriber getIntegerSlider(String title, int initialValue);
    IBooleanSubscriber getCheckbox(String title, boolean initialValue);
    <V> ISendableChooser<V> getSendableChooser(String name);
    IDoubleSubscriber getDoubleSubscriber(String key);
    IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue);
    IBooleanSubscriber getBooleanSubscriber(String key);
    IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue);
    IIntegerSubscriber getIntegerSubscriber(String key);
    IIntegerSubscriber getIntegerSubscriber(String key, int defaultValue);
    IStringSubscriber getStringSubscriber(String key);
    IStringSubscriber getStringSubscriber(String key, String defaultValue);
}
