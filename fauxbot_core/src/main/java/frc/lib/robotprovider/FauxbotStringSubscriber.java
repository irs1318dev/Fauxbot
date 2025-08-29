package frc.lib.robotprovider;

public class FauxbotStringSubscriber implements IStringSubscriber
{
    private final String name;
    private String value;

    public FauxbotStringSubscriber(String name, String defaultValue)
    {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public String get()
    {
        return this.value;
    }
}
