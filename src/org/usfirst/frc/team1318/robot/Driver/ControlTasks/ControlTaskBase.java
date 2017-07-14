package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import java.util.Map;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Driver.States.AnalogOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.DigitalOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.OperationState;

public abstract class ControlTaskBase implements IControlTask
{
    private Map<Operation, OperationState> operationStateMap;
    private ComponentManager components;

    /**
     * Initialize the task with the mapping of operations to states
     * @param operationStateMap indicating the mapping of an operation to its current state
     * @param components to utilize for making any decisions
     */
    @Override
    public void initialize(Map<Operation, OperationState> operationStateMap, ComponentManager components)
    {
        this.operationStateMap = operationStateMap;
        this.components = components;
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
     * Stops the current task gracefully (but unexpectedly).
     */
    public abstract void stop();

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
    public abstract boolean shouldCancel();

    /**
     * Sets the interrupt for the operation state for a given analog operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setAnalogOperationState(Operation operation, double value)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        ((AnalogOperationState)operationState).setInterruptState(value);
    }

    /**
     * Sets the interrupt for the operation state for a given digital operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setDigitalOperationState(Operation operation, boolean value)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        ((DigitalOperationState)operationState).setInterruptState(value);
    }

    /**
     * Gets the current state of the operation state for a given analog operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected double getAnalogOperationState(Operation operation)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        return ((AnalogOperationState)operationState).getState();
    }

    /**
     * Gets the current state of the operation state for a given digital operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected boolean getDigitalOperationState(Operation operation)
    {
        OperationState operationState = this.operationStateMap.get(operation);
        return ((DigitalOperationState)operationState).getState();
    }

    /**
     * Gets the component manager for the robot
     * @return the set of robot components
     */
    protected ComponentManager getComponents()
    {
        return this.components;
    }
}
