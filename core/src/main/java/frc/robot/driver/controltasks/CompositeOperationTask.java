package frc.robot.driver.controltasks;

import frc.robot.TuningConstants;
import frc.robot.driver.Operation;
import frc.robot.driver.common.IControlTask;

/**
 * Task that applies a single operation from a group of related operations for a short period of time.
 * 
 */
public abstract class CompositeOperationTask extends TimedTask implements IControlTask
{
    private final Operation toPerform;
    private final Operation[] possibleOperations;

    /**
     * Initializes a new CompositeOperationTask
     * @param duration to wait in seconds
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    protected CompositeOperationTask(double duration, Operation toPerform, Operation[] possibleOperations)
    {
        super(duration);
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // if we are cool with throwing exceptions (testing), check if toPerform is in
            // the possibleOperations set and throw an exception if it is not
            boolean containsToPerform = false;
            for (Operation op : possibleOperations)
            {
                if (op == toPerform)
                {
                    containsToPerform = true;
                    break;
                }
            }

            if (!containsToPerform)
            {
                throw new RuntimeException("" + toPerform.toString() + " not contained in the set");
            }
        }

        this.toPerform = toPerform;
        this.possibleOperations = possibleOperations;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();
        for (Operation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, op == toPerform);
        }
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        for (Operation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, op == toPerform);
        }
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();
        for (Operation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, false);
        }
    }
}
