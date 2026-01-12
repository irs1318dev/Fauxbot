package frc.robot.driver.controltasks;

import frc.lib.driver.IControlTask;
import frc.lib.robotprovider.IDriverStation;
import frc.lib.robotprovider.IRobotProvider;

public final class RemainingTimeDecisionTask extends ConditionalTask
{
    private final double threshold;

    /**
     * Decision task that runs one of two tasks based on the current match time.
     * 
     * @param threshold threshold time in seconds - for values above this time, the highTask will run, otherwise the lowTask will run.
     * @param lowTask   the task to run if the match time is below the threshold
     * @param highTask  the task to run if the match time is above the threshold
     */
    public RemainingTimeDecisionTask(
        double threshold,
        IControlTask lowTask,
        IControlTask highTask)
    {
        super(lowTask, highTask);

        this.threshold = threshold;
    }

    @Override
    protected boolean evaluateCondition()
    {
        IDriverStation ds = this.getInjector().getInstance(IRobotProvider.class).getDriverStation();
        return ds.getMatchTime() <= this.threshold;
    }
}
