package org.usfirst.frc.team1318.robot.common.wpilib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXWrapper implements ITalonSRX
{
    private static final int slotId = 0;
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
        if (mode == TalonSRXControlMode.Current)
        {
            controlMode = ControlMode.Current;
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

    public void setPIDF(double p, double i, double d, double f)
    {
        this.wrappedObject.config_kP(TalonSRXWrapper.slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(TalonSRXWrapper.slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(TalonSRXWrapper.slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(TalonSRXWrapper.slotId, f, TalonSRXWrapper.timeoutMS);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile)
    {
        this.wrappedObject.config_kP(TalonSRXWrapper.slotId, p, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kI(TalonSRXWrapper.slotId, i, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kD(TalonSRXWrapper.slotId, d, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_kF(TalonSRXWrapper.slotId, f, TalonSRXWrapper.timeoutMS);
        this.wrappedObject.config_IntegralZone(TalonSRXWrapper.slotId, izone, TalonSRXWrapper.timeoutMS);
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
        // what is pidIdx ??
        return this.wrappedObject.getSelectedSensorPosition(TalonSRXWrapper.slotId);
    }

    public double getVelocity()
    {
        // what is pidIdx ??
        return this.wrappedObject.getSelectedSensorVelocity(TalonSRXWrapper.slotId);
    }

    public double getError()
    {
        // what is pidIdx ??
        return this.wrappedObject.getClosedLoopError(TalonSRXWrapper.slotId);
    }
}
