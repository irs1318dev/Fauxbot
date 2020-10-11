package frc.robot.common.robotprovider;

public interface IColorMatch
{
    void addColorMatch(String name, double red, double green, double blue);
    ColorMatchResult matchClosestColor(double red, double green, double blue);
}
