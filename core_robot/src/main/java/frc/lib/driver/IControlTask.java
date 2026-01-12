package frc.lib.driver;

import java.util.EnumSet;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

import com.google.inject.Injector;

/**
 * Interface describing a task that that controls the functioning of the robot.
 * 
 */
public interface IControlTask
{
    /**
     * Initialize the task with the mapping of operations to states
     * 
     * @param operationModifier used for retrieving and modifying operation state
     * @param injector          used to retrieve components to utilize for making any decisions
     */
    public void initialize(
        IOperationModifier operationModifier,
        Injector injector);

    /**
     * Retrieve the set of analog operations that this task affects.
     * 
     * @return set of analog operations that this task affects.
     */
    public EnumSet<AnalogOperation> getAffectedAnalogOperations();

    /**
     * Retrieve the set of digital operations that this task affects.
     * 
     * @return set of digital operations that this task affects.
     */
    public EnumSet<DigitalOperation> getAffectedDigitalOperations();

    /**
     * Begin the current task.
     */
    public void begin();

    /**
     * Run an iteration of the current task.
     */
    public void update();

    /**
     * Stops the current task gracefully (but unexpectedly).
     */
    public void stop();

    /**
     * Ends the current task, called when it (or a master task) has completed.
     */
    public void end();

    /**
     * Checks whether this task has completed, or whether it should continue being processed.
     * 
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    public boolean hasCompleted();

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * 
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    public boolean shouldCancel();
}
