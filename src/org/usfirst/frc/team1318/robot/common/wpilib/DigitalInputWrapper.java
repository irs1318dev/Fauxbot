package org.usfirst.frc.team1318.robot.common.wpilib;

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
