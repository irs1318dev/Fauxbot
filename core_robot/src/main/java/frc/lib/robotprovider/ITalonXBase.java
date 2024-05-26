package frc.lib.robotprovider;

public interface ITalonXBase extends IMotor
{
    void setSelectedSlot(int slotId);
    void setPIDF(double p, double i, double d, double f, int slotId);
    void updateLimitSwitchConfig(boolean forwardEnabled, boolean forwardNormallyOpen, boolean reverseEnabled, boolean reverseNormallyOpen);
    void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode);
    void setVoltageCompensation(boolean enabled, double maxVoltage);
    void stop();
    void setPosition(double position);
    void reset();
    double getPosition();
    double getVelocity();
    double getError();
    TalonXLimitSwitchStatus getLimitSwitchStatus();
}
