package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Task that simply waits for a short period of time.
 * 
 */
public final class WaitTask extends TimedTask
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
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        return EnumSet.noneOf(AnalogOperation.class);
    }

    /**
     * Retrieve the set of digital operations that this task affects.
     * @return set of digital operations that this task affects.
     */
    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        return EnumSet.noneOf(DigitalOperation.class);
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
