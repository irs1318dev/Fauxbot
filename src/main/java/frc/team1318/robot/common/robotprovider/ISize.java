package frc.team1318.robot.common.robotprovider;

public interface ISize
{
    double getWidth();
    double getHeight();
    void set(double[] vals);
    double area();
    ISize clone();
}