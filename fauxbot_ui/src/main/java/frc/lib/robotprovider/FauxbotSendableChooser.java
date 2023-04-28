package frc.lib.robotprovider;

public class FauxbotSendableChooser<V> implements ISendableChooser<V>
{
    public FauxbotSendableChooser()
    {
    }

    @Override
    public void addDefault(String name, V object)
    {
    }

    @Override
    public void addObject(String name, V object)
    {
    }

    @Override
    public V getSelected()
    {
        return (V)null;
    }
}
