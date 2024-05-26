package frc.lib.robotprovider;

public enum TalonFXControlMode
{
    PercentOutput,
    Follower,
    Position,
    Velocity,
    MotionMagicPosition,
    Coast, // force coast mode
    StaticBrake, // force brake mode
    Neutral; // currently configured neutral (coast/brake)
}
