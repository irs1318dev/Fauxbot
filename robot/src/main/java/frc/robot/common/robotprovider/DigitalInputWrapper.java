package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputWrapper implements IDigitalInput
{
    private final DigitalInput wrappedObject;

    public DigitalInputWrapper(int channel)
    {
        this.wrappedObject = new DigitalInput(channel);
    }

    public boolean get()
    {
        return this.wrappedObject.get();
    }
}
