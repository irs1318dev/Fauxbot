package org.usfirst.frc.team1318.robot.common.wpilib;

import javax.inject.Singleton;

import edu.wpi.first.wpilibj.Timer;

@Singleton
public class TimerWrapper implements ITimer
{
    private final Timer wrappedObject;

    public TimerWrapper()
    {
        this.wrappedObject = new Timer();
    }

    public void start()
    {
        this.wrappedObject.start();
    }

    public void stop()
    {
        this.wrappedObject.stop();
    }

    public double get()
    {
        return this.wrappedObject.get();
    }

    public void reset()
    {
        this.wrappedObject.reset();
    }
}
