package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * Task that holds two sub-tasks and executes one of them conditionally based on the result of some check
 * 
 */
public abstract class ConditionalTask extends DecisionSequentialTask
{
    private IControlTask trueTask;
    private IControlTask falseTask;

    /**
     * Initializes a new ConditionalTask
     * @param trueTask task to run if the condition is true
     * @param falseTask task to run if the condition is false
     */
    protected ConditionalTask(IControlTask trueTask, IControlTask falseTask)
    {
        super();

        this.trueTask = trueTask;
        this.falseTask = falseTask;
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        EnumSet<AnalogOperation> allAffectedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        allAffectedAnalogOperations.addAll(this.trueTask.getAffectedAnalogOperations());
        allAffectedAnalogOperations.addAll(this.falseTask.getAffectedAnalogOperations());
        return allAffectedAnalogOperations;
    }

    /**
     * Retrieve the set of digital operations that this task affects.
     * @return set of digital operations that this task affects.
     */
    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        EnumSet<DigitalOperation> allAffectedDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
        allAffectedDigitalOperations.addAll(this.trueTask.getAffectedDigitalOperations());
        allAffectedDigitalOperations.addAll(this.falseTask.getAffectedDigitalOperations());
        return allAffectedDigitalOperations;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        super.begin();

        if (this.evaluateCondition())
        {
            this.AppendTask(this.trueTask);
        }
        else
        {
            this.AppendTask(this.falseTask);
        }
    }

    /**
     * Evaluate the condition that determines which task to run
     * @return true if the trueTask should be run, false if the falseTask should be run
     */
    protected abstract boolean evaluateCondition();
}
