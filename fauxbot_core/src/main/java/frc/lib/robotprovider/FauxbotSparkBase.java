package frc.lib.robotprovider;

import frc.lib.controllers.PIDHandler;

abstract class FauxbotSparkBase extends FauxbotAdvancedMotorBase implements ISparkBase
{
    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private SparkControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    FauxbotSparkBase(int deviceID, SparkMotorType motorType)
    {
        super(deviceID);

        this.currentMode = SparkControlMode.PercentOutput;
        this.innerEncoder = new FauxbotEncoder(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.getClass(), this.connection.getPort()));
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
    public void set(SparkControlMode controlMode, double value)
    {
        this.set(controlMode, value, 0.0);
    }

    @Override
    public void set(SparkControlMode controlMode, double value, double feedForward)
    {
        if (controlMode == SparkControlMode.Velocity)
        {
            super.set(this.pidHandler.calculateVelocity(value, innerEncoder.getRate()) + feedForward);
        }
        else if (controlMode == SparkControlMode.Position)
        {
            super.set(this.pidHandler.calculatePosition(value, innerEncoder.get()) + feedForward);
        }
        else
        {
            super.set(value + feedForward);
        }
    }

    public void setControlMode(SparkControlMode mode)
    {
        this.currentMode = mode;
        this.resetPID();
    }

    public void setSelectedSlot(int slotId)
    {
    }

    public void stop()
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

    public double getOutput()
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

    public void setPosition(double position)
    {
    }

    public void reset()
    {
    }

    public void applyConfiguration(boolean persistant)
    {
    }

    public void configureFollow(ISparkBase sparkBase)
    {
    }

    public void configureSignals(SparkSignalType signalType, boolean alwaysOn)
    {
    }

    public void configureSignals(SparkSignalType signalType, int periodMs)
    {
    }

    public void configurePIDF(double p, double i, double d, double f, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configurePIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configurePIDF(double p, double i, double d, double f, double izone, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configurePIDF(double p, double i, double d, double f, double izone, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configurePIDFMAXMotion(double p, double i, double d, double f, double izone, double velocity, double acceleration, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configurePIDFMAXMotion(double p, double i, double d, double f, double izone, double velocity, double acceleration, double minOutput, double maxOutput, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void configureForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void configureReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void configureMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
    {
    }

    public void configureInvertSensor(boolean flip)
    {
    }

    public void configureVoltageCompensation(boolean enabled, double maxVoltage)
    {
    }

    public void configureVelocityMeasurements(int periodMS, int windowSize)
    {
    }

    public void configureAbsoluteEncoder()
    {
    }

    public void configureRelativeEncoder()
    {
    }

    public void configureRelativeEncoder(int resolution)
    {
    }

    public void configureEncoderAverageDepth(int depth)
    {
    }

    public void configureVelocityMeasurementPeriod(int periodMS)
    {
    }

    public void configureSmartCurrentLimit(int stallLimit, int freeLimit, int limitRPM)
    {
    }

    public void configureSecondaryCurrentLimit(double limit, int chopCycles)
    {
    }

    public void configureAbsoluteOffset(double zeroOffset)
    {
    }

    public void configurePositionConversionFactor(double ratio)
    {
    }

    public void configureVelocityConversionFactor(double ratio)
    {
    }

    public void configurePositionPIDWrappingSettings(boolean enable, double minInput, double maxInput)
    {
    }

    private void resetPID()
    {
        if (this.currentMode == SparkControlMode.Position ||
            this.currentMode == SparkControlMode.Velocity)
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
