package frc.lib.robotprovider;

/**
 * Represents a SparkMax or SparkFlex motor controller
 */
public interface ISparkBase extends IMotor
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
    void set(SparkControlMode controlMode, double value);

    /**
     * Apply the desired control input for the specified control mode, using the configured PID slot, with additional feedForward input
     * @param mode to use (percent output, position, velocity, etc.)
     * @param value to apply, meaning depends on the specified control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(SparkControlMode controlMode, double value, double feedForward);

    /**
     * Stop the motor (disable, providing no torque)
     */
    void stop();

    /**
     * Set the selected PID data slot
     * PID configuration (gains) are stored in different "slots" to easily switch between gains (e.g. gain scheduling)
     * @param slotId identity of the slot
     */
    void setSelectedSlot(int slotId);

    /**
     * Configure the control mode for the motor, affecting the interpretation of the value passed to the set() function
     * @param mode to use (percent output, position, velocity, etc.)
     */
    void setControlMode(SparkControlMode mode);

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

    /**
     * Configure the current position of the sensor associated with the motor controller
     * Note: for absolute encoders, we will need to apply configuration after a setPosition call
     * @param position to use
     */
    void setPosition(double position);

    /**
     * Reset the current position of the sensor associated with the motor controller to 0
     */
    void reset();

    /**
     * Apply the configuration updates, optionally persisting them in flash memory so that it persists across robot brown-outs, etc.
     * @param persistant whether to persist the configuration in flash memory
     */
    void applyConfiguration(boolean persistant);

    /**
     * Have this motor controller follow the same control instructions as the provided SparkMax
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param sparkBase (ISparkMax or ISparkFlex) to follow
     */
    void configureFollow(ISparkBase sparkBase);

    /**
     * Configure the motor controller to use a duty-cycle absolute encoder for position measurements
     * Note: to take effect, applyConfiguration must be called sometime after this function
     */
    void configureAbsoluteEncoder();

    /**
     * Configure the motor controller to use the built-in relative encoder (hall-effect with 42 ticks/rotation) for position measurements
     * Note: this is the default configuration for a NEO
     * Note: to take effect, applyConfiguration must be called sometime after this function
     */
    void configureRelativeEncoder();

    /**
     * Configure the motor controller to use a relative encoder of the specified resolution for position measurements
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param resolution number of ticks in a rotation/revolution for the conencted encoder
     */
    void configureRelativeEncoder(int resolution);

    /**
     * Configure whether the Spark motor controller always sends CAN frames, to manage responsiveness vs. CAN utilization.
     * See SparkMax documentation for more information
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param frameType the type of frame for which we want to adjust frequency
     * @param alwaysOn whether the frame should always be sent
     */
    void configureSignals(SparkSignalType signalType, boolean alwaysOn);
    
    /**
     * Configure the frequency at which the Spark motor controller sends CAN frames, to manage responsiveness vs. CAN utilization.
     * See SparkMax documentation for more information
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param frameType the type of frame for which we want to adjust frequency
     * @param periodMs amount of time between updates in milliseconds
     */
    void configureSignals(SparkSignalType signalType, int periodMs);

    /**
     * Configure the window size for velocity sampling
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param windowSize number of samples to use for velocity averaging (2^(0 to 7) for absolute, 1 to 64 for quadrature, 2^(0 to 3) for hall-effect)
     */
    void configureEncoderAverageDepth(int depth);

    /**
     * Configure the measurement period used to calculate velocity for a relative encoder
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param periodMS milliseconds over which to calculate (1 to 100 for quadrature, 8 to 64 for hall-effect)
     */
    void configureVelocityMeasurementPeriod(int periodMS);

    /**
     * Configure PID gains that are kept in the specified slot
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param slotId identity of the slot in which to store the data
     */
    void configurePIDF(double p, double i, double d, double f, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param minOutput the lower bound of the output range to use from PID
     * @param maxOutput the upper bound of the output range to use from PID
     * @param slotId identity of the slot in which to store the data
     */
    void configurePIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param slotId identity of the slot in which to store the data
     */
    void configurePIDF(double p, double i, double d, double f, double izone, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param minOutput the lower bound of the output range to use from PID
     * @param maxOutput the upper bound of the output range to use from PID
     * @param slotId identity of the slot in which to store the data
     */
    void configurePIDF(double p, double i, double d, double f, double izone, double minOutput, double maxOutput, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot, and MAXMotion gains
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param velocity cruise velocity
     * @param acceleration max acceleration
     * @param slotId identity of the slot in which to store the data
     */
    void configurePIDFMAXMotion(double p, double i, double d, double f, double izone, double velocity, double acceleration, int slotId);

    /**
     * Configure PID gains that are kept in the specified slot, and MAXMotion gains
     * Note: to take effect, applyConfiguration must be called sometime after this function
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
    void configurePIDFMAXMotion(double p, double i, double d, double f, double izone, double velocity, double acceleration, double minOutput, double maxOutput, int slotId);

    /**
     * Configure the forward limit switch for this motor controller
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param enabled whether to use the forward limit switch
     * @param normallyOpen whether the forward limit switch is normally open or normally closed
     */
    void configureForwardLimitSwitch(boolean enabled, boolean normallyOpen);

    /**
     * Configure the reverse limit switch for this motor controller
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param enabled whether to use the reverse limit switch
     * @param normallyOpen whether the reverse limit switch is normally open or normally closed
     */
    void configureReverseLimitSwitch(boolean enabled, boolean normallyOpen);

    /**
     * Configure output settings for the motor
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param invert whether to invert the direction that the motor spins from its default
     * @param neutralMode to use (brake, coast)
     */
    void configureMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode);

    /**
     * Configure forward direction of sensor vs motor
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param invert whether the sensor is inverted compared to the motor
     */
    void configureInvertSensor(boolean invert);

    /**
     * Apply smart current limiting for the motor
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * 
     * From REV documentation (2025):
     * Sets the current limit in Amps.
     * 
     * The motor controller will reduce the controller voltage output to avoid surpassing this limit. This limit is enabled by default and used for brushless only. This limit is highly recommended when using the NEO brushless motor.
     * 
     * The NEO Brushless Motor has a low internal resistance, which can mean large current spikes that could be enough to cause damage to the motor and controller. This current limit provides a smarter strategy to deal with high current draws and keep the motor and controller operating in a safe region.
     * 
     * The controller can also limit the current based on the RPM of the motor in a linear fashion to help with controllability in closed loop control. For a response that is linear the entire RPM range leave limit RPM at 0.
     * @param stallLimit current limit in Amps at 0 RPM
     * @param freeLimit current limit at free speed (5700RPM for NEO)
     * @param limitRPM threshold at which lower speeds will use stall limit, higher speeds will scale linearly to free limit
     */
    void configureSmartCurrentLimit(int stallLimit, int freeLimit, int limitRPM);

    /**
     * Apply secondary current limit
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * 
     * From REV documentation (2025):
     * Sets the secondary current limit in Amps.
     * 
     * The motor controller will disable the output of the controller briefly if the current limit is exceeded to reduce the current. This limit is a simplified 'on/off' controller. This limit is enabled by default but is set higher than the default Smart Current Limit.
     * 
     * The time the controller is off after the current limit is reached is determined by the parameter limitCycles, which is the number of PWM cycles (20kHz). The recommended value is the default of 0 which is the minimum time and is part of a PWM cycle from when the over current is detected. This allows the controller to regulate the current close to the limit value.
     * 
     * The total time is set by the equation t = (50us - t0) + 50us * limitCycles t = total off time after over current t0 = time from the start of the PWM cycle until over current is detected 
     * @param limit The current limit in Amps
     * @param chopCycles the number of additional PWM cycles to turn the driver off after overcurrent is detected.
    */
    void configureSecondaryCurrentLimit(double limit, int chopCycles);

    /**
     * Configure the offset position absolute encoder associated with the motor controller
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param zeroOffset offset between the measured zero from the absolute encoder and the desired physical zero
     */
    void configureAbsoluteOffset(double zeroOffset);

    /**
     * Configure the multiplier between the native sensor units and the position/distance
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param ratio between rotations and position
     */
    void configurePositionConversionFactor(double ratio);

    /**
     * Configure the multiplier between the rotations per timeunit (absolute encoders: second, relative encoders: minute) and the velocity
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param ratio between rotations per timeunit and velocity
     */
    void configureVelocityConversionFactor(double ratio);

    /**
     * Configure Position PID wrapping for the motor controller
     * See SparkMax documentation for more information about PID wrapping
     * Note: to take effect, applyConfiguration must be called sometime after this function
     * @param enable whether to use Position PID wrapping feature
     * @param minInput the value of the minimum position
     * @param maxInput the value of the maximum position
     */
    void configurePositionPIDWrappingSettings(boolean enable, double minInput, double maxInput);
}
