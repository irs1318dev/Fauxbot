package org.usfirst.frc.team1318.robot.Driver.Autonomous;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.States.OperationState;

/**
 * Driver for autonomous mode.  Autonomous driver acts as the operator of the robot,
 * telling it what actions to perform as determined by the current task and tasks that have
 * come before it that intentionally don't reset their state.
 * 
 */
public class AutonomousDriver extends Driver
{
    private final AutonomousRoutineSelector routineSelector;
    private final IControlTask autonomousTask;

    private boolean hasBegun;
    private boolean hasEnded;

    /**
     * Initializes a new AutonomousDriver
     * @param components to utilize for making any decisions
     */
    public AutonomousDriver(ComponentManager components)
    {
        super();

        this.routineSelector = new AutonomousRoutineSelector();
        this.autonomousTask = this.routineSelector.selectRoutine();

        this.hasBegun = false;
        this.hasEnded = false;

        this.autonomousTask.initialize(this.operationStateMap, components);

        for (OperationState state : this.operationStateMap.values())
        {
            state.setIsInterrupted(true);
        }
    }

    /**
     * Tell the driver that some time has passed
     */
    @Override
    public void update()
    {
        if (!this.hasEnded)
        {
            if (!this.hasBegun)
            {
                // if we haven't begun, begin
                this.autonomousTask.begin();
                this.hasBegun = true;
            }

            if (this.autonomousTask.hasCompleted())
            {
                // if we shouldn't continue, end the task
                this.autonomousTask.end();
                this.hasEnded = true;
            }
            else if (this.autonomousTask.shouldCancel())
            {
                this.autonomousTask.stop();
                this.hasEnded = true;
            }
            else
            {
                // run the current task and apply the result to the state
                this.autonomousTask.update();
            }
        }
    }

    /**
     * Tell the driver that operation is stopping
     */
    @Override
    public void stop()
    {
        if (this.autonomousTask != null)
        {
            this.autonomousTask.stop();
            this.hasEnded = true;
        }
    }
}
