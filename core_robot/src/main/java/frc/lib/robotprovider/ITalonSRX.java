package frc.lib.robotprovider;

public interface ITalonSRX extends ITalonXBase
{
    void set(double value);
    void set(double value, double feedForward);
    void set(TalonSRXControlMode mode, double value);
    void set(TalonSRXControlMode mode, double value, double feedForward);
    void follow(ITalonSRX talonSRX);
    void follow(IVictorSPX victorSPX);
    void setControlMode(TalonSRXControlMode mode);
    void setMotionMagicPIDF(double p, double i, double d, double f, double velocity, double acceleration, int slotId);
    void setSensorType(TalonSRXFeedbackDevice feedbackDevice);
    void setGeneralFramePeriod(int periodMS);
    void setFeedbackFramePeriod(int periodMS);
    void setPIDFFramePeriod(int periodMS);
    void configureVelocityMeasurements(int periodMS, int windowSize);
    void configureAllowableClosedloopError(int slotId, int error);
    void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId);
    void setInvertSensor(boolean flip);
    double getOutput();
}
