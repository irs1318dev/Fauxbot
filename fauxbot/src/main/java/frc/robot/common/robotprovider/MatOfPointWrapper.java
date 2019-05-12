package frc.robot.common.robotprovider;

import java.util.List;
import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class MatOfPointWrapper implements IMatOfPoint
{
    final MatOfPoint wrappedObject;

    public MatOfPointWrapper(MatOfPoint wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public void alloc(int elemNumber)
    {
        this.wrappedObject.alloc(elemNumber);
    }

    @Override
    public void fromArray(IPoint... a)
    {
        Point[] points = new Point[a.length];
        for (int i = 0; i < a.length; i++)
        {
            points[i] = OpenCVProvider.unwrap(a[i]);
        }

        this.wrappedObject.fromArray(points);
    }

    @Override
    public IPoint[] toArray()
    {
        Point[] pointsArray = this.wrappedObject.toArray();
        PointWrapper[] result = new PointWrapper[pointsArray.length];
        for (int i = 0; i < pointsArray.length; i++)
        {
            result[i] = new PointWrapper(pointsArray[i]);
        }

        return result;
    }

    @Override
    public void fromList(List<IPoint> lp)
    {
        List<Point> points = new ArrayList<Point>(lp.size());
        for (int i = 0; i < lp.size(); i++)
        {
            points.add(OpenCVProvider.unwrap(lp.get(i)));
        }

        this.wrappedObject.fromList(points);
    }

    @Override
    public List<IPoint> toList()
    {
        List<Point> pointsList = this.wrappedObject.toList();
        List<IPoint> result = new ArrayList<IPoint>(pointsList.size());
        for (int i = 0; i < pointsList.size(); i++)
        {
            result.add(new PointWrapper(pointsList.get(i)));
        }

        return result;
    }

    @Override
    public void release()
    {
        this.wrappedObject.release();
    }
}