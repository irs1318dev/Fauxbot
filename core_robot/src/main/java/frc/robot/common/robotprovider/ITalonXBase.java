package frc.robot.common.robotprovider;

public interface ITalonXBase extends IMotor
{
    void follow(ITalonSRX talonSRX);
    void follow(ITalonFX talonFX);
    void follow(IVictorSPX victorSPX);
    void setControlMode(TalonXControlMode mode);
    void setSensorType(TalonXFeedbackDevice feedbackDevice);
    void setFeedbackFramePeriod(int periodMS);
    void setPIDFFramePeriod(int periodMS);
    void configureVelocityMeasurements(int periodMS, int windowSize);
    void configureAllowableClosedloopError(int slotId, int error);
    void setSelectedSlot(int slotId);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void setMotionMagicPIDF(double p, double i, double d, double f, int velocity, int acceleration, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId);
    void setForwardLimitSwitch(boolean enabled, boolean normallyOpen);
    void setReverseLimitSwitch(boolean enabled, boolean normallyOpen);
    void setInvertOutput(boolean flip);
    void setInvertSensor(boolean flip);
    void setNeutralMode(MotorNeutralMode neutralMode);
    void setVoltageCompensation(boolean enabled, double maxVoltage);
    void stop();
    void setPosition(double position);
    void reset();
    double getPosition();
    double getVelocity();
    double getError();
    TalonXLimitSwitchStatus getLimitSwitchStatus();
}
