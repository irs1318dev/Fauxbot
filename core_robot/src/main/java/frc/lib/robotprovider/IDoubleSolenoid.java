package frc.lib.robotprovider;

/**
 * Represents a Double Solenoid for controlling pneumatics
 */
public interface IDoubleSolenoid
{
    /**
     * Sets the desired state of the double solenoid
     * @param value to apply (i.e. Forward, Reverse, Off)
     */
    void set(DoubleSolenoidValue value);
}
