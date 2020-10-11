package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.DigitalOutput;

public class DigitalOutputWrapper implements IDigitalOutput
{
    private final DigitalOutput wrappedObject;

    public DigitalOutputWrapper(int channel)
    {
        this.wrappedObject = new DigitalOutput(channel);
    }

    public void set(boolean value)
    {
        this.wrappedObject.set(value);
    }
}
