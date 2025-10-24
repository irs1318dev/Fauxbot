package frc.lib.robotprovider;

/**
 * Represents a TalonSRX/TalonFX motor controller
 */
public interface ITalonXBase extends IMotor
{
    /**
     * Set the selected PID data slot
     * PID configuration (gains) are stored in different "slots" to easily switch between gains (e.g. gain scheduling)
     * @param slotId identity of the slot
     */
    void setSelectedSlot(int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDF(double p, double i, double d, double f, int slotId);

    /**
     * Configure the limit switch for this motor controller
     * @param forwardEnabled whether to use the forward limit switch
     * @param forwardNormallyOpen whether the forward limit switch is normally open or normally closed
     * @param reverseEnabled whether to use the reverse limit switch
     * @param reverseNormallyOpen whether the reverse limit switch is normally open or normally closed
     */
    void updateLimitSwitchConfig(boolean forwardEnabled, boolean forwardNormallyOpen, boolean reverseEnabled, boolean reverseNormallyOpen);

    /**
     * Configure output settings for the motor
     * @param invert whether to invert the direction that the motor spins from its default
     * @param neutralMode to use (brake, coast)
     */
    void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode);

    /**
     * Apply voltage compensation for the motor
     * @param enabled whether to use voltage compensation
     * @param maxVoltage the max voltage to attempt to provide at 100% output
     */
    void setVoltageCompensation(boolean enabled, double maxVoltage);

    /**
     * Stop the motor (disable, providing no torque)
     */
    void stop();

    /**
     * Configure the current position of the sensor associated with the motor controller
     * @param position to use
     */
    void setPosition(double position);

    /**
     * Reset the current position of the sensor associated with the motor controller to 0
     */
    void reset();

    /**
     * Retrieve the current position of the sensor associated with the motor controller
     * @return current position
     */
    double getPosition();

    /**
     * Retrieve the current velocity of the sensor associated with the motor controller
     * @return current velocity
     */
    double getVelocity();

    /**
     * Retrieve the current PID error between the setpoint and the sensor's measured value
     * @return error
     */
    double getError();

    /**
     * Retrieve the current control signal (percent output/duty cycle) from the motor controller
     * @return current output being used (from -1.0 to 1.0)
     */
    double getOutput();

    /**
     * Retrieve the current status of the limit switches associated with the motor controller
     * @return whether the forward/reverse limit switches are currently tripped
     */
    TalonXLimitSwitchStatus getLimitSwitchStatus();
}
