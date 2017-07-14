package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogInputWrapper implements IAnalogInput
{
    private final AnalogInput wrappedObject;

    public AnalogInputWrapper(int channel)
    {
        this.wrappedObject = new AnalogInput(channel);
    }

    public double getVoltage()
    {
        return this.wrappedObject.getVoltage();
    }
}
