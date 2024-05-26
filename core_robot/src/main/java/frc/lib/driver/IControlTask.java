package frc.lib.driver;

import java.util.EnumMap;

import frc.lib.driver.states.AnalogOperationState;
import frc.lib.driver.states.DigitalOperationState;
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
     * @param analogOperationStateMap indicating the mapping of an analog operation to its current state
     * @param digitalOperationStateMap indicating the mapping of a digital operation to its current state
     * @param injector used to retrieve components to utilize for making any decisions
     */
    public void initialize(
        EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap,
        EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap,
        Injector injector);

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
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    public boolean hasCompleted();

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    public boolean shouldCancel();
}
