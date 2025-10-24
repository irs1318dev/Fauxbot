package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.lib.robotprovider.IDriverStation;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public final class RemainingTimeDecisionTask extends DecisionSequentialTask
{
    private final double threshold;

    private final IControlTask lowTask;
    private final IControlTask highTask;

    /**
     * Decision task that runs one of two tasks based on the current match time.
     * @param threshold threshold time in seconds - for values above this time, the highTask will run, otherwise the lowTask will run.
     * @param lowTask the task to run if the match time is below the threshold
     * @param highTask the task to run if the match time is above the threshold
     */
    public RemainingTimeDecisionTask(
        double threshold,
        IControlTask lowTask,
        IControlTask highTask)
    {
        this.threshold = threshold;

        this.lowTask = lowTask;
        this.highTask = highTask;
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        EnumSet<AnalogOperation> allAffectedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        allAffectedAnalogOperations.addAll(this.lowTask.getAffectedAnalogOperations());
        allAffectedAnalogOperations.addAll(this.highTask.getAffectedAnalogOperations());
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
        allAffectedDigitalOperations.addAll(this.lowTask.getAffectedDigitalOperations());
        allAffectedDigitalOperations.addAll(this.highTask.getAffectedDigitalOperations());
        return allAffectedDigitalOperations;
    }

    @Override
    public void begin()
    {
        super.begin();

        IDriverStation ds = this.getInjector().getInstance(IRobotProvider.class).getDriverStation();
        if (ds.getMatchTime() > this.threshold)
        {
            this.AppendTask(this.highTask);
        }
        else
        {
            this.AppendTask(this.lowTask);
        }
    }
}
