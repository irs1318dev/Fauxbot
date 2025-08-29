package frc.lib.robotprovider;

public class FauxbotBooleanSubscriber implements IBooleanSubscriber
{
    private final String name;
    private boolean value;

    public FauxbotBooleanSubscriber(String name, boolean defaultValue)
    {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public boolean get()
    {
        return this.value;
    }
}
