package frc.lib.driver.states;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperationModifier;
import frc.lib.robotprovider.IJoystick;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.OperationContext;
import frc.robot.driver.Shift;

/**
 * The state of the current autonomous operation.
 *
 */
public class AutonomousOperationState extends OperationState implements IMacroOperationState
{
    private final IOperationModifier operationModifier;

    private final EnumSet<AnalogOperation> allAnalogOperations;
    private final EnumSet<DigitalOperation> allDigitalOperations;

    private IControlTask task;

    private boolean hasBegun;
    private boolean shouldEnd;
    private boolean hasEnded;
    private boolean isInterrupted;

    public AutonomousOperationState(
        IControlTask task,
        IOperationModifier operationModifier)
    {
        super(null);

        this.operationModifier = operationModifier;

        this.allAnalogOperations = EnumSet.allOf(AnalogOperation.class);
        this.allDigitalOperations = EnumSet.allOf(DigitalOperation.class);

        this.task = task;

        this.hasBegun = false;
        this.shouldEnd = false;
        this.hasEnded = false;
        this.isInterrupted = false;
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    @Override
    public void setIsInterrupted(boolean enable)
    {
        this.isInterrupted = true;
    }

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    @Override
    public boolean getIsInterrupted()
    {
        return this.isInterrupted;
    }

    /**
     * Checks whether the operation state should change based on the joysticks and active stifts. 
     * @param joysticks to update from
     * @param activeShifts shifts currently applied by operator
     * @param currentContext operation context currently applied to the driver 
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick[] joysticks, EnumSet<Shift> activeShifts, OperationContext currentContext)
    {
        return false;
    }

    public EnumSet<AnalogOperation> getMacroCancelAnalogOperations()
    {
        return this.allAnalogOperations;
    }

    public EnumSet<DigitalOperation> getMacroCancelDigitalOperations()
    {
        return this.allDigitalOperations;
    }

    public boolean getIsActive()
    {
        return !this.hasEnded;
    }

    public void run()
    {
        if (this.shouldEnd)
        {
            for (AnalogOperation operation : this.allAnalogOperations)
            {
                this.operationModifier.setAnalogOperationInterrupt(operation, false);
            }

            for (DigitalOperation operation : this.allDigitalOperations)
            {
                this.operationModifier.setDigitalOperationInterrupt(operation, false);
            }

            this.shouldEnd = false;
            this.hasEnded = true;
        }
        else if (!this.hasEnded)
        {
            if (this.isInterrupted)
            {
                this.hasEnded = true;
                if (this.hasBegun)
                {
                    this.task.stop();
                    this.task = null;
                }

                for (AnalogOperation operation : this.allAnalogOperations)
                {
                    this.operationModifier.setAnalogOperationInterrupt(operation, false);
                }

                for (DigitalOperation operation : this.allDigitalOperations)
                {
                    this.operationModifier.setDigitalOperationInterrupt(operation, false);
                }

                return;
            }

            if (!this.hasBegun)
            {
                for (AnalogOperation operation : this.allAnalogOperations)
                {
                    this.operationModifier.setAnalogOperationInterrupt(operation, true);
                }

                for (DigitalOperation operation : this.allDigitalOperations)
                {
                    this.operationModifier.setDigitalOperationInterrupt(operation, true);
                }

                // if we haven't begun, begin
                this.task.begin();
                this.hasBegun = true;
            }

            boolean complete = this.task.hasCompleted();
            boolean cancel = this.task.shouldCancel();
            if (complete || cancel)
            {
                if (complete)
                {
                    this.task.end();
                }
                else
                {
                    this.task.stop();
                }

                this.shouldEnd = true;
            }
            else
            {
                // run the current task and apply the result to the state
                this.task.update();
            }
        }
    }

    public void cancel()
    {
        this.hasEnded = true;
        this.task.stop();
        this.task = null;

        for (AnalogOperation operation : this.allAnalogOperations)
        {
            this.operationModifier.setAnalogOperationInterrupt(operation, false);
        }

        for (DigitalOperation operation : this.allDigitalOperations)
        {
            this.operationModifier.setDigitalOperationInterrupt(operation, false);
        }
    }
}
