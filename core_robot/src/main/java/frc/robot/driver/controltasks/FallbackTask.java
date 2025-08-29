package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public final class FallbackTask extends DecisionSequentialTask
{
    private final IControlTask initialTask;
    private final IControlTask fallbackTask;

    /**
     * Executes an intial task, and if that task aborts (indicates that it has failed and should be canceled), executes the fallback
     * @param initialTask to execute by default
     * @param fallbackTask to execute only if the initial task indicates that it should be canceled
     */
    public FallbackTask(
        IControlTask initialTask,
        IControlTask fallbackTask)
    {
        this.initialTask = initialTask;
        this.fallbackTask = fallbackTask;

        this.AppendTask(initialTask);
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        EnumSet<AnalogOperation> allAffectedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        allAffectedAnalogOperations.addAll(this.initialTask.getAffectedAnalogOperations());
        allAffectedAnalogOperations.addAll(this.fallbackTask.getAffectedAnalogOperations());
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
        allAffectedDigitalOperations.addAll(this.initialTask.getAffectedDigitalOperations());
        allAffectedDigitalOperations.addAll(this.fallbackTask.getAffectedDigitalOperations());
        return allAffectedDigitalOperations;
    }

    @Override
    protected boolean onTaskCanceled(IControlTask task)
    {
        if (task == this.initialTask)
        {
            this.AppendTask(this.fallbackTask);
            return false;
        }

        return super.onTaskCanceled(task);
    }
}
