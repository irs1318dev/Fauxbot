package frc.lib.robotprovider;

/**
 * A representation of a point in 2 dimensions
 */
public class Point2d
{
    public double x;
    public double y;

    /**
     * A point in 2 dimensions.
     * @param x typically in inches
     * @param y typically in inches
     */
    public Point2d(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return String.format("(%.2f, %.2f)", this.x, this.y);
    }
}
