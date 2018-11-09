package frc.team1318.robot.common.robotprovider;

public class PathPlan
{
    private ITrajectory left;
    private ITrajectory right;
    private double duration;
    private double timestep;

    public PathPlan(ITrajectory left, ITrajectory right, double duration, double timestep)
    {
        this.left = left;
        this.right = right;
        this.duration = duration;
        this.timestep = timestep;
    }

    public ITrajectory getLeft()
    {
        return this.left;
    }

    public ITrajectory getRight()
    {
        return this.right;
    }

    public double getDuration()
    {
        return this.duration;
    }

    public double getTimestep()
    {
        return this.timestep;
    }
}