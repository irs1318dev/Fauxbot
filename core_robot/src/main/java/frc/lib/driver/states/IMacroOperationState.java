package frc.lib.driver.states;

import frc.lib.driver.IOperation;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * The state of the current macro operation.
 *
 */
public interface IMacroOperationState extends IOperationState
{
    public AnalogOperation[] getMacroCancelAnalogOperations();

    public DigitalOperation[] getMacroCancelDigitalOperations();

    public IOperation[] getAffectedOperations();

    public boolean getIsActive();

    public void run();

    public void cancel();
}
