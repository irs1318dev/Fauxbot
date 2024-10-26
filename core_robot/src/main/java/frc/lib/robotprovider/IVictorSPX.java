package frc.lib.robotprovider;

/**
 * Represents a VictorSPX motor controller
 */
public interface IVictorSPX extends IMotor
{
    /**
     * Have this motor controller follow the same control instructions as the provided TalonSRX
     * @param talonSRX to follow
     */
    void follow(ITalonSRX talonSRX);

    /**
     * Have this motor controller follow the same control instructions as the provided VictorSPX
     * @param victorySPX to follow
     */
    void follow(IVictorSPX victorSPX);

    /**
     * Configure output settings for the motor
     * @param invert whether to invert the direction that the motor spins from its default
     * @param neutralMode to use (brake, coast)
     */
    void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode);

    /**
     * Configure the control mode for the motor, affecting the interpretation of the value passed to the set() function
     * @param mode to use (percent output, follower, disabled)
     */
    void setControlMode(TalonSRXControlMode mode);

    /**
     * Stop the motor (disable, providing no torque)
     */
    void stop();
}
