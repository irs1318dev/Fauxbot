package frc.robot.common.robotprovider;

public interface IPoint
{
    double getX();
    double getY();
    void set(double[] vals);
    IPoint clone();
    double dot(IPoint p);
    boolean inside(IRect r);
}