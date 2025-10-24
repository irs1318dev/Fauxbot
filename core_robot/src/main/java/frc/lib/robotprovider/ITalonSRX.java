package frc.lib.robotprovider;

/**
 * Represents a TalonSRX motor controller
 */
public interface ITalonSRX extends ITalonXBase
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
    void set(TalonSRXControlMode mode, double value);

    /**
     * Apply the desired control input for the specified control mode, using the configured PID slot, with additional feedForward input
     * @param mode to use (percent output, position, velocity, etc.)
     * @param value to apply, meaning depends on the specified control mode
     * @param feedForward additional motor torque to provide in addition to the control (e.g. for gravity compensation, etc.)
     */
    void set(TalonSRXControlMode mode, double value, double feedForward);

    /**
     * Have this motor controller follow the same control instructions as the provided TalonSRX
     * @param talonSRX to follow
     */
    void follow(ITalonSRX talonSRX);

    /**
     * Have this motor controller follow the same control instructions as the provided VictorSPX
     * @param victorSPX to follow
     */
    void follow(IVictorSPX victorSPX);

    /**
     * Configure the control mode for the motor, affecting the interpretation of the value passed to the set() function
     * @param mode to use (percent output, position, velocity, etc.)
     */
    void setControlMode(TalonSRXControlMode mode);

    /**
     * Configure PID gains that are kept in the specified slot and MotionMagic gains
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param velocity cruise velocity
     * @param acceleration max acceleration
     * @param slotId identity of the slot in which to store the data
     */
    void setMotionMagicPIDF(double p, double i, double d, double f, double velocity, double acceleration, int slotId);

    /**
     * Configure the type of sensor being used with this TalonSRX
     * @param feedbackDevice sensor connected to/associated with this TalonSRX
     */
    void setSensorType(TalonSRXFeedbackDevice feedbackDevice);

    /**
     * Configure the frequency at which the TalonSRX sends general frames, to manage responsiveness vs. CAN utilization.
     * See TalonSRX documentation for more information
     * @param periodMS amount of time between updates
     */
    void setGeneralFramePeriod(int periodMS);

    /**
     * Configure the frequency at which the TalonSRX sends feedback frames, to manage responsiveness vs. CAN utilization.
     * See TalonSRX documentation for more information
     * @param periodMS amount of time between updates
     */
    void setFeedbackFramePeriod(int periodMS);

    /**
     * Configure the frequency at which the TalonSRX sends PIDF frames, to manage responsiveness vs. CAN utilization.
     * See TalonSRX documentation for more information
     * @param periodMS amount of time between updates
     */
    void setPIDFFramePeriod(int periodMS);

    /**
     * Configure how the TalonSRX filters velocity measurements.
     * See TalonSRX documentation for more information
     * @param periodMS amount of time over which we filter the velocity measurement
     * @param windowSize number of samples to use in the rolling average for velocity
     */
    void configureVelocityMeasurements(int periodMS, int windowSize);

    /**
     * Configure the allowable closed-loop error for the PID slot
     * See TalonSRX documentation for more information
     * @param slotId identity of the slot in which to store the data
     * @param error allowable closed-loop error
     */
    void configureAllowableClosedloopError(int slotId, int error);

    /**
     * Configure PID gains that are kept in the specified slot
     * @param p proportional PID gain
     * @param i integral PID gain
     * @param d derivative PID gain (dampening)
     * @param f feed-forward PID gain
     * @param izone integral zone
     * @param closeLoopRampRate ramp rate to use - minimum amount of time between neutral and full-throttle
     * @param slotId identity of the slot in which to store the data
     */
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId);

    /**
     * Configure forward direction of sensor vs motor
     * @param flip whether the sensor is inverted compared to the motor
     */
    void setInvertSensor(boolean flip);
}
