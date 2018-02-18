package org.usfirst.frc.team1318.robot.common.wpilib;

public interface IVictorSPX extends IMotor
{
    void setInvertOutput(boolean flip);
    void setInvertSensor(boolean flip);
    void setControlMode(TalonSRXControlMode mode);
}
