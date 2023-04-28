package frc.robot.driver.controltasks;

import frc.lib.robotprovider.ITimer;

/**
 * Abstract class defining a task that lasts only for a certain duration.
 * 
 */
public abstract class TimedTask extends ControlTaskBase
{
    protected final double duration;
    protected ITimer timer;
    protected Double startTime;

    /**
     * Initializes a new TimedTask
     * @param duration to perform the task in seconds
     */
    protected TimedTask(double duration)
    {
        this.duration = duration;
        this.startTime = null;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.timer = this.getInjector().getInstance(ITimer.class);

        this.startTime = this.timer.get();
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    public abstract void update();

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
    }

    /**
     * Gets the ratio of the total duration that has elapsed
     * @return value between approximately 0.0 and 1.0
     */
    protected double getRatioComplete()
    {
        double ratioComplete = (this.timer.get() - this.startTime) / this.duration;

        if (ratioComplete < 0.0)
        {
            return 0.0;
        }
        else if (ratioComplete > 1.0)
        {
            return 1.0;
        }

        return ratioComplete;
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        return this.timer.get() >= this.startTime + this.duration;
    }
}
