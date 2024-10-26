package frc.lib.robotprovider;

/**
 * Represents a SparkMax motor controller
 */
public interface ISparkMax extends IMotor
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
    void set(SparkMaxControlMode controlMode, double value);

    /**
     * Apply the desired control input for the specified control mode, using the configured PID slot, with additional feedForward input
     * @param mode to use (percent output, position, velocity, etc.)
     * @param value to apply, meaning depends on the specified control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(SparkMaxControlMode controlMode, double value, double feedForward);

    /**
     * Have this motor controller follow the same control instructions as the provided SparkMax
     * @param sparkMax to follow
     */
    void follow(ISparkMax sparkMax);

    /**
     * Configure the control mode for the motor, affecting the interpretation of the value passed to the set() function
     * @param mode to use (percent output, position, velocity, etc.)
     */
    void setControlMode(SparkMaxControlMode mode);

    /**
     * Configure the motor controller to use a duty-cycle absolute encoder for position measurements
     */
    void setAbsoluteEncoder();

    /**
     * Configure the motor controller to use a relative hall-effect encoder with 42 ticks/rotation for position measurements
     * Note: this is the default configuration for a NEO
     */
    void setRelativeEncoder();

    /**
     * Configure the motor controller to use a relative encoder of hte specified type and resolution for position measurements
     * @param encoderType of the connected encoder
     * @param resolution number of ticks in a rotation/revolution for the conencted encoder
     */
    void setRelativeEncoder(SparkMaxRelativeEncoderType encoderType, int resolution);

    /**
     * Configure the frequency at which the SparkMax sends CAN frames, to manage responsiveness vs. CAN utilization.
     * See SparkMax documentation for more information
     * @param frameType the type of frame for which we want to adjust frequency
     * @param periodMS amount of time between updates
     */
    void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS);

    /**
     * Configure the window size for velocity sampling
     * @param windowSize number of samples to use for velocity averaging (2^(0 to 7) for absolute, 1 to 64 for quadrature, 2^(0 to 3) for hall-effect)
     */
    void setEncoderAverageDepth(int windowSize);

    /**
     * Configure the measurement period used to calculate velocity for a relative encoder
     * @param periodMS milliseconds over which to calculate (1 to 100 for quadrature, 8 to 64 for hall-effect)
     */
    void setVelocityMeasurementPeriod(int periodMS);

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
     * Configure PID gains that are kept in the specified slot
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param minOutput the lower bound of the output range to use from PID
     * @param maxOutput the upper bound of the output range to use from PID
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDF(double p, double i, double d, double f, int izone, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param minOutput the lower bound of the output range to use from PID
     * @param maxOutput the upper bound of the output range to use from PID
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDF(double p, double i, double d, double f, int izone, double minOutput, double maxOutput, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot, and SmartMotion gains
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param velocity cruise velocity
     * @param acceleration max acceleration
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot, and SmartMotion gains
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param velocity cruise velocity
     * @param acceleration max acceleration
     * @param minOutput the lower bound of the output range to use from PID
     * @param maxOutput the upper bound of the output range to use from PID
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, double minOutput, double maxOutput, int slotId);

    /**
     * Configure the forward limit switch for this motor controller
     * @param enabled whether to use the forward limit switch
     * @param normallyOpen whether the forward limit switch is normally open or normally closed
     */
    void setForwardLimitSwitch(boolean enabled, boolean normallyOpen);

    /**
     * Configure the reverse limit switch for this motor controller
     * @param enabled whether to use the reverse limit switch
     * @param normallyOpen whether the reverse limit switch is normally open or normally closed
     */
    void setReverseLimitSwitch(boolean enabled, boolean normallyOpen);

    /**
     * Configure output settings for the motor
     * @param invert whether to invert the direction that the motor spins from its default
     * @param neutralMode to use (brake, coast)
     */
    void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode);

    /**
     * Configure forward direction of sensor vs motor
     * @param invert whether the sensor is inverted compared to the motor
     */
    void setInvertSensor(boolean invert);

    /**
     * Apply smart current limiting for the motor
     * @param stallLimit current limit in Amps at 0 RPM
     * @param freeLimit current limit at free speed
     * @param limitRPM threshold at which lower speeds will use stall limit, higher speeds will scale linearly to free limit
     */
    void setCurrentLimit(int stallLimit, int freeLimit, int limitRPM);

    /**
     * Stop the motor (disable, providing no torque)
     */
    void stop();

    /**
     * Configure the offset position absolute encoder associated with the motor controller
     * Note: setPositionConversionFactor and setInvertSensor should be called before this function
     * @param zeroOffset offset between the measured zero from the absolute encoder and the desired physical zero
     */
    void setAbsoluteOffset(double zeroOffset);

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
     * Save the applicable configurations into flash memory so that it persists across robot brown-outs, etc.
     */
    void burnFlash();

    /**
     * Configure the multiplier between the native sensor units and the position/distance
     * @param ratio between rotations and position
     */
    void setPositionConversionFactor(double ratio);

    /**
     * Configure the multiplier between the rotations per timeunit (absolute encoders: second, relative encoders: minute) and the velocity
     * @param ratio between rotations per timeunit and velocity
     */
    void setVelocityConversionFactor(double ratio);

    /**
     * Configure Position PID wrapping for the motor controller
     * See SparkMax documentation for more information about PID wrapping
     * @param enable whether to use Position PID wrapping feature
     * @param minInput the value of the minimum position
     * @param maxInput the value of the maximum position
     */
    void setPositionPIDWrappingSettings(boolean enable, double minInput, double maxInput);

    /**
     * Retrieve the current position of the sensor associated with the motor controller
     * @return current position
     */
    double getPosition();

    /**
     * Retrieve the current velocity of the sensor associated with the motor controller
     * Note: SparkMAX Absolute encoder provides velocity in Rotations per Second,
     * but SparkMAX Relative/Alternative encoder provides velocity in Rotations per Minute.
     * @return current velocity
     */
    double getVelocity();

    /**
     * Retrieve the current control signal (duty cycle) from the motor controller
     * @return current output being used (from -1.0 to 1.0)
     */
    double getOutput();

    /**
     * Retrieve the current status of the forward limit switch
     * @return true if the forward limit switch is pressed, otherwise false
     */
    boolean getForwardLimitSwitchStatus();

    /**
     * Retrieve the current status of the reverse limit switch
     * @return true if the reverse limit switch is pressed, otherwise false
     */
    boolean getReverseLimitSwitchStatus();
}
