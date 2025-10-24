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
    public IAlert createAlert(String text, AlertType type)
    {
        return new FauxbotAlert(text, type);
    }

    @Override
    public IField2d getField2d(String name)
    {
        return new FauxbotField2d(name);
    }

    @Override
    public IDoubleSubscriber getNumberSlider(String title, double initialValue)
    {
        return new FauxbotDoubleSubscriber(title, initialValue);
    }

    @Override
    public IIntegerSubscriber getIntegerSlider(String title, int initialValue)
    {
        return new FauxbotIntegerSubscriber(title, initialValue);
    }

    @Override
    public IBooleanSubscriber getCheckbox(String title, boolean initialValue)
    {
        return new FauxbotBooleanSubscriber(title, initialValue);
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
        return new FauxbotDoubleSubscriber(key, 0.0);
    }

    @Override
    public IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue)
    {
        return new FauxbotDoubleSubscriber(key, defaultValue);
    }

    @Override
    public IBooleanSubscriber getBooleanSubscriber(String key)
    {
        return new FauxbotBooleanSubscriber(key, false);
    }

    @Override
    public IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue)
    {
        return new FauxbotBooleanSubscriber(key, defaultValue);
    }

    @Override
    public IIntegerSubscriber getIntegerSubscriber(String key)
    {
        return new FauxbotIntegerSubscriber(key, 0);
    }

    @Override
    public IIntegerSubscriber getIntegerSubscriber(String key, int defaultValue)
    {
        return new FauxbotIntegerSubscriber(key, defaultValue);
    }

    @Override
    public IStringSubscriber getStringSubscriber(String key)
    {
        return new FauxbotStringSubscriber(key, null);
    }

    @Override
    public IStringSubscriber getStringSubscriber(String key, String defaultValue)
    {
        return new FauxbotStringSubscriber(key, defaultValue);
    }
}
