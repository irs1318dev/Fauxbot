package frc.robot.common.robotprovider;

public interface ISparkMax
{
    void setControlMode(SparkMaxControlMode mode);
    void set(double value);
    void follow(ISparkMax sparkMax);
    void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void setPIDF(double p, double i, double d, double f, int izone, int slotId);
    void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, int slotId);
    void setForwardLimitSwitch(boolean enabled, boolean normallyOpen);
    void setReverseLimitSwitch(boolean enabled, boolean normallyOpen);
    void setInvertOutput(boolean invert);
    void setNeutralMode(MotorNeutralMode neutralMode);
    void stop();
    void setPosition(double position);
    void reset();
    double getPosition();
    double getVelocity();
    boolean getForwardLimitSwitchStatus();
    boolean getReverseLimitSwitchStatus();
}
