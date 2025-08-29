package frc.robot.driver.controltasks;

import java.util.LinkedList;
import java.util.Queue;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperationModifier;

import com.google.inject.Injector;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * Allows more tasks to be appended to the end based on the flow of the current task
 * 
 */
public abstract class DecisionSequentialTask extends ControlTaskBase
{
    private final Queue<IControlTask> orderedTasks;
    private IControlTask currentTask;
    private boolean shouldCancelTask;

    private boolean isInitialized;

    /**
     * Initializes a new DecisionSequentialTask
     */
    protected DecisionSequentialTask()
    {
        this.orderedTasks = new LinkedList<IControlTask>();
        this.currentTask = null;

        this.shouldCancelTask = false;
        this.isInitialized = false;
    }

    /**
     * Appends a task to run as the current "last" task
     * 
     * @param task to append
     */
    protected void AppendTask(IControlTask task)
    {
        this.orderedTasks.add(task);

        if (this.isInitialized)
        {
            task.initialize(this.getOperationModifier(), this.getInjector());
        }
    }

    /**
     * Extension point that will be available so that child classes can decide what to do after any given task ends
     * @param finishedTask the task that just finished executing
     */
    protected void onTaskCompleted(IControlTask finishedTask)
    {
    }

    /**
     * Extension point that will be available so that child classes can decide what to do after any given task cancels itself
     * @param canceledTask the task that just canceled itself
     * @return true if this task should be stopped, otherwise false
     */
    protected boolean onTaskCanceled(IControlTask canceledTask)
    {
        return true;
    }

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationModifier used for retrieving and modifying operation state
     * @param injector used to retrieve components to utilize for making any decisions
     */
    @Override
    public void initialize(
        IOperationModifier operationModifier,
        Injector injector)
    {
        super.initialize(operationModifier, injector);
        for (IControlTask task : this.orderedTasks)
        {
            task.initialize(operationModifier, injector);
        }

        this.isInitialized = true;
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
        do
        {
            // if there's no current task, find the next one and start it (if any)
            if (this.currentTask == null)
            {
                this.currentTask = this.orderedTasks.poll();

                // if there's no next task to run, then we are done
                if (this.currentTask == null)
                {
                    return;
                }

                this.currentTask.begin();
            }

            if (this.currentTask.hasCompleted())
            {
                this.currentTask.end();
                this.onTaskCompleted(this.currentTask);
                this.currentTask = null;
            }
            else if (this.currentTask.shouldCancel())
            {
                this.shouldCancelTask = this.onTaskCanceled(currentTask);
                if (!this.shouldCancelTask)
                {
                    this.currentTask.stop();
                    this.currentTask = null;
                }
            }
            else
            {
                this.currentTask.update();
            }
        } while (this.currentTask == null);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        if (this.currentTask != null)
        {
            this.currentTask.end();
        }
    }

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    @Override
    public boolean shouldCancel()
    {
        return this.shouldCancelTask;
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        return this.currentTask == null && this.orderedTasks.isEmpty();
    }
}
