package frc.team1318.robot.common.robotprovider;

public interface IRect
{
    double getX();
    double getY();
    double getWidth();
    double getHeight();
    void set(double[] vals);
    IRect clone();
    IPoint tl();
    IPoint br();
    ISize size();
    double area();
    boolean contains(IPoint p);
}