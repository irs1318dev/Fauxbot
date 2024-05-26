package frc.lib.robotprovider;

public class FauxbotNetworkTableProvider implements INetworkTableProvider
{
    @Override
    public void startShuffleboardRecording()
    {
    }

    @Override
    public void stopShuffleboardRecording()
    {
    }

    @Override
    public IDoubleSubscriber getNumberSlider(String title, double initialValue)
    {
        return null;
    }

    @Override
    public IIntegerSubscriber getIntegerSlider(String title, int initialValue)
    {
        return null;
    }

    @Override
    public IBooleanSubscriber getCheckbox(String title, boolean initialValue)
    {
        return null;
    }

    /**
     * Add a sendable chooser to the smart dashboard
     */
    @Override
    public <V> ISendableChooser<V> getSendableChooser(String name)
    {
        return new FauxbotSendableChooser<V>();
    }

    @Override
    public IDoubleSubscriber getDoubleSubscriber(String key)
    {
        return null;
    }

    @Override
    public IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue)
    {
        return null;
    }

    @Override
    public IBooleanSubscriber getBooleanSubscriber(String key)
    {
        return null;
    }

    @Override
    public IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue)
    {
        return null;
    }

    @Override
    public IIntegerSubscriber getIntegerSubscriber(String key)
    {
        return null;
    }

    @Override
    public IIntegerSubscriber getIntegerSubscriber(String key, int defaultVAlue)
    {
        return null;
    }

    @Override
    public IStringSubscriber getStringSubscriber(String key)
    {
        return null;
    }

    @Override
    public IStringSubscriber getStringSubscriber(String key, String defaultValue)
    {
        return null;
    }
}
