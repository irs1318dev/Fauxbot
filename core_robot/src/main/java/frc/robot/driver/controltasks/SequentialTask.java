package frc.robot.driver.controltasks;

import frc.lib.driver.IControlTask;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public class SequentialTask extends DecisionSequentialTask
{
    /**
     * Initializes a new SequentialTask
     * @param tasks to run
     */
    public SequentialTask(IControlTask[] tasks)
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
    public static SequentialTask Sequence(IControlTask... tasks)
    {
        return new SequentialTask(tasks);
    }
}
