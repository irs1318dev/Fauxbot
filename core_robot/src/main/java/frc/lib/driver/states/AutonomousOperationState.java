package frc.lib.driver.states;

import java.util.EnumMap;
import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperation;
import frc.lib.robotprovider.IJoystick;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.Shift;

/**
 * The state of the current autonomous operation.
 *
 */
public class AutonomousOperationState extends OperationState implements IMacroOperationState
{
    private final EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap;
    private final EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap;

    private final AnalogOperation[] allAnalogOperations;
    private final DigitalOperation[] allDigitalOperations;
    private final IOperation[] allOperations;

    private IControlTask task;

    private boolean hasBegun;
    private boolean shouldEnd;
    private boolean hasEnded;
    private boolean isInterrupted;

    public AutonomousOperationState(
        IControlTask task,
        EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap,
        EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap)
    {
        super(null);

        this.analogOperationStateMap = analogOperationStateMap;
        this.digitalOperationStateMap = digitalOperationStateMap;

        this.allAnalogOperations = AnalogOperation.values();
        this.allDigitalOperations = DigitalOperation.values();

        int i = 0;
        this.allOperations = new IOperation[this.allAnalogOperations.length + this.allDigitalOperations.length];
        for (AnalogOperation analogOperation : this.allAnalogOperations)
        {
            this.allOperations[i++] = analogOperation;
        }

        for (DigitalOperation digitalOperation : this.allDigitalOperations)
        {
            this.allOperations[i++] = digitalOperation;
        }

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
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick[] joysticks, EnumSet<Shift> activeShifts)
    {
        return false;
    }

    public AnalogOperation[] getMacroCancelAnalogOperations()
    {
        return this.allAnalogOperations;
    }

    public DigitalOperation[] getMacroCancelDigitalOperations()
    {
        return this.allDigitalOperations;
    }

    public IOperation[] getAffectedOperations()
    {
        return this.allOperations;
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
                this.analogOperationStateMap.get(operation).setIsInterrupted(false);
            }

            for (DigitalOperation operation : this.allDigitalOperations)
            {
                this.digitalOperationStateMap.get(operation).setIsInterrupted(false);
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
                    this.analogOperationStateMap.get(operation).setIsInterrupted(false);
                }

                for (DigitalOperation operation : this.allDigitalOperations)
                {
                    this.digitalOperationStateMap.get(operation).setIsInterrupted(false);
                }

                return;
            }

            if (!this.hasBegun)
            {
                for (AnalogOperation operation : this.allAnalogOperations)
                {
                    this.analogOperationStateMap.get(operation).setIsInterrupted(true);
                }

                for (DigitalOperation operation : this.allDigitalOperations)
                {
                    this.digitalOperationStateMap.get(operation).setIsInterrupted(true);
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
            this.analogOperationStateMap.get(operation).setIsInterrupted(false);
        }

        for (DigitalOperation operation : this.allDigitalOperations)
        {
            this.digitalOperationStateMap.get(operation).setIsInterrupted(false);
        }
    }
}
