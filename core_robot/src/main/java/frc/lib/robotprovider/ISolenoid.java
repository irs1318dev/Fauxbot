package frc.lib.robotprovider;

/**
 * Represents a Solenoid for controlling pneumatics
 */
public interface ISolenoid
{
    /**
     * Sets the desired state of the solenoid
     * @param on whether to turn the device on (true) or not (false)
     */
    void set(boolean on);
}
