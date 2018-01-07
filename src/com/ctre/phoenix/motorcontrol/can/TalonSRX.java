package com.ctre.phoenix.motorcontrol.can;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class TalonSRX
{
    public TalonSRX(int deviceNumber)
    {
        // TODO Auto-generated constructor stub
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
    public void set(ControlMode disabled, double value)
    {
    }
}
