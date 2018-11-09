package frc.team1318.robot.common.robotprovider;

import org.opencv.core.Rect;

public class RectWrapper implements IRect
{
    final Rect wrappedObject;

    public RectWrapper(Rect wrappedObject)
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
    public IRect clone()
    {
        return new RectWrapper(this.wrappedObject.clone());
    }

    @Override
    public IPoint tl()
    {
        return new PointWrapper(this.wrappedObject.tl());
    }

    @Override
    public IPoint br()
    {
        return new PointWrapper(this.wrappedObject.br());
    }

    @Override
    public ISize size()
    {
        return new SizeWrapper(this.wrappedObject.size());
    }

    @Override
    public double area()
    {
        return this.wrappedObject.area();
    }

    @Override
    public boolean contains(IPoint p)
    {
        return this.wrappedObject.contains(OpenCVProvider.unwrap(p));
    }
}