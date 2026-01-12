package frc.lib.robotprovider;

import frc.lib.helpers.Triple;

public interface ISwervePoseEstimator
{
    /**
     * Checks if the pose estimator is configured.
     * 
     * @return true if the pose estimator is configured, false otherwise
     */
    boolean isConfigured();

    /**
     * Configures the swerve pose estimator
     * 
     * @param swerveModuleLocations    the locations of the swerve modules based on the robot's center of rotation
     * @param swerveModuleStates       the initial states of the swerve modules
     * @param initialPose              the inital pose of the robot, in inches and degrees
     * @param odometryStdDevs          the standard deviations to use for the odometry measurements
     * @param visionMeasurementStdDevs the standard deviations to use for the vision measurements
     */
    public void configure(
        frc.lib.robotprovider.Point2d[] swerveModuleLocations,
        frc.lib.robotprovider.SwerveModuleState[] swerveModuleStates,
        frc.lib.robotprovider.Pose2d initialPose,
        Triple<Double, Double, Double> odometryStdDevs,
        Triple<Double, Double, Double> visionMeasurementStdDevs);

    /**
     * Gets the current pose of the robot.
     * 
     * @return the current estimated pose of the robot, in inches and degrees
     */
    Pose2d get();

    /**
     * Update the pose estimator with the current odometry information.
     * 
     * @param moduleStates the current states of the swerve modules, in inches and degrees
     * @param gyroAngle    the current angle of the gyro, in degrees
     * @param timestamp    the timestamp of the odometry measurement, in seconds
     */
    void updateOdometry(
        SwerveModuleState[] moduleStates,
        double gyroAngle,
        Double timestamp);

    /**
     * Update the pose estimator with a vision measurement.
     * 
     * @param visionPose the pose from the vision system, in inches and degrees
     * @param timestamp  the timestamp of the vision measurement, in seconds
     * @param stdDevs    the standard deviations to use for the vision measurement
     */
    void updateVision(
        frc.lib.robotprovider.Pose2d visionPose,
        double timestamp,
        Triple<Double, Double, Double> stdDevs);

    /**
     * Reset the pose estimator with a new pose.
     * 
     * @param pose the new pose to set, in inches and degrees
     */
    void resetPose(Pose2d pose);
}