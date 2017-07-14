package org.usfirst.frc.team1318.robot.driver.controltasks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.states.OperationState;

import com.google.inject.Injector;

/**
 * Task that holds multiple other tasks and executes them sequentially (in order).
 * 
 */
public class SequentialTask extends ControlTaskBase implements IControlTask
{
    private final Queue<IControlTask> orderedTasks;
    private IControlTask currentTask;
    private boolean shouldCancelTask;

    /**
     * Initializes a new SequentialTask
     * @param tasks to run
     */
    public SequentialTask(IControlTask[] tasks)
    {
        this.orderedTasks = new LinkedList<IControlTask>(Arrays.asList(tasks));
        this.currentTask = null;
        this.shouldCancelTask = false;
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

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationStateMap indicating the mapping of an operation to its current state
     * @param injector used to retrieve the components to utilize for making any decisions
     */
    @Override
    public void initialize(Map<Operation, OperationState> operationStateMap, Injector injector)
    {
        super.initialize(operationStateMap, injector);
        for (IControlTask task : this.orderedTasks)
        {
            task.initialize(operationStateMap, injector);
        }
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
        // check whether we should continue with the current task
        if (this.currentTask != null)
        {
            if (this.currentTask.hasCompleted())
            {
                this.currentTask.end();
                this.currentTask = null;
            }
        }

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

        // run the current task and apply the result to the control data
        this.currentTask.update();
        this.shouldCancelTask = this.currentTask.shouldCancel();
    }

    /**
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {
        if (this.currentTask != null)
        {
            this.currentTask.stop();
        }
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
