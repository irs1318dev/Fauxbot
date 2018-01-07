package org.usfirst.frc.team1318.robot.common.wpilib;

public interface ITalonSRX extends IMotor
{
    void changeControlMode(TalonSRXControlMode mode);
    void setPIDF(double p, double i, double d, double f);
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile);
    void invertOutput(boolean flip);
    void invertSensor(boolean flip);
    void setNeutralMode(boolean brake);
    void reset();
    int getPosition();
    double getVelocity();
    double getError();
}
