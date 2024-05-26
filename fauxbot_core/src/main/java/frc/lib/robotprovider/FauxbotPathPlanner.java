package frc.lib.robotprovider;

public class FauxbotPathPlanner implements IPathPlanner
{
    public FauxbotPathPlanner()
    {
    }

    @Override
    public ITrajectory loadTrajectory(String name, double maxVelocity, double maxAcceleration)
    {
        return null;
    }

    @Override
    public ITrajectory loadTrajectory(String name, double maxVelocity, double maxAcceleration, boolean reversed)
    {
        return null;
    }

    @Override
    public ITrajectory buildTrajectory(double maxVelocity, double maxAcceleration, double maxAngularVelocity, double maxAngularAcceleration, IPathPlannerGoal... goalPoints)
    {
        return null;
    }
}