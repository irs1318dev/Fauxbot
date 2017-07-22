package org.usfirst.frc.team1318.robot.common.wpilib;

import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidWrapper implements ISolenoid
{
    private final Solenoid wrappedObject;

    public SolenoidWrapper(int channel)
    {
        this.wrappedObject = new Solenoid(channel);
    }

    public SolenoidWrapper(int moduleNumber, int channel)
    {
        this.wrappedObject = new Solenoid(moduleNumber, channel);
    }

    public void set(boolean on)
    {
        this.wrappedObject.set(on);
    }
}
