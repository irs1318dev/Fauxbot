package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Counter;

public class CounterWrapper implements ICounter
{
    private final Counter wrappedObject;

    public CounterWrapper(int channel)
    {
        this.wrappedObject = new Counter(channel);
    }

    public int get()
    {
        return this.wrappedObject.get();
    }

    public void reset()
    {
        this.wrappedObject.reset();
    }
}
