package edu.wpi.first.wpilibj;

public class DoubleSolenoid
{
    public enum Value
    {
        kOff, kForward, kReverse;
    }

    private Value currentValue;

    public DoubleSolenoid(int forwardChannel, int reverseChannel)
    {
        this(0, forwardChannel, reverseChannel);
    }

    public DoubleSolenoid(int moduleNumber, int forwardChannel, int reverseChannel)
    {
        this.currentValue = Value.kOff;
    }

    public void set(Value value)
    {
        this.currentValue = value;
    }
}
