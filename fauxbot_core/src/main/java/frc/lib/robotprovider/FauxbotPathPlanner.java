package frc.lib.robotprovider;

public class FauxbotPathPlanner implements IPathPlanner
{
    private boolean configured;

    public FauxbotPathPlanner()
    {
        this.configured = false;
    }

    @Override
    public boolean isConfigured()
    {
        return this.configured;
    }

    @Override
    public void configureRobot(
        double robotWeight,
        double robotMomentOfInertia,
        double swerveModuleWheelRadius,
        double swerveModuleMaxVelocity,
        double swerveModuleWheelCoefficientOfFriction,
        double swerveDriveGearReduction,
        MotorType swerveDriveMotorType,
        int swerveDriveMotorCount,
        double swerveDriveMotorCurrentLimit,
        double horizontalModuleCenterDistance,
        double verticalModuleCenterDistance)
    {
        this.configured = true;
    }

    @Override
    public ITrajectory loadTrajectory(String name)
    {
        return null;
    }

    @Override
    public ITrajectory loadTrajectory(String name, boolean reversed)
    {
        return null;
    }

    @Override
    public ITrajectory buildTrajectory(double maxVelocity, double maxAcceleration, double maxAngularVelocity, double maxAngularAcceleration, IPathPlannerGoal... goalPoints)
    {
        return null;
    }
}