package org.usfirst.frc.team1318.robot.common.wpilib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXWrapper implements ITalonSRX
{
    private static final int pidIdx = 0;
    private static final int timeoutMS = 10;

    private final TalonSRX wrappedObject;

    private ControlMode controlMode;

    public TalonSRXWrapper(int deviceNumber)
    {
        this.wrappedObject = new TalonSRX(deviceNumber);
        this.controlMode = ControlMode.PercentOutput;
    }

    public void set(double value)
    {
        this.wrappedObject.set(this.controlMode, value);
    }

    public void setControlMode(TalonSRXControlMode mode)
    {
        if (mode == TalonSRXControlMode.PercentOutput)
        {
            this.controlMode = ControlMode.PercentOutput;
        }
        else if (mode == TalonSRXControlMode.Disabled)
        {
            this.controlMode = ControlMode.Disabled;
        }
        else if (mode == TalonSRXControlMode.Follower)
        {
            this.controlMode = ControlMode.Follower;
        }
        else if (mode == TalonSRXControlMode.Position)
        {
            this.controlMode = ControlMode.Position;
        }
        else if (mode == TalonSRXControlMode.MotionMagicPosition)
        {
            this.controlMode = ControlMode.MotionMagic;
        }
        else if (mode == TalonSRXControlMode.Velocity)
        {
            this.controlMode = ControlMode.Velocity;
        }
    }

    public void setSensorType(TalonSRXFeedbackDevice feedbackDevice)
    {
        FeedbackDevice device;
        if (feedbackDevice == TalonSRXFeedbackDevice.QuadEncoder)
        {
            device = FeedbackDevice.QuadEncoder;
        }
        else
        {
            device = FeedbackDevice.None;
        }

        this.wrappedObject.configSelectedFeedbackSensor(device, TalonSRXWrapper.pidIdx, 0);
    }

    public void setSelectedSlot(int slotId)
    {
        this.wrappedObject.selectProfileSlot(slotId, TalonSRXWrapper.pidIdx);
    }

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonSRXWrapper.timeoutMS);
    }

    public void setMotionMagicPIDF(double p, double i, double d, double f, int velocity, int acceleration, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.configMotionCruiseVelocity(velocity, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.configMotionAcceleration(acceleration, TalonSRXWrapper.timeoutMS);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_IntegralZone(slotId, izone, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.configClosedloopRamp(closeLoopRampRate, TalonSRXWrapper.timeoutMS);
    }

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchSource source = LimitSwitchSource.Deactivated;
        if (enabled)
        {
            source = LimitSwitchSource.FeedbackConnector;
        }

        LimitSwitchNormal type = LimitSwitchNormal.NormallyClosed;
        if (normallyOpen)
        {
            type = LimitSwitchNormal.NormallyOpen;
        }

        this.wrappedObject.configForwardLimitSwitchSource(
            source,
            type,
            TalonSRXWrapper.timeoutMS);
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchSource source = LimitSwitchSource.Deactivated;
        if (enabled)
        {
            source = LimitSwitchSource.FeedbackConnector;
        }

        LimitSwitchNormal type = LimitSwitchNormal.NormallyClosed;
        if (normallyOpen)
        {
            type = LimitSwitchNormal.NormallyOpen;
        }

        this.wrappedObject.configReverseLimitSwitchSource(
            source,
            type,
            TalonSRXWrapper.timeoutMS);
    }

    public void setInvertOutput(boolean invert)
    {
        this.wrappedObject.setInverted(invert);
    }

    public void setInvertSensor(boolean invert)
    {
        this.wrappedObject.setSensorPhase(invert);
    }

    public void setNeutralMode(TalonSRXNeutralMode neutralMode)
    {
        NeutralMode mode;
        if (neutralMode == TalonSRXNeutralMode.Brake)
        {
            mode = NeutralMode.Brake;
        }
        else
        {
            mode = NeutralMode.Coast;
        }

        this.wrappedObject.setNeutralMode(mode);
    }

    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
        this.wrappedObject.configVoltageCompSaturation(maxVoltage, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.enableVoltageCompensation(enabled);
    }

    public void stop()
    {
        this.wrappedObject.set(ControlMode.Disabled, 0.0);
    }

    public void setPosition(int position)
    {
        this.wrappedObject.setSelectedSensorPosition(position, TalonSRXWrapper.pidIdx, TalonSRXWrapper.timeoutMS);
    }

    public void reset()
    {
        this.wrappedObject.setSelectedSensorPosition(0, TalonSRXWrapper.pidIdx, TalonSRXWrapper.timeoutMS);
    }

    public int getPosition()
    {
        return this.wrappedObject.getSelectedSensorPosition(TalonSRXWrapper.pidIdx);
    }

    public double getVelocity()
    {
        return this.wrappedObject.getSelectedSensorVelocity(TalonSRXWrapper.pidIdx);
    }

    public double getError()
    {
        return this.wrappedObject.getClosedLoopError(TalonSRXWrapper.pidIdx);
    }

    public TalonSRXLimitSwitchStatus getLimitSwitchStatus()
    {
        SensorCollection collection = this.wrappedObject.getSensorCollection();

        return new TalonSRXLimitSwitchStatus(
            collection.isFwdLimitSwitchClosed(),
            collection.isRevLimitSwitchClosed());
    }
}
