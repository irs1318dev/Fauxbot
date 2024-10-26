package frc.lib.robotprovider;

import frc.lib.controllers.PIDHandler;

public class FauxbotSparkMax extends FauxbotAdvancedMotorBase implements ISparkMax
{
    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private SparkMaxControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    public FauxbotSparkMax(int deviceID, SparkMaxMotorType motorType)
    {
        super(deviceID);

        this.currentMode = SparkMaxControlMode.PercentOutput;
        this.innerEncoder = new FauxbotEncoder(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), this.connection.getPort()));
    }

    public void setControlMode(SparkMaxControlMode mode)
    {
        this.currentMode = mode;
        this.resetPID();
    }

    public void follow(ISparkMax talonSRX)
    {
    }

    public void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS)
    {
    }

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setPIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setPIDF(double p, double i, double d, double f, int izone, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    @Override
    public void set(double value)
    {
        this.set(this.currentMode, value, 0.0);
    }

    @Override
    public void set(double value, double feedForward)
    {
        this.set(this.currentMode, value, feedForward);
    }

    @Override
    public void set(SparkMaxControlMode controlMode, double value)
    {
        this.set(controlMode, value, 0.0);
    }

    @Override
    public void set(SparkMaxControlMode controlMode, double value, double feedForward)
    {
        if (controlMode == SparkMaxControlMode.Velocity)
        {
            super.set(this.pidHandler.calculateVelocity(value, innerEncoder.getRate()) + feedForward);
        }
        else if (controlMode == SparkMaxControlMode.Position)
        {
            super.set(this.pidHandler.calculatePosition(value, innerEncoder.get()) + feedForward);
        }
        else
        {
            super.set(value + feedForward);
        }
    }

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
    {
    }

    public void setInvertSensor(boolean flip)
    {
    }

    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
    }

    public void setVelocityMeasurements(int periodMS, int windowSize)
    {
    }

    public void setAbsoluteEncoder()
    {
    }

    public void setRelativeEncoder()
    {
    }

    public void setRelativeEncoder(SparkMaxRelativeEncoderType encoderType, int resolution)
    {
    }

    public void setEncoderAverageDepth(int windowSize)
    {
    }

    public void setVelocityMeasurementPeriod(int periodMS)
    {
    }

    public void setSelectedSlot(int slotId)
    {
    }

    public void setCurrentLimit(int stallLimit, int freeLimit, int limitRPM)
    {
    }

    public void setAbsoluteOffset(double zeroOffset)
    {
    }

    public void burnFlash()
    {
    }

    public void setPositionConversionFactor(double ratio)
    {
    }

    public void setVelocityConversionFactor(double ratio)
    {
    }

    public void setPositionPIDWrappingSettings(boolean enable, double minInput, double maxInput)
    {
    }

    public double getOutput()
    {
        return 0.0;
    }

    public void stop()
    {
    }

    public void setPosition(double position)
    {
    }

    public void reset()
    {
    }

    public double getPosition()
    {
        return (double)this.innerEncoder.getDistance();
    }

    public double getVelocity()
    {
        return 0.0;
    }

    public double getError()
    {
        return 0.0;
    }

    public boolean getForwardLimitSwitchStatus()
    {
        return false;
    }

    public boolean getReverseLimitSwitchStatus()
    {
        return false;
    }

    private void resetPID()
    {
        if (this.currentMode == SparkMaxControlMode.Position ||
            this.currentMode == SparkMaxControlMode.Velocity)
        {
            ITimer timer = new FauxbotTimer();
            timer.start();
            this.pidHandler = new PIDHandler(this.kp, this.ki, this.kd, this.kf, 1.0, -4096.0, 4096.0, timer);
        }
        else
        {
            this.pidHandler = null;
        }
    }
}
