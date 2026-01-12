package frc.lib.robotprovider;

public class SwerveModuleState
{
    private double angle;
    private double distance;

    public SwerveModuleState(double angle, double distance)
    {
        this.angle = angle;
        this.distance = distance;
    }

    public void set(double angle, double distance)
    {
        this.angle = angle;
        this.distance = distance;
    }

    public double getAngle()
    {
        return this.angle;
    }

    public double getDistance()
    {
        return this.distance;
    }
}
