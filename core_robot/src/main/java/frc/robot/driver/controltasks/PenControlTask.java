package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.driver.DigitalOperation;

public class PenControlTask extends CompositeOperationTask {
    PenControlTask(DigitalOperation toPerform, EnumSet<DigitalOperation> possibleOperations){
        super(toPerform, possibleOperations);
    }

    public static PenControlTask Create(boolean Down)
    {
        if (Down)
        {
            return new PenControlTask(DigitalOperation.PrinterPenDown, EnumSet.of(DigitalOperation.PrinterPenDown, DigitalOperation.PrinterPenUp));
        }
        else 
        {
            return new PenControlTask(DigitalOperation.PrinterPenUp, EnumSet.of(DigitalOperation.PrinterPenDown, DigitalOperation.PrinterPenUp));
        }
    }
}
