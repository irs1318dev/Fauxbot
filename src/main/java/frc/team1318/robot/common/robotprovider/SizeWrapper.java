package frc.team1318.robot.common.robotprovider;

import org.opencv.core.Size;

public class SizeWrapper implements ISize
{
    final Size wrappedObject;

    public SizeWrapper(Size wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public double getWidth()
    {
        return this.wrappedObject.width;
    }

    @Override
    public double getHeight()
    {
        return this.wrappedObject.height;
    }

    @Override
    public void set(double[] vals)
    {
        this.wrappedObject.set(vals);
    }

    @Override
    public double area()
    {
        return this.wrappedObject.area();
    }

    @Override
    public ISize clone()
    {
        return new SizeWrapper(this.wrappedObject.clone());
    }
}