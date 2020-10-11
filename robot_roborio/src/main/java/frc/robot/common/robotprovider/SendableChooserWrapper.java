package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class SendableChooserWrapper<V> implements ISendableChooser<V>
{
    final SendableChooser<V> wrappedObject;

    public SendableChooserWrapper()
    {
        this.wrappedObject = new SendableChooser<V>();
    }

    @Override
    public void addDefault(String name, V object)
    {
        this.wrappedObject.setDefaultOption(name, object);
    }

    @Override
    public void addObject(String name, V object)
    {
        this.wrappedObject.addOption(name, object);
    }

    @Override
    public V getSelected() {
        return this.wrappedObject.getSelected();
    }

}
