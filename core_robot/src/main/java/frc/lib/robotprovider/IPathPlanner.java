package frc.lib.robotprovider;

/**
 * Represents the PathPlanner library functionality
 */
public interface IPathPlanner
{
    /**
     * Get whether the path planner has been configured yet or not
     * @return true if configured, otherwise false
     */
    boolean isConfigured();

    /**
     * Configure a swerve drive robot for use with path planner
     * Note: this MUST happen before loading/building any trajectories
     * Note: assumes 4 swerve modules, each located consistent distances from the center of the robot
     * @param robotWeight in pounds
     * @param robotMomentOfInertia in pound * square-foot
     * @param swerveModuleWheelRadius in inches
     * @param swerveModuleMaxVelocity in inches
     * @param swerveModuleWheelCoefficientOfFriction unitless
     * @param swerveDriveGearReduction gear reduction ratio for the swerve module's drive motor
     * @param swerveDriveMotorType motor type used in the swerve module for driving
     * @param swerveDriveMotorCount number of drive motors per swerve module
     * @param swerveDriveMotorCurrentLimit current limit for the swerve drive motors, in amps
     * @param horizontalModuleCenterDistance robot "y" distance from center to modules in inches
     * @param verticalModuleCenterDistance robot "x" distance from center to modules in inches
     */
    void configureRobot(
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
        double verticalModuleCenterDistance);

    /**
     * Load a trajectory that was created by the external PathPlanner tool
     * @param name of the file (without the .path)
     * @return trajectory to follow
     */
    public ITrajectory loadTrajectory(String name);

    /**
     * Load a trajectory that was created by the external PathPlanner tool
     * @param name of the file (without the .path)
     * @param reversed whether to return the path in reversed direction
     * @return trajectory to follow
     */
    public ITrajectory loadTrajectory(String name, boolean reversed);

    /**
     * Build a trajectory involving the provided waypoints with the provided velocity/acceleration constraints
     * @param maxVelocity in inches per second
     * @param maxAcceleration in inches per second
     * @param maxAngularVelocity in degrees per second
     * @param maxAngularAcceleration in degrees per second
     * @param goalPoints the set of waypoints for translation (and any intermediate holonomic rotations)
     * @return trajectory to follow
     */
    public ITrajectory buildTrajectory(
        double maxVelocity,
        double maxAcceleration,
        double maxAngularVelocity,
        double maxAngularAcceleration,
        IPathPlannerGoal... goalPoints);
}
