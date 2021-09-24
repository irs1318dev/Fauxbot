package frc.robot.driver.controltasks;

import java.util.Map;

import frc.robot.driver.*;
import frc.robot.driver.common.*;
import frc.robot.driver.common.states.AnalogOperationState;
import frc.robot.driver.common.states.DigitalOperationState;
import frc.robot.driver.common.states.OperationState;

import com.google.inject.Injector;

public abstract class ControlTaskBase implements IControlTask
{
    private Map<IOperation, OperationState> operationStateMap;
    private Injector injector;

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationStateMap indicating the mapping of an operation to its current state
     * @param components to utilize for making any decisions
     */
    @Override
    public void initialize(Map<IOperation, OperationState> operationStateMap, Injector injector)
    {
        this.operationStateMap = operationStateMap;
        this.injector = injector;
    }

    /**
     * Begin the current task.
     */
    public abstract void begin();

    /**
     * Run an iteration of the current task.
     */
    public abstract void update();

    /**
     * Ends the current task, called when it (or a master task) has completed.
     */
    public abstract void end();

    /**
     * Checks whether this task has completed, or whether it should continue being processed.
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    public abstract boolean hasCompleted();

    /**
     * Checks whether this task should be stopped, or whether it should continue being processed.
     * @return true if we should cancel this task (and stop performing any subsequent tasks), otherwise false (to keep processing this task)
     */
    public boolean shouldCancel()
    {
        // if a task needs to be able to cancel itself, it should override this function
        return false;
    }

    /**
     * Stops the current task gracefully (but unexpectedly).
     */
    public void stop()
    {
        // if a task needs to have separate stop/end semantics, it should override this function
        this.end();
    }

    /**
     * Sets the interrupt for the operation state for a given analog operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setAnalogOperationState(AnalogOperation operation, double value)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        ((AnalogOperationState)operationState).setInterruptState(value);
    }

    /**
     * Sets the interrupt for the operation state for a given digital operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setDigitalOperationState(DigitalOperation operation, boolean value)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        ((DigitalOperationState)operationState).setInterruptState(value);
    }

    /**
     * Gets the current state of the operation state for a given analog operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected double getAnalogOperationState(AnalogOperation operation)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        return ((AnalogOperationState)operationState).getState();
    }

    /**
     * Gets the current state of the operation state for a given digital operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected boolean getDigitalOperationState(DigitalOperation operation)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        return ((DigitalOperationState)operationState).getState();
    }

    /**
     * Gets the injector for the robot
     * @return the injector for retrieving the of robot components
     */
    protected Injector getInjector()
    {
        return this.injector;
    }

    /**
     * Gets the Operation State Map
     * @return the operation state map
     */
    protected Map<IOperation, OperationState> getOperationStateMap()
    {
        return this.operationStateMap;
    }
}
