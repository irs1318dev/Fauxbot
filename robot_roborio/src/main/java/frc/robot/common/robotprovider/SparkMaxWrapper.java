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
    }

    public void setControlMode(SparkMaxControlMode mode)
    {
        int controlMode = 0;
        switch (mode)
        {
            case PercentOutput:
                controlMode = 0;
                break;

            case Velocity:
                controlMode = 1;
                break;

            case Voltage:
                controlMode = 2;
                break;

            case Position:
                controlMode = 3;
                break;
        }

        this.wrappedObject.setParameter(ConfigParameter.kCtrlType, controlMode);
    }

    public void set(double value)
    {
        this.wrappedObject.set(value);
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

    public void setPIDFSmartMotion(double p, double i, double d, double f, int izone, int velocity, int acceleration,  int slotId)
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

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        this.wrappedObject.setParameter(ConfigParameter.kHardLimitFwdEn, enabled);
        this.wrappedObject.setParameter(ConfigParameter.kLimitSwitchFwdPolarity, !normallyOpen);

        LimitSwitchPolarity polarity = LimitSwitchPolarity.kNormallyClosed;
        if (normallyOpen)
        {
            polarity = LimitSwitchPolarity.kNormallyOpen;
        }

        this.wrappedFwdLimitSwitch = this.wrappedObject.getForwardLimitSwitch(polarity);
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        this.wrappedObject.setParameter(ConfigParameter.kHardLimitRevEn, enabled);
        this.wrappedObject.setParameter(ConfigParameter.kLimitSwitchRevPolarity, !normallyOpen);
    }

    public void setInvertOutput(boolean invert)
    {
        this.wrappedObject.setInverted(invert);
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
        this.wrappedObject.setEncPosition(position);
    }

    public void reset()
    {
        this.wrappedObject.setEncPosition(0);
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
