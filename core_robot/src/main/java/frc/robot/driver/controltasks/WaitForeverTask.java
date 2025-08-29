package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Task that waits forever.  This task can be used as a way to give up if a certain condition isn't met (by pairing it with an AnyTask)
 * 
 */
public final class WaitForeverTask extends ControlTaskBase
{
    /**
     * Initializes a new WaitForeverTask
     */
    public WaitForeverTask()
    {
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
     * Begin the current task
     */
    @Override
    public void begin()
    {
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        return false;
    }
}
