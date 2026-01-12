package frc.lib.robotprovider;

import frc.lib.helpers.Triple;

public class FauxbotSwervePoseEstimator implements ISwervePoseEstimator
{
    public static final FauxbotSwervePoseEstimator instance = new FauxbotSwervePoseEstimator();

    private FauxbotSwervePoseEstimator()
    {
    }

    @Override
    public boolean isConfigured()
    {
        return false;
    }

    @Override
    public void configure(
        Point2d[] swerveModuleLocations,
        SwerveModuleState[] swerveModuleStates,
        Pose2d initialPose,
        Triple<Double, Double, Double> odometryStdDevs,
        Triple<Double, Double, Double> visionMeasurementStdDevs)
    {
    }

    @Override
    public frc.lib.robotprovider.Pose2d get()
    {
        return new frc.lib.robotprovider.Pose2d(0, 0, 0);
    }

    @Override
    public void updateVision(
        frc.lib.robotprovider.Pose2d visionPose,
        double timestamp,
        Triple<Double, Double, Double> stdDevs)
    {
    }

    @Override
    public void updateOdometry(
        SwerveModuleState[] moduleStates,
        double gyroAngle,
        Double timestamp)
    {
    }

    @Override
    public void resetPose(frc.lib.robotprovider.Pose2d pose)
    {
    }
}
