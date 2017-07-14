package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSolenoidWrapper implements IDoubleSolenoid
{
    private final DoubleSolenoid wrappedObject;

    public DoubleSolenoidWrapper(int forwardChannel, int reverseChannel)
    {
        this.wrappedObject = new DoubleSolenoid(forwardChannel, reverseChannel);
    }

    public DoubleSolenoidWrapper(int moduleNumber, int forwardChannel, int reverseChannel)
    {
        this.wrappedObject = new DoubleSolenoid(moduleNumber, forwardChannel, reverseChannel);
    }

    public void set(DoubleSolenoidValue value)
    {
        Value wpilibValue = Value.kOff;
        if (value == DoubleSolenoidValue.kForward)
        {
            wpilibValue = Value.kForward;
        }
        else if (value == DoubleSolenoidValue.kReverse)
        {
            wpilibValue = Value.kReverse;
        }

        this.wrappedObject.set(wpilibValue);
    }
}
