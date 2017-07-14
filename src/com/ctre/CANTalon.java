package com.ctre;

public class CANTalon
{

    public CANTalon(int deviceNumber)
    {
        // TODO Auto-generated constructor stub
    }

    public enum TalonControlMode
    {
        Current, Disabled, Follower, Position, Speed, Voltage, PercentVbus

    }

    public void set(double value)
    {
        // TODO Auto-generated method stub
        
    }

    public void changeControlMode(TalonControlMode controlMode)
    {
        // TODO Auto-generated method stub
        
    }

    public void setP(double p)
    {
        // TODO Auto-generated method stub
        
    }

    public void setI(double i)
    {
        // TODO Auto-generated method stub
        
    }

    public void setD(double d)
    {
        // TODO Auto-generated method stub
        
    }

    public void setF(double f)
    {
        // TODO Auto-generated method stub
        
    }

    public void setPID(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile)
    {
        // TODO Auto-generated method stub
        
    }

    public void reverseOutput(boolean flip)
    {
        // TODO Auto-generated method stub
        
    }

    public void reverseSensor(boolean flip)
    {
        // TODO Auto-generated method stub
        
    }

    public void enableBrakeMode(boolean brake)
    {
        // TODO Auto-generated method stub
        
    }

    public void reset()
    {
        // TODO Auto-generated method stub
        
    }

    public int getEncPosition()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public double getEncVelocity()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public double getError()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
