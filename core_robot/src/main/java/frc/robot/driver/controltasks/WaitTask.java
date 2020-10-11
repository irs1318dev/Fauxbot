package frc.robot.driver.controltasks;

/**
 * Task that simply waits for a short period of time.
 * 
 */
public class WaitTask extends TimedTask
{
    /**
     * Initializes a new WaitTask
     * @param duration to wait in seconds
     */
    public WaitTask(double duration)
    {
        super(duration);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        // no-op
    }
}
