package frc.robot.driver.controltasks;

import java.util.EnumMap;

import frc.lib.driver.IControlTask;
import frc.lib.driver.states.AnalogOperationState;
import frc.lib.driver.states.DigitalOperationState;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

import com.google.inject.Injector;

/**
 * Task that holds multiple other tasks and executes them in parallel until certain conditions
 * are met.
 * 
 * AnyTask - a task that continues processing all of the provided tasks until any of them is ready to continue
 * AllTask - a task that continues processing all of the provided tasks until all of them are ready to continue
 * 
 */
public class ConcurrentTask extends ControlTaskBase
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
     * @param analogOperationStateMap indicating the mapping of an analog operation to its current state
     * @param digitalOperationStateMap indicating the mapping of a digital operation to its current state
     * @param injector used to retrieve components to utilize for making any decisions
     */
    @Override
    public void initialize(
        EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap,
        EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap,
        Injector injector)
    {
        super.initialize(analogOperationStateMap, digitalOperationStateMap, injector);
        for (IControlTask task : this.tasks)
        {
            if (task != null)
            {
                task.initialize(analogOperationStateMap, digitalOperationStateMap, injector);
            }
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
            if (task != null)
            {
                task.begin();
            }
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
            if (this.completedTasks[i])
            {
                continue;
            }

            IControlTask task = this.tasks[i];
            if (task == null)
            {
                this.completedTasks[i] = true;
                continue;
            }

            if (task.hasCompleted())
            {
                this.completedTasks[i] = true;
                task.end();
                continue;
            }

            if (task.shouldCancel())
            {
                this.shouldCancelTasks = true;
                continue;
            }

            task.update();
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
                IControlTask task = this.tasks[i];
                if (task != null)
                {
                    task.end();
                }
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
