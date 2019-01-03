package frc.robot.common.robotprovider;

import org.opencv.core.Point;

public class PointWrapper implements IPoint
{
    final Point wrappedObject;

    public PointWrapper(Point wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public double getX()
    {
        return this.wrappedObject.x;
    }

    @Override
    public double getY()
    {
        return this.wrappedObject.y;
    }

    @Override
    public void set(double[] vals)
    {
        this.wrappedObject.set(vals);
    }

    @Override
    public IPoint clone()
    {
        return new PointWrapper(this.wrappedObject.clone());
    }

    @Override
    public double dot(IPoint p)
    {
        return this.wrappedObject.dot(OpenCVProvider.unwrap(p));
    }

    @Override
    public boolean inside(IRect r)
    {
        return this.wrappedObject.inside(OpenCVProvider.unwrap(r));
    }
}