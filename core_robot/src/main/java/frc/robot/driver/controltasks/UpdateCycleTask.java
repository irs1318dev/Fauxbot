package frc.robot.driver.controltasks;

/**
 * Abstract class defining a task that lasts only for a certain number of update cycles.
 * 
 */
public abstract class UpdateCycleTask extends ControlTaskBase
{
    private final int requiredUpdates;

    protected int currentUpdates;

    /**
     * Initializes a new UpdateCycleTask
     * @param requiredUpdates to perform the task
     */
    protected UpdateCycleTask(int requiredUpdates)
    {
        this.requiredUpdates = requiredUpdates;
        this.currentUpdates = 0;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.currentUpdates = 0;
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.currentUpdates++;
    }

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
        double ratioComplete = (double)this.currentUpdates / (double)this.requiredUpdates;

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
        return this.currentUpdates >= this.requiredUpdates;
    }
}
