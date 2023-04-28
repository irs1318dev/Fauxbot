package frc.robot.driver.controltasks;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.robotprovider.ITimer;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

/**
 * Task that applies a single operation from a group of related operations for a single update cycle.
 * 
 */
public abstract class CompositeOperationTask extends UpdateCycleTask
{
    private final DigitalOperation[] possibleOperations;
    private final boolean timeoutMode;
    private final double timeout;
    private final boolean runIndefinitely;

    private DigitalOperation toPerform;
    private ITimer timer;
    private double startTime;

    /**
     * Initializes a new CompositeOperationTask
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    protected CompositeOperationTask(DigitalOperation toPerform, DigitalOperation[] possibleOperations)
    {
        this(toPerform, possibleOperations, false, 0.0, false);
    }

    /**
     * Initializes a new CompositeOperationTask
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     * @param runIndefinitely whether to keep running indefinitely instead of for a single update cycle
     */
    protected CompositeOperationTask(DigitalOperation toPerform, DigitalOperation[] possibleOperations, boolean runIndefinitely)
    {
        this(toPerform, possibleOperations, false, 0.0, runIndefinitely);
    }

    /**
     * Initializes a new CompositeOperationTask
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     * @param timeout how long to keep running the macro
     */
    protected CompositeOperationTask(DigitalOperation toPerform, DigitalOperation[] possibleOperations, double timeout)
    {
        this(toPerform, possibleOperations, true, timeout, false);
    }

    /**
     * Initializes a new CompositeOperationTask
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     * @param timeoutMode whether we are in timeout mode
     * @param timeout the timeout, if we are in timeout mode
     */
    private CompositeOperationTask(DigitalOperation toPerform, DigitalOperation[] possibleOperations, boolean timeoutMode, double timeout, boolean runIndefinitely)
    {
        super(1);
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // if we are cool with throwing exceptions (testing), check if toPerform is in
            // the possibleOperations set and throw an exception if it is not
            boolean containsToPerform = false;
            for (DigitalOperation op : possibleOperations)
            {
                if (op == toPerform)
                {
                    containsToPerform = true;
                    break;
                }
            }

            ExceptionHelpers.Assert(containsToPerform, toPerform.toString() + " not contained in the set");
        }

        this.toPerform = toPerform;
        this.possibleOperations = possibleOperations;
        this.timeoutMode = timeoutMode;
        this.timeout = timeout;
        this.runIndefinitely = runIndefinitely;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();
        if (this.timeoutMode)
        {
            this.timer = this.getInjector().getInstance(ITimer.class);
            this.startTime = this.timer.get();
        }

        for (DigitalOperation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, op == this.toPerform);
        }
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        super.update();
        for (DigitalOperation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, op == this.toPerform);
        }
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        super.end();
        for (DigitalOperation op : this.possibleOperations)
        {
            this.setDigitalOperationState(op, false);
        }
    }

    @Override
    public boolean hasCompleted()
    {
        if (this.runIndefinitely)
        {
            return false;
        }

        if (this.timeoutMode)
        {
            return this.timer.get() >= this.startTime + this.timeout;
        }

        return super.hasCompleted();
    }

    /**
     * Update the toPerform value.  Note that we would want this to be called only really within an override for begin()
     * @param toPerform the operation to perform (by setting to true)
     */
    protected void setToPerform(DigitalOperation toPerform)
    {
        this.toPerform = toPerform;
    }
}
