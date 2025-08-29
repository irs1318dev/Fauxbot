package frc.lib.robotprovider;

/**
 * The supported Control modes for a SparkMax motor controller
 */
public enum SparkControlMode
{
    PercentOutput,
    Position, // Position PID
    Voltage,
    Velocity, // Velocity PID
    MAXMotionPosition, // Trapezoidal Motion Profile with PID
    MAXMotionVelocity;
}
