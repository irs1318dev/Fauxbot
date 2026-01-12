package frc.lib.robotprovider;

public class FauxbotDoubleArraySubscriber implements IDoubleArraySubscriber
{
    private final String name;

    public FauxbotDoubleArraySubscriber(String name)
    {
        this.name = name;
    }

    @Override
    public double[] get()
    {
        return null;
    }

    @Override
    public double getLastChange()
    {
        return 0.0;
    }

    @Override
    public DoubleArrayValue[] getValues()
    {
        return new DoubleArrayValue[0];
    }
}
