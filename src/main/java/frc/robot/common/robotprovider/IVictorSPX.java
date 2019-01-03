package frc.robot.common.robotprovider;

public interface IVictorSPX extends IMotor
{
    void setInvertOutput(boolean flip);
    void setInvertSensor(boolean flip);
    void setControlMode(TalonSRXControlMode mode);
}
