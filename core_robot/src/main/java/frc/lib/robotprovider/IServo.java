package frc.lib.robotprovider;

/**
 * Represents a PWM-controlled Servo device
 */
public interface IServo
{
    /**
     * Sets the desired position of the servo
     * @param value position from 0.0 (full left) to 1.0 (full right)
     */
    void set(double value);
}
