package frc.lib.robotprovider;

/**
 * The supported Control modes for a TalonFX motor controller
 */
public enum TalonFXControlMode
{
    PercentOutput,
    Follower,
    Position, // Position PID
    Velocity, // Velocity PID
    MotionMagicPosition, // Trapezoidal Motion Profile with PID
    MotionMagicPositionExpo, // Trapezoidal Motion Profile with PID
    MotionMagicVelocity, // Trapezoidal Motion Profile with PID
    Coast, // force coast mode
    StaticBrake, // force brake mode
    Neutral; // currently configured neutral (coast/brake)
}
