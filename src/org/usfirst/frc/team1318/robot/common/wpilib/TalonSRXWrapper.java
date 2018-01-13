package org.usfirst.frc.team1318.robot.common.wpilib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
        this.controlMode = ControlMode.Current;
    }

    public void set(double value)
    {
        this.wrappedObject.set(this.controlMode, value);
    }

    public void changeControlMode(TalonSRXControlMode mode)
    {
        ControlMode controlMode;
        if (mode == TalonSRXControlMode.PercentOutput)
        {
            controlMode = ControlMode.PercentOutput;
        }
        else if (mode == TalonSRXControlMode.Disabled)
        {
            controlMode = ControlMode.Disabled;
        }
        else if (mode == TalonSRXControlMode.Follower)
        {
            controlMode = ControlMode.Follower;
        }
        else if (mode == TalonSRXControlMode.Position)
        {
            controlMode = ControlMode.Position;
        }
        else if (mode == TalonSRXControlMode.Velocity)
        {
            controlMode = ControlMode.Velocity;
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

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonSRXWrapper.timeoutMS);
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

    public void invertOutput(boolean invert)
    {
        this.wrappedObject.setInverted(invert);
    }

    public void invertSensor(boolean invert)
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

    public void reset()
    {
        this.wrappedObject.set(ControlMode.Disabled, 0.0);
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
}
