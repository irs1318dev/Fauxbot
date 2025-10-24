package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public final class SequentialTask extends DecisionSequentialTask
{
    private final EnumSet<AnalogOperation> allAffectedAnalogOperations;
    private final EnumSet<DigitalOperation> allAffectedDigitalOperations;

    /**
     * Initializes a new SequentialTask
     * @param tasks to run
     */
    public SequentialTask(IControlTask[] tasks)
    {
        super();

        this.allAffectedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        this.allAffectedDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
        if (tasks != null)
        {
            for (IControlTask task : tasks)
            {
                if (task != null)
                {
                    this.AppendTask(task);
                    this.allAffectedAnalogOperations.addAll(task.getAffectedAnalogOperations());
                    this.allAffectedDigitalOperations.addAll(task.getAffectedDigitalOperations());
                }
            }
        }
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        return this.allAffectedAnalogOperations;
    }

    /**
     * Retrieve the set of digital operations that this task affects.
     * @return set of digital operations that this task affects.
     */
    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        return this.allAffectedDigitalOperations;
    }

    /**
     * Create a sequential task from one or more tasks
     * @param tasks to create the sequence from
     * @return sequential task
     */
    public static SequentialTask Sequence(IControlTask... tasks)
    {
        return new SequentialTask(tasks);
    }
}
