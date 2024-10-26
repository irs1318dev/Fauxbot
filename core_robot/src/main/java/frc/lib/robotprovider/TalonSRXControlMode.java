package frc.lib.robotprovider;

/**
 * The supported Control modes for a TalonSRX motor controller
 */
public enum TalonSRXControlMode
{
    Required, // Do not use
    PercentOutput,
    Disabled,
    Follower,
    Position, // Position PID
    MotionMagicPosition, // Trapezoidal Motion Profile with PID
    Velocity, // Velocity PID
    Current;
}
