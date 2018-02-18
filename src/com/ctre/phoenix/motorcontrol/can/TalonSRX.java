package com.ctre.phoenix.motorcontrol.can;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;

public class TalonSRX
{
    public TalonSRX(int deviceNumber)
    {
    }

    public void config_kP(int slotid, double p, int timeoutms)
    {
    }

    public void config_kI(int slotid, double i, int timeoutms)
    {
    }

    public void config_kD(int slotid, double d, int timeoutms)
    {
    }

    public void config_kF(int slotid, double f, int timeoutms)
    {
    }

    public void config_IntegralZone(int slotid, int izone, int timeoutms)
    {
    }

    public void configClosedloopRamp(double closeLoopRampRate, int timeoutms)
    {
    }

    public int getSelectedSensorPosition(int slotid)
    {
        return 0;
    }

    public double getSelectedSensorVelocity(int slotid)
    {
        return 0;
    }

    public double getClosedLoopError(int slotid)
    {
        return 0;
    }

    public void setInverted(boolean invert)
    {
    }

    public void setSensorPhase(boolean invert)
    {
    }

    public void setNeutralMode(NeutralMode mode)
    {
    }

    public void set(ControlMode mode, double value)
    {
    }

    public void configSelectedFeedbackSensor(FeedbackDevice device, int pididx, int i)
    {
    }

    public void selectProfileSlot(int slotId, int pididx)
    {
    }

    public void configForwardLimitSwitchSource(LimitSwitchSource source, LimitSwitchNormal type, int timeoutms)
    {
    }

    public void configReverseLimitSwitchSource(LimitSwitchSource source, LimitSwitchNormal type, int timeoutms)
    {
    }

    public void setSelectedSensorPosition(int i, int pididx, int timeoutms)
    {
    }

    public SensorCollection getSensorCollection()
    {
        return null;
    }

    public void configVoltageCompSaturation(double maxVoltage, int timeoutms)
    {
    }

    public void enableVoltageCompensation(boolean enabled)
    {
    }
}
