package frc.lib.robotprovider;

import javax.inject.Singleton;
import java.util.Calendar;

@Singleton
public class FauxbotTimer implements ITimer
{
    private boolean isRunning;
    private double startTime;
    private double accumulatedTime;

    public FauxbotTimer()
    {
        this.accumulatedTime = 0.0;
        this.isRunning = false;
    }

    public void start()
    {
        this.isRunning = true;
        this.startTime = Calendar.getInstance().getTime().getTime() / 1000.0;
    }

    public double get()
    {
        if (!this.isRunning)
        {
            return this.accumulatedTime;
        }

        double currentTime = Calendar.getInstance().getTime().getTime() / 1000.0;
        return this.accumulatedTime + (currentTime - this.startTime);
    }

    public void stop()
    {
        this.accumulatedTime = this.get();
        this.isRunning = false;
    }

    public void reset()
    {
        this.accumulatedTime = 0.0;
        this.startTime = Calendar.getInstance().getTime().getTime() / 1000.0;
    }
}
