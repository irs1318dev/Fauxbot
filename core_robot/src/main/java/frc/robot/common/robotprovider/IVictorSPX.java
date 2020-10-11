package frc.robot.common.robotprovider;

public interface IVictorSPX extends IMotor
{
    void follow(ITalonSRX talonSRX);
    void follow(ITalonFX talonFX);
    void follow(IVictorSPX victorSPX);
    void setInvertOutput(boolean flip);
    void setNeutralMode(MotorNeutralMode neutralMode);
    void setControlMode(TalonSRXControlMode mode);
    void stop();
}
