package org.usfirst.frc.team1318.robot.common.wpilib;

public interface ITalonSRX extends IMotor
{
    void changeControlMode(TalonSRXControlMode mode);
    void setSensorType(TalonSRXFeedbackDevice feedbackDevice);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId);
    void invertOutput(boolean flip);
    void invertSensor(boolean flip);
    void setNeutralMode(TalonSRXNeutralMode neutralMode);
    void reset();
    int getPosition();
    double getVelocity();
    double getError();
}
