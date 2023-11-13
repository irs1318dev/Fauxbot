package frc.robot.driver.controltasks;

import java.util.EnumMap;

import frc.lib.driver.*;
import frc.lib.driver.states.AnalogOperationState;
import frc.lib.driver.states.DigitalOperationState;
import frc.robot.driver.*;

import com.google.inject.Injector;

public abstract class ControlTaskBase implements IControlTask
{
    private EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap;
    private EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap;
    private Injector injector;

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
        this.analogOperationStateMap = analogOperationStateMap;
        this.digitalOperationStateMap = digitalOperationStateMap;
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
        AnalogOperationState operationState = this.analogOperationStateMap.get(operation);
        operationState.setInterruptState(value);
    }

    /**
     * Sets the interrupt for the operation state for a given digital operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setDigitalOperationState(DigitalOperation operation, boolean value)
    {
        DigitalOperationState operationState = this.digitalOperationStateMap.get(operation);
        operationState.setInterruptState(value);
    }

    /**
     * Gets the current state of the operation state for a given analog operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected double getAnalogOperationState(AnalogOperation operation)
    {
        AnalogOperationState operationState = this.analogOperationStateMap.get(operation);
        return operationState.getState();
    }

    /**
     * Gets the current state of the operation state for a given digital operation
     * @param operation to set the interrupt state for
     * @return value to set for the operation
     */
    protected boolean getDigitalOperationState(DigitalOperation operation)
    {
        DigitalOperationState operationState = this.digitalOperationStateMap.get(operation);
        return operationState.getState();
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
     * Gets the Analog Operation State Map
     * @return the operation state map
     */
    protected EnumMap<AnalogOperation, AnalogOperationState> getAnalogOperationStateMap()
    {
        return this.analogOperationStateMap;
    }

    /**
     * Gets the Digital Operation State Map
     * @return the operation state map
     */
    protected EnumMap<DigitalOperation, DigitalOperationState> getDigitalOperationStateMap()
    {
        return this.digitalOperationStateMap;
    }
}
