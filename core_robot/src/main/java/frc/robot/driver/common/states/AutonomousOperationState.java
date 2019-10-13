package frc.robot.driver.common.states;

import java.util.Map;
import java.util.Set;

import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.IOperation;
import frc.robot.driver.Shift;
import frc.robot.driver.common.IControlTask;

/**
 * The state of the current autonomous operation.
 *
 */
public class AutonomousOperationState extends OperationState implements IMacroOperationState
{
    private final Map<IOperation, OperationState> operationStateMap;

    private IControlTask task;

    private boolean hasBegun;
    private boolean shouldEnd;
    private boolean hasEnded;
    private boolean isInterrupted;

    public AutonomousOperationState(
        IControlTask task,
        Map<IOperation, OperationState> operationStateMap)
    {
        super(null);

        this.operationStateMap = operationStateMap;
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
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick driver, IJoystick coDriver, Shift activeShifts)
    {
        return false;
    }

    public IOperation[] getMacroCancelOperations()
    {
        return this.getAffectedOperations();
    }

    public IOperation[] getAffectedOperations()
    {
        Set<IOperation> keys = this.operationStateMap.keySet();
        IOperation[] keyArray = new IOperation[keys.size()];
        return (IOperation[])keys.toArray(keyArray);
    }

    public boolean getIsActive()
    {
        return !this.hasEnded;
    }

    public void run()
    {
        if (this.shouldEnd)
        {
            for (IOperation operation : this.getAffectedOperations())
            {
                this.operationStateMap.get(operation).setIsInterrupted(false);
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

                for (IOperation operation : this.getAffectedOperations())
                {
                    this.operationStateMap.get(operation).setIsInterrupted(false);
                }

                return;
            }

            if (!this.hasBegun)
            {
                for (IOperation operation : this.getAffectedOperations())
                {
                    this.operationStateMap.get(operation).setIsInterrupted(true);
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

        for (IOperation operation : this.getAffectedOperations())
        {
            this.operationStateMap.get(operation).setIsInterrupted(false);
        }
    }
}
