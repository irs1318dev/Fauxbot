package frc.lib.robotprovider;

public interface ISparkMax extends IMotor
{
    void follow(ISparkMax sparkMax);
    void setControlMode(SparkMaxControlMode mode);
    void setAbsoluteEncoder();
    void setRelativeEncoder();
    void setRelativeEncoder(SparkMaxRelativeEncoderType encoderType, int resolution);
    void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS);
    void setEncoderAverageDepth(int windowSize);
    void setVelocityMeasurementPeriod(int periodMS);
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
    void setCurrentLimit(int stallLimit, int freeLimit, int limitRPM);
    void stop();
    void setAbsoluteOffset(double zeroOffset);
    void setPosition(double position);
    void reset();
    void burnFlash();
    void setPositionConversionFactor(double ratio);
    void setVelocityConversionFactor(double ratio);
    void setPositionPIDWrappingSettings(boolean enable, double minInput, double maxInput);
    double getPosition();
    double getVelocity();
    double getOutput();
    boolean getForwardLimitSwitchStatus();
    boolean getReverseLimitSwitchStatus();
}
