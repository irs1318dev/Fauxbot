package frc.lib.robotprovider;

/**
 * Desired motion for a DoubleSolenoid
 */
public enum DoubleSolenoidValue
{
    /**
     * Move forward (push outwards)
     */
    Forward,

    /**
     * Move reverse (pull inwards)
     */
    Reverse,

    /**
     * Don't move (no force)
     */
    Off;
}
