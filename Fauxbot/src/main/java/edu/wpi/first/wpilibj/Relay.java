package edu.wpi.first.wpilibj;

public class Relay
{

    public Relay(int channel)
    {
        // TODO Auto-generated constructor stub
    }

    public Relay(int channel, Direction translateDirection)
    {
        // TODO Auto-generated constructor stub
    }

    public enum Value
    {
        kOn, kForward, kReverse, kOff

    }

    public enum Direction
    {
        kForward, kReverse, kBoth

    }

    public void set(Value wrappedValue)
    {
        // TODO Auto-generated method stub
        
    }

    public void setDirection(Direction translateDirection)
    {
        // TODO Auto-generated method stub
        
    }

}
