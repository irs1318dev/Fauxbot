package edu.wpi.first.wpilibj;

public class Solenoid
{
    private boolean currentValue;

    public Solenoid(int channel)
    {
        this(0, channel);
    }

    public Solenoid(int moduleNumber, int channel)
    {
        this.currentValue = false;
    }

    public void set(boolean on)
    {
        this.currentValue = on;
    }
}
