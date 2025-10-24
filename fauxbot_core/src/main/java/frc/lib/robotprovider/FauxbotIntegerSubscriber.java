package frc.lib.robotprovider;

public class FauxbotIntegerSubscriber implements IIntegerSubscriber
{
    private final String name;
    private long value;

    public FauxbotIntegerSubscriber(String name, long defaultValue)
    {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public long get()
    {
        return this.value;
    }
}
