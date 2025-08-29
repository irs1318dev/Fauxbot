package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public final class RepeatedSequentialTask extends DecisionSequentialTask
{
    private final EnumSet<AnalogOperation> allAffectedAnalogOperations;
    private final EnumSet<DigitalOperation> allAffectedDigitalOperations;

    /**
     * Initializes a new RepeatedTask
     * @param tasks to run
     */
    public RepeatedSequentialTask(IControlTask[] tasks)
    {
        super();

        this.allAffectedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        this.allAffectedDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
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
    public static RepeatedSequentialTask Sequence(IControlTask... tasks)
    {
        return new RepeatedSequentialTask(tasks);
    }

    /**
     * Extension point that will be available so that child classes can decide what to do after any given task ends
     * @param finishedTask the task that just finished executing
     */
    protected void onTaskCompleted(IControlTask finishedTask)
    {
        this.AppendTask(finishedTask);
    }
}
