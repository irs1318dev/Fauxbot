package frc.lib.robotprovider;

/**
 * Represents a TalonFX motor controller
 */
public interface ITalonFX extends ITalonXBase
{
    /**
     * Apply the desired control input for the configured control mode, using the configured PID slot
     * @param value to apply, meaning depends on the currently-configured control mode
     */
    void set(double value);

    /**
     * Apply the desired control input for the configured control mode, using the configured PID slot, with additional feedForward input
     * @param value to apply, meaning depends on the currently-configured control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(double value, double feedForward);

    /**
     * Apply the desired control input for the specified control mode, using the configured PID slot
     * @param mode to use (percent output, position, velocity, etc.)
     * @param value to apply, meaning depends on the specified control mode
     */
    void set(TalonFXControlMode mode, double value);

    /**
     * Apply the desired control input for the specified control mode using the specified PID slot
     * @param mode to use (percent output, position, velocity, etc.)
     * @param slotId the slot containing PID gains to use as a part of the control
     * @param value to apply, meaning depends on the specified control mode
     */
    void set(TalonFXControlMode mode, int slotId, double value);

    /**
     * Apply the desired control input for the specified control mode, using the configured PID slot, with additional feedForward input
     * @param mode to use (percent output, position, velocity, etc.)
     * @param value to apply, meaning depends on the specified control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(TalonFXControlMode mode, double value, double feedForward);

    /**
     * Apply the desired control input for the specified control mode using the specified PID slot, with additional feedForward input
     * @param mode to use (percent output, position, velocity, etc.)
     * @param slotId the slot containing PID gains to use as a part of the control
     * @param value to apply, meaning depends on the specified control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(TalonFXControlMode mode, int slotId, double value, double feedForward);

    /**
     * Have this motor controller follow the same control instructions as the provided TalonFX
     * @param talonFX to follow
     */
    void follow(ITalonFX talonFX);

    /**
     * Have this motor controller follow the same control instructions as the provided TalonFX,
     * @param talonFX to follow
     * @param invertDirection whether to invert direction (spin the other way), such as if connected 
     */
    void follow(ITalonFX talonFX, boolean invertDirection);

    /**
     * Configure the control mode for the motor, affecting the interpretation of the value passed to the set() function
     * @param mode to use (percent output, position, velocity, etc.)
     */
    void setControlMode(TalonFXControlMode mode);

    /**
     * Configure the default feedback configuratiuon
     */
    void clearRemoteSensor();

    /**
     * Configure the motor controller to use a remote CANCoder as its sensor
     * @param sensorId CAN Id of the remote CANCoder
     * @param ratio between the remote sensor's units and the internal units
     */
    void setRemoteSensor(int sensorId, double ratio);

    /**
     * Configure the frequency at which the TalonFX sends updates about the sensor Position/Velocity, to manage responsiveness vs. CAN utilization.
     * See TalonFX documentation for more information
     * @param frequencyHz frequency of updates
     */
    void setFeedbackUpdateRate(double frequencyHz);

    /**
     * Configure the frequency at which the TalonFX sends updates about the PID error, to manage responsiveness vs. CAN utilization.
     * See TalonFX documentation for more information
     * @param frequencyHz frequency of updates
     */
    void setErrorUpdateRate(double frequencyHz);

    /**
     * Configure the frequency at which the TalonFX sends updates about the duty-cycle output, to manage responsiveness vs. CAN utilization.
     * See TalonFX documentation for more information
     * @param frequencyHz frequency of updates
     */
    void setOutputUpdateRate(double frequencyHz);

    /**
     * Configure the frequency at which the TalonFX sends updates about the forward limit switch, to manage responsiveness vs. CAN utilization.
     * See TalonFX documentation for more information
     * @param frequencyHz frequency of updates
     */
    void setForwardLimitSwitchUpdateRate(double frequencyHz);

    /**
     * Configure the frequency at which the TalonFX sends updates about the reverse limit switch, to manage responsiveness vs. CAN utilization.
     * See TalonFX documentation for more information
     * @param frequencyHz frequency of updates
     */
    void setReverseLimitSwitchUpdateRate(double frequencyHz);

    /**
     * Optimize CAN bus utilization by disabling (or decreasing the frequency of) signals that haven't been explicitly configured.
     * See TalonFX documentation for more information
     */
    void optimizeCanbus();

    /**
     * Configure PID gains that are kept in the specified slot and MotionMagic gains
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param v velocity feed-forward PID gain
     * @param s static feed-forward PID gain
     * @param cruiseVelocity cruise velocity
     * @param maxAcceleration max acceleration
     * @param maxJerk max jerk (change in acceleration)
     * @param slotId identity of the slot in which to store the data
     */
    void setMotionMagicPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double maxAcceleration, double maxJerk, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot and MotionMagicExpo gains
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param v velocity feed-forward PID gain
     * @param s static feed-forward PID gain
     * @param cruiseVelocity cruise velocity
     * @param velocityVoltage the amount of voltage necessary to hold a velocity, Volts per rotations-per-second
     * @param accelerationVoltage the amount of voltage necessary to achieve an acceleration, Volts per rotations-per-second-squared
     * @param slotId identity of the slot in which to store the data
     */
    void setMotionMagicExpoPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double velocityVoltage, double accelerationVoltage, int slotId);

    /**
     * Configure the limit switch for this motor controller
     * @param forwardEnabled whether to use the forward limit switch
     * @param forwardNormallyOpen whether the forward limit switch is normally open or normally closed
     * @param forwardReset whether to automatically reset position when the forward limit switch is hit
     * @param forwardResetPosition position to reset to when the forward limit reset option is enabled
     * @param reverseEnabled whether to use the reverse limit switch
     * @param reverseNormallyOpen whether the reverse limit switch is normally open or normally closed
     * @param reverseReset whether to automatically reset position when the reverse limit switch is hit
     * @param reverseResetPosition position to reset to when the reverse limit reset option is enabled
     */
    void updateLimitSwitchConfig(
        boolean forwardEnabled,
        boolean forwardNormallyOpen,
        boolean forwardReset,
        double forwardResetPosition,
        boolean reverseEnabled,
        boolean reverseNormallyOpen,
        boolean reverseReset,
        double reverseResetPosition);

    /**
     * Apply voltage compensation for the motor
     * @param enabled whether to use voltage compensation
     * @param maxVoltage the max voltage to attempt to provide at 100% output
     */
    void setVoltageCompensation(boolean enabled, double maxVoltage);

    /**
     * Retrieve whether the forward limit switch is closed
     * @return true if the forward limit switch is closed, otherwise false
     */
    boolean getForwardLimitSwitchClosed();

    /**
     * Retrieve whether the reverse limit switch is closed
     * @return true if the reverse limit switch is closed, otherwise false
     */
    boolean getReverseLimitSwitchClosed();

    /**
     * Configure current limiting
     * @param enabled whether to enable supply current limiting
     * @param currentLimit the maximum supply current to allow
     * @param triggerThresholdCurrent the threshold after which supply current limiting will apply
     * @param triggerThresholdTime the amount of time the supply current must exceed the threshold before limiting will happen
     */
    void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);

    /**
     * Configure current limiting
     * @param enabled whether to enable supply current limiting
     * @param currentLimit the maximum supply current to allow
     * @param triggerThresholdCurrent the threshold after which supply current limiting will apply
     * @param triggerThresholdTime the amount of time the supply current must exceed the threshold before limiting will happen
     * @param statorLimiting whether to enable stator current limiting
     * @param statorCurrentLimit the amount of current allowed in the motor
     */
    void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime, boolean statorLimiting, double statorCurrentLimit);
}
