package frc.robot.driver.common.states;

import frc.robot.driver.Operation;

/**
 * The state of the current macro operation.
 *
 */
public interface IMacroOperationState extends IOperationState
{
    public Operation[] getMacroCancelOperations();

    public Operation[] getAffectedOperations();

    public boolean getIsActive();

    public void run();

    public void cancel();
}
