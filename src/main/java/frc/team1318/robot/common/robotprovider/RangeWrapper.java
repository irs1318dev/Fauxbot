package frc.team1318.robot.common.robotprovider;

import org.opencv.core.Range;

public class RangeWrapper implements IRange
{
    final Range wrappedObject;

    public RangeWrapper(Range wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public int getStart()
    {
        return this.wrappedObject.start;
    }

    @Override
    public int getEnd()
    {
        return this.wrappedObject.end;
    }

    @Override
    public void set(double[] vals)
    {
        this.wrappedObject.set(vals);
    }

    @Override
    public int size()
    {
        return this.wrappedObject.size();
    }

    @Override
    public boolean empty()
    {
        return this.wrappedObject.empty();
    }

    @Override
    public IRange intersection(IRange r1)
    {
        return new RangeWrapper(this.wrappedObject.intersection(OpenCVProvider.unwrap(r1)));
    }

    @Override
    public IRange shift(int delta)
    {
        return new RangeWrapper(this.wrappedObject.shift(delta));
    }

    @Override
    public IRange clone()
    {
        return new RangeWrapper(this.wrappedObject.clone());
    }
}