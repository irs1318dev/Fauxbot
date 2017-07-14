package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import java.util.Map;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Driver.States.OperationState;

/**
 * Task that holds multiple other tasks and executes them in parallel until certain conditions
 * are met.
 * 
 * AnyTask - a task that continues processing all of the provided tasks until any of them is ready to continue
 * AllTask - a task that continues processing all of the provided tasks until all of them are ready to continue
 * 
 */
public class ConcurrentTask extends ControlTaskBase implements IControlTask
{
    private final boolean anyTask;
    private final IControlTask[] tasks;
    private final boolean[] completedTasks;
    private boolean shouldCancelTasks;

    /**
     * Initializes a new ConcurrentTask
     * @param anyTask indicates that we want to use AnyTask semantics as opposed to AllTask semantics
     * @param tasks to run
     */
    private ConcurrentTask(boolean anyTask, IControlTask... tasks)
    {
        this.anyTask = anyTask;
        this.tasks = tasks;
        this.completedTasks = new boolean[tasks.length];
        for (int i = 0; i < this.completedTasks.length; i++)
        {
            this.completedTasks[i] = false;
        }

        this.shouldCancelTasks = false;
    }

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationStateMap indicating the mapping of an operation to its current state
     * @param components to utilize for making any decisions
     */
    @Override
    public void initialize(Map<Operation, OperationState> operationStateMap, ComponentManager components)
    {
        super.initialize(operationStateMap, components);
        for (IControlTask task : this.tasks)
        {
            task.initialize(operationStateMap, components);
        }
    }

    /**
     * Create a task that continues processing all of the provided tasks until any of them is ready to continue
     * @param tasks to run
     * @return a task that runs all of the provided tasks until all of them are ready to continue
     */
    public static IControlTask AnyTasks(IControlTask... tasks)
    {
        return new ConcurrentTask(true, tasks);
    }

    /**
     * Create a task that continues processing all of the provided tasks until all of them are ready to continue
     * @param tasks to run
     * @return a task that runs all of the provided tasks until one of them is ready to continue
     */
    public static IControlTask AllTasks(IControlTask... tasks)
    {
        return new ConcurrentTask(false, tasks);
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        for (IControlTask task : this.tasks)
        {
            task.begin();
        }
    }

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        for (int i = 0; i < this.tasks.length; i++)
        {
            if (!this.completedTasks[i])
            {
                if (this.tasks[i].hasCompleted())
                {
                    this.completedTasks[i] = true;
                    this.tasks[i].end();
                }
                else
                {
                    this.tasks[i].update();

                    if (this.tasks[i].shouldCancel())
                    {
                        this.shouldCancelTasks = true;
                    }
                }
            }
        }
    }

    /**
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {
        for (int i = 0; i < this.tasks.length; i++)
        {
            this.tasks[i].stop();
        }
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        for (int i = 0; i < this.tasks.length; i++)
        {
            if (!this.completedTasks[i])
            {
                this.tasks[i].end();
            }
        }
    }

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    @Override
    public boolean shouldCancel()
    {
        return this.shouldCancelTasks;
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        for (int i = 0; i < this.tasks.length; i++)
        {
            boolean taskHasCompleted = this.completedTasks[i];

            // for AnyTask tasks, return that we're completed (true) if any of them have completed (true).
            if (this.anyTask && taskHasCompleted)
            {
                return true;
            }

            // for AllTask tasks, return that we haven't completed (false) if any hasn't completed (false).
            if (!this.anyTask && !taskHasCompleted)
            {
                return false;
            }
        }

        // AnyTasks return false when none of them are true.  AllTasks return true when none of them are false.
        return !this.anyTask;
    }
}
