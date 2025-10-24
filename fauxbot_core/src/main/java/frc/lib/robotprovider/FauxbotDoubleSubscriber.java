package frc.lib.robotprovider;

public class FauxbotDoubleSubscriber implements IDoubleSubscriber
{
    private final String name;
    private double value;

    public FauxbotDoubleSubscriber(String name, double defaultValue)
    {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public double get()
    {
        return this.value;
    }
}
