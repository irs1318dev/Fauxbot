package frc.lib.driver.states;

import java.util.EnumSet;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

/**
 * The state of the current macro operation.
 *
 */
public interface IMacroOperationState extends IOperationState
{
    public EnumSet<AnalogOperation> getMacroCancelAnalogOperations();

    public EnumSet<DigitalOperation> getMacroCancelDigitalOperations();

    public boolean getIsActive();

    public void run();

    public void cancel();
}
