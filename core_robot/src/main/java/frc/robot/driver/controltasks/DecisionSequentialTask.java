package frc.robot.driver.controltasks;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import frc.robot.driver.IOperation;
import frc.robot.driver.common.IControlTask;
import frc.robot.driver.common.states.OperationState;

import com.google.inject.Injector;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * Allows more tasks to be appended to the end based on the flow of the current task
 * 
 */
public abstract class DecisionSequentialTask extends ControlTaskBase implements IControlTask
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
            task.initialize(this.getOperationStateMap(), this.getInjector());
        }
    }

    /**
     * Extension point that will be availablke so that child classes can decide what to do after any given task ends
     */
    protected void finishedTask()
    {
    }

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationStateMap indicating the mapping of an operation to its current state
     * @param injector used to retrieve the components to utilize for making any decisions
     */
    @Override
    public void initialize(Map<IOperation, OperationState> operationStateMap, Injector injector)
    {
        super.initialize(operationStateMap, injector);
        for (IControlTask task : this.orderedTasks)
        {
            task.initialize(operationStateMap, injector);
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
                this.finishedTask();
                this.currentTask = null;
            }
            else if (this.currentTask.shouldCancel())
            {
                this.shouldCancelTask = true;
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
