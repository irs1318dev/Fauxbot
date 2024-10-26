package frc.lib.robotprovider;

/**
 * The supported Control modes for a SparkMax motor controller
 */
public enum SparkMaxControlMode
{
    PercentOutput,
    Position, // Position PID
    Voltage,
    Velocity, // Velocity PID
    SmartMotionPosition; // (Discouraged) Trapezoidal Motion Profile with PID
}
