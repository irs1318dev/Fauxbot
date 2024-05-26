package frc.lib.robotprovider;

public interface ITalonFX extends ITalonXBase
{
    void set(double value);
    void set(double value, double feedForward);
    void set(TalonFXControlMode mode, double value);
    void set(TalonFXControlMode mode, int slotId, double value);
    void set(TalonFXControlMode mode, double value, double feedForward);
    void set(TalonFXControlMode mode, int slotId, double value, double feedForward);

    void follow(ITalonFX talonFX);
    void follow(ITalonFX talonFX, boolean invertDirection);

    void setControlMode(TalonFXControlMode mode);

    void clearRemoteSensor();

    void setRemoteSensor(int sensorId, double ratio);

    void setFeedbackUpdateRate(double frequencyHz);
    void setErrorUpdateRate(double frequencyHz);
    void setForwardLimitSwitchUpdateRate(double frequencyHz);
    void setReverseLimitSwitchUpdateRate(double frequencyHz);
    void optimizeCanbus();

    void setSelectedSlot(int slotId);

    void setPIDF(double p, double i, double d, double f, int slotId);

    void setMotionMagicPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double maxAcceleration, double maxJerk, int slotId);

    void updateLimitSwitchConfig(
        boolean forwardEnabled,
        boolean forwardNormallyOpen,
        boolean forwardReset,
        double forwardResetPosition,
        boolean reverseEnabled,
        boolean reverseNormallyOpen,
        boolean reverseReset,
        double reverseResetPosition);

    void setVoltageCompensation(boolean enabled, double maxVoltage);

    void stop();
    void setPosition(double position);
    void reset();
    double getPosition();
    double getVelocity();
    double getError();
    boolean getForwardLimitSwitchClosed();
    boolean getReverseLimitSwitchClosed();
    TalonXLimitSwitchStatus getLimitSwitchStatus();

    void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime, boolean statorLimiting, double statorCurrentLimit);
}
