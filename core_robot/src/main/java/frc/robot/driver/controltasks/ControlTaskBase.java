package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.*;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.*;
import frc.robot.driver.*;

import com.google.inject.Injector;

public abstract class ControlTaskBase implements IControlTask
{
    private IOperationModifier operationModifier;
    private Injector injector;

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
        this.operationModifier = operationModifier;
        this.injector = injector;
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    public abstract EnumSet<AnalogOperation> getAffectedAnalogOperations();

    /**
     * Retrieve the set of digital operations that this task affects.
     * @return set of digital operations that this task affects.
     */
    public abstract EnumSet<DigitalOperation> getAffectedDigitalOperations();

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
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // if we are cool with throwing exceptions (testing), check if the operation is in the
            // set of operations affected by this task and throw an exception if it is not
            ExceptionHelpers.Assert(
                this.getAffectedAnalogOperations().contains(operation),
                "%s not contained in the set of affected operations for this task!",
                operation.toString());
        }

        this.operationModifier.setAnalogOperationValue(operation, value);
    }

    /**
     * Sets the interrupt for the operation state for a given digital operation to the provided value 
     * @param operation to set the interrupt state for
     * @param value to set as the interrupt
     */
    protected void setDigitalOperationState(DigitalOperation operation, boolean value)
    {
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // if we are cool with throwing exceptions (testing), check if the operation is in the
            // set of operations affected by this task and throw an exception if it is not
            ExceptionHelpers.Assert(
                this.getAffectedDigitalOperations().contains(operation),
                "%s not contained in the set of affected operations for this task!",
                operation.toString());
        }

        this.operationModifier.setDigitalOperationValue(operation, value);
    }

    /**
     * Gets the current state of the operation state for a given analog operation
     * @param operation to get the interrupt state for
     * @return value for the operation
     */
    protected double getAnalogOperationState(AnalogOperation operation)
    {
        return this.operationModifier.getAnalog(operation);
    }

    /**
     * Gets the current state of the operation state for a given digital operation
     * @param operation to get the interrupt state for
     * @return value for the operation
     */
    protected boolean getDigitalOperationState(DigitalOperation operation)
    {
        return this.operationModifier.getDigital(operation);
    }

    /**
     * Gets the current operation context.
     * @return the current operation context
     */
    protected OperationContext getOperationContext()
    {
        return this.operationModifier.getContext();
    }

    /**
     * Sets the current operation context.
     * @param context the current operation context
     */
    protected void setOperationContext(OperationContext context)
    {
        this.operationModifier.setContext(context);
    }

    /**
     * Gets the injector for the robot
     * @return the injector for retrieving the of robot components
     */
    protected Injector getInjector()
    {
        return this.injector;
    }

    protected IOperationModifier getOperationModifier()
    {
        return this.operationModifier;
    }
}
