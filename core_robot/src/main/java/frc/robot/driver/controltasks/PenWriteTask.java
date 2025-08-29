package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.driver.DigitalOperation;

/**
 * Task that puts the pend down or up in order to start writing
 */
public class PenWriteTask extends CompositeOperationTask
{
    private static final EnumSet<DigitalOperation> possibleOperations = EnumSet.of(DigitalOperation.PrinterPenDown, DigitalOperation.PrinterPenUp);

    /**
     * Initializes a new PenWriteTask
     * @param toPerform the operation to perform by setting to true for duration
     * @param possibleOperations to set of linked operations that should be set to false for duration
     */
    public PenWriteTask(boolean penDown)
    {
        super(penDown ? DigitalOperation.PrinterPenDown : DigitalOperation.PrinterPenUp, PenWriteTask.possibleOperations, 0.1);
    }
}
