package frc.robot.driver.controltasks;

import frc.lib.driver.IControlTask;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public class RepeatedSequentialTask extends DecisionSequentialTask
{
    /**
     * Initializes a new RepeatedTask
     * @param tasks to run
     */
    public RepeatedSequentialTask(IControlTask[] tasks)
    {
        super();

        for (IControlTask task : tasks)
        {
            if (task != null)
            {
                this.AppendTask(task);
            }
        }
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
    protected void finishedTask(IControlTask finishedTask)
    {
        this.AppendTask(finishedTask);
    }
}
