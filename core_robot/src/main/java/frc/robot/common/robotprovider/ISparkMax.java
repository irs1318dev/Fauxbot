package frc.robot.common.robotprovider;

public interface ISparkMax
{
    void follow(ISparkMax sparkMax);
    void setControlMode(SparkMaxControlMode mode);
    void set(double value);
    void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS);
    void setSelectedSlot(int slotId);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void setPIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, double minOutput, double maxOutput, int slotId);
    void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, int slotId);
    void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, double minOutput, double maxOutput, int slotId);
    void setForwardLimitSwitch(boolean enabled, boolean normallyOpen);
    void setReverseLimitSwitch(boolean enabled, boolean normallyOpen);
    void setInvertOutput(boolean invert);
    void setInvertSensor(boolean invert);
    void setNeutralMode(MotorNeutralMode neutralMode);
    void setVelocityMeasurements(int periodMS, int windowSize);
    void stop();
    void setPosition(double position);
    void reset();
    double getPosition();
    double getVelocity();
    boolean getForwardLimitSwitchStatus();
    boolean getReverseLimitSwitchStatus();
}
