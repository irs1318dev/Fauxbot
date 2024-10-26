package frc.lib.robotprovider;

/**
 * Represents a simple motor which can be set to some power level
 */
public interface IMotor
{
    /**
     * Set the signal for the motor
     * @param power level between -1.0 (full reverse) and 1.0 (full forward)
     */
    void set(double power);
}
