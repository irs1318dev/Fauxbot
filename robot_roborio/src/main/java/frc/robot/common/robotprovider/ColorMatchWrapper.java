package frc.robot.common.robotprovider;

public class ColorMatchWrapper implements IColorMatch
{
    public ColorMatchWrapper()
    {
    }

    @Override
    public void addColorMatch(String name, double red, double green, double blue)
    {
    }

    @Override
    public ColorMatchResult matchClosestColor(double red, double green, double blue)
    {
        return new ColorMatchResult("none", 0);
    }
}
