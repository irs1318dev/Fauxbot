package frc.team1318.robot.common.robotprovider;

public interface ITalonSRX extends IMotor
{
    void setControlMode(TalonSRXControlMode mode);
    void setSensorType(TalonSRXFeedbackDevice feedbackDevice);
    void setFeedbackFramePeriod(int periodMS);
    void setPIDFFramePeriod(int periodMS);
    void configureVelocityMeasurements();
    void setSelectedSlot(int slotId);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void setMotionMagicPIDF(double p, double i, double d, double f, int velocity, int acceleration, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId);
    void setForwardLimitSwitch(boolean enabled, boolean normallyOpen);
    void setReverseLimitSwitch(boolean enabled, boolean normallyOpen);
    void setInvertOutput(boolean flip);
    void setInvertSensor(boolean flip);
    void setNeutralMode(TalonSRXNeutralMode neutralMode);
    void setVoltageCompensation(boolean enabled, double maxVoltage);
    void stop();
    void setPosition(int position);
    void reset();
    int getPosition();
    double getVelocity();
    double getError();
    TalonSRXLimitSwitchStatus getLimitSwitchStatus();
}
