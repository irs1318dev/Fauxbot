package frc.robot.common.robotprovider;

import com.revrobotics.*;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.*;
import com.revrobotics.CANSparkMaxLowLevel.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMaxWrapper implements ISparkMax
{
    final CANSparkMax wrappedObject;
    private CANPIDController pidController;
    private CANEncoder wrappedEncoder;
    private CANDigitalInput wrappedFwdLimitSwitch;
    private CANDigitalInput wrappedRevLimitSwitch;

    private SparkMaxControlMode currentMode;
    private int currentSlot;

    public SparkMaxWrapper(int deviceID, SparkMaxMotorType motorType)
    {
        MotorType type = MotorType.kBrushless;
        switch (motorType)
        {
            case Brushed:
                type = MotorType.kBrushed;
                break;

            case Brushless:
                type = MotorType.kBrushless;
                break;
        }

        this.wrappedObject = new CANSparkMax(deviceID, type);
        this.currentMode = SparkMaxControlMode.PercentOutput;
        this.currentSlot = 0;
    }

    public void setControlMode(SparkMaxControlMode mode)
    {
        this.currentMode = mode;
    }

    public void setSelectedSlot(int slotId)
    {
        this.currentSlot = slotId;
    }

    public void set(double value)
    {
        if (this.currentMode != SparkMaxControlMode.PercentOutput &&
            this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        ControlType controlType;
        switch (this.currentMode)
        {
            case PercentOutput:
                this.wrappedObject.set(value);
                return;

            case Position:
                controlType = ControlType.kPosition;
                break;

            case Velocity:
                controlType = ControlType.kVelocity;
                break;

            case Voltage:
                controlType = ControlType.kVoltage;
                break;

            default:
                throw new RuntimeException("unexpected control mode " + this.currentMode);
        }

        this.pidController.setReference(value, controlType, this.currentSlot);
    }

    public void follow(ISparkMax sparkMax)
    {
        this.wrappedObject.follow(((SparkMaxWrapper)sparkMax).wrappedObject);
    }

    public void setFeedbackFramePeriod(SparkMaxPeriodicFrameType frameType, int periodMS)
    {
        PeriodicFrame type = PeriodicFrame.kStatus0;
        switch (frameType)
        {
            case Status0:
                type = PeriodicFrame.kStatus0;
                break;
            case Status1:
                type = PeriodicFrame.kStatus1;
                break;
            case Status2:
                type = PeriodicFrame.kStatus2;
                break;
        }

        this.wrappedObject.setPeriodicFramePeriod(type, periodMS);
    }

    public void setVelocityMeasurements(int periodMS, int windowSize)
    {
        this.wrappedEncoder.setAverageDepth(windowSize);
        this.wrappedEncoder.setMeasurementPeriod(periodMS);
    }

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
    }

    public void setPIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
        this.pidController.setOutputRange(minOutput, maxOutput);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
        this.pidController.setIZone(izone, slotId);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double minOutput, double maxOutput, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
        this.pidController.setIZone(izone, slotId);
        this.pidController.setOutputRange(minOutput, maxOutput);
    }

    public void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
        this.pidController.setIZone(izone, slotId);
        this.pidController.setSmartMotionMaxVelocity(velocity, slotId);
        this.pidController.setSmartMotionMaxAccel(acceleration, slotId);
    }

    public void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration, double minOutput, double maxOutput, int slotId)
    {
        if (this.pidController == null)
        {
            this.pidController = this.wrappedObject.getPIDController();
        }

        this.pidController.setP(p, slotId);
        this.pidController.setI(i, slotId);
        this.pidController.setD(d, slotId);
        this.pidController.setFF(f, slotId);
        this.pidController.setIZone(izone, slotId);
        this.pidController.setSmartMotionMaxVelocity(velocity, slotId);
        this.pidController.setSmartMotionMaxAccel(acceleration, slotId);
        this.pidController.setOutputRange(minOutput, maxOutput);
    }

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchPolarity polarity = LimitSwitchPolarity.kNormallyClosed;
        if (normallyOpen)
        {
            polarity = LimitSwitchPolarity.kNormallyOpen;
        }

        this.wrappedFwdLimitSwitch = this.wrappedObject.getForwardLimitSwitch(polarity);
        this.wrappedFwdLimitSwitch.enableLimitSwitch(enabled);
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchPolarity polarity = LimitSwitchPolarity.kNormallyClosed;
        if (normallyOpen)
        {
            polarity = LimitSwitchPolarity.kNormallyOpen;
        }

        this.wrappedRevLimitSwitch = this.wrappedObject.getForwardLimitSwitch(polarity);
        this.wrappedRevLimitSwitch.enableLimitSwitch(enabled);
    }

    public void setInvertOutput(boolean invert)
    {
        this.wrappedObject.setInverted(invert);
    }

    public void setInvertSensor(boolean invert)
    {
        this.wrappedEncoder.setInverted(invert);
    }

    public void setNeutralMode(MotorNeutralMode neutralMode)
    {
        IdleMode mode;
        if (neutralMode == MotorNeutralMode.Brake)
        {
            mode = IdleMode.kBrake;
        }
        else
        {
            mode = IdleMode.kCoast;
        }

        this.wrappedObject.setIdleMode(mode);
    }

    public void stop()
    {
        this.wrappedObject.stopMotor();
    }

    public void setPosition(double position)
    {
        if (this.wrappedEncoder == null)
        {
            this.wrappedEncoder = this.wrappedObject.getEncoder();
            if (this.wrappedEncoder == null)
            {
                return;
            }
        }

        this.wrappedEncoder.setPosition(position);
    }

    public void reset()
    {
        this.setPosition(0.0);
    }

    public double getPosition()
    {
        if (this.wrappedEncoder == null)
        {
            this.wrappedEncoder = this.wrappedObject.getEncoder();
            if (wrappedEncoder == null)
            {
                return 0.0;
            }
        }

        return this.wrappedEncoder.getPosition();
    }

    public double getVelocity()
    {
        if (this.wrappedEncoder == null)
        {
            this.wrappedEncoder = this.wrappedObject.getEncoder();
            if (wrappedEncoder == null)
            {
                return 0.0;
            }
        }

        return this.wrappedEncoder.getVelocity();
    }

    public boolean getForwardLimitSwitchStatus()
    {
        if (this.wrappedFwdLimitSwitch == null)
        {
            return false;
        }

        return this.wrappedFwdLimitSwitch.get();
    }

    public boolean getReverseLimitSwitchStatus()
    {
        if (this.wrappedRevLimitSwitch == null)
        {
            return false;
        }

        return this.wrappedRevLimitSwitch.get();
    }
}
