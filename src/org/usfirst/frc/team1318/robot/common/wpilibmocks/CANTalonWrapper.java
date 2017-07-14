package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class CANTalonWrapper implements ICANTalon
{
    private final CANTalon wrappedObject;

    public CANTalonWrapper(int deviceNumber)
    {
        this.wrappedObject = new CANTalon(deviceNumber);
    }

    public void set(double value)
    {
        this.wrappedObject.set(value);
    }

    public void changeControlMode(CANTalonControlMode mode)
    {
        TalonControlMode controlMode;
        if (mode == CANTalonControlMode.Current)
        {
            controlMode = TalonControlMode.Current;
        }
        else if (mode == CANTalonControlMode.Disabled)
        {
            controlMode = TalonControlMode.Disabled;
        }
        else if (mode == CANTalonControlMode.Follower)
        {
            controlMode = TalonControlMode.Follower;
        }
        else if (mode == CANTalonControlMode.Position)
        {
            controlMode = TalonControlMode.Position;
        }
        else if (mode == CANTalonControlMode.Speed)
        {
            controlMode = TalonControlMode.Speed;
        }
        else if (mode == CANTalonControlMode.Voltage)
        {
            controlMode = TalonControlMode.Voltage;
        }
        else // if (mode == CANTalonControlMode.PercentVbus)
        {
            controlMode = TalonControlMode.PercentVbus;
        }

        this.wrappedObject.changeControlMode(controlMode);
    }

    public void setPIDF(double p, double i, double d, double f)
    {
        this.wrappedObject.setP(p);
        this.wrappedObject.setI(i);
        this.wrappedObject.setD(d);
        this.wrappedObject.setF(f);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile)
    {
        this.wrappedObject.setPID(p, i, d, f, izone, closeLoopRampRate, profile);
    }

    public void reverseOutput(boolean flip)
    {
        this.wrappedObject.reverseOutput(flip);
    }

    public void reverseSensor(boolean flip)
    {
        this.wrappedObject.reverseSensor(flip);
    }

    public void enableBrakeMode(boolean brake)
    {
        this.wrappedObject.enableBrakeMode(brake);
    }

    public void reset()
    {
        this.wrappedObject.reset();
    }

    public int getTicks()
    {
        return this.wrappedObject.getEncPosition();
    }

    public double getSpeed()
    {
        return this.wrappedObject.getEncVelocity();
    }

    public double getError()
    {
        return this.wrappedObject.getError();
    }
}
