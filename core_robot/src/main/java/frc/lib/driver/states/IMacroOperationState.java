package frc.lib.driver.states;

import frc.lib.driver.IOperation;

/**
 * The state of the current macro operation.
 *
 */
public interface IMacroOperationState extends IOperationState
{
    public IOperation[] getMacroCancelOperations();

    public IOperation[] getAffectedOperations();

    public boolean getIsActive();

    public void run();

    public void cancel();
}
