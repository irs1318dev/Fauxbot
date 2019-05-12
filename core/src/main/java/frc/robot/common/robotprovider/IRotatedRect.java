package frc.robot.common.robotprovider;

public interface IRotatedRect
{
    ISize getSize();
    IPoint getCenter();
    double getAngle();
    double getAspectRatio();
    void set(double[] vals);
    IRect boundingRect();
    IRotatedRect clone();
    double[] getRawValues();
}