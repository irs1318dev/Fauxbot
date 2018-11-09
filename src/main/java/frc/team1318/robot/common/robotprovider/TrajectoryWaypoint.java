package frc.team1318.robot.common.robotprovider;

public class TrajectoryWaypoint
{
    private final double x;
    private final double y;
    private final double angle;

    public TrajectoryWaypoint(int x, int y, int angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getAngle()
    {
        return this.angle;
    }
}