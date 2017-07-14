package edu.wpi.first.wpilibj;

import java.util.Calendar;

public class Timer
{
    private boolean isRunning;
    private double startTime;

    public Timer()
    {
        this.isRunning = false;
    }

    public void start()
    {
        this.isRunning = true;
        this.startTime = Calendar.getInstance().getTime().getTime() / 1000.0;
    }

    public double get()
    {
        if (!isRunning)
        {
            return 0.0;
        }

        double currentTime = Calendar.getInstance().getTime().getTime() / 1000.0;
        return currentTime - this.startTime;
    }
}
