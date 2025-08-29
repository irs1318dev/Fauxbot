package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.HardwareConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.PrinterMechanism;

/**
 * PenMoveTask
 */
public class PenMoveTask extends ControlTaskBase
{
    private static final double AllowableDelta = 1.0;

    private final double xPixel;
    private final double yPixel;
    private final double xCommand;
    private final double yCommand;

    private PrinterMechanism printerMechanism;

    /**
     * Initializes a new PenMoveTask
     * @param xPixel to move the pen to in pixels
     * @param yPixel to move the pen to in pixels
     */
    public PenMoveTask(double xPixel, double yPixel)
    {
        this.xPixel = xPixel;
        this.yPixel = yPixel;
        this.xCommand = 2.0 * (xPixel - HardwareConstants.PRINTER_MIN_X) / (HardwareConstants.PRINTER_MAX_X - HardwareConstants.PRINTER_MIN_X) - 1.0;
        this.yCommand = 2.0 * (yPixel - HardwareConstants.PRINTER_MIN_Y) / (HardwareConstants.PRINTER_MAX_Y - HardwareConstants.PRINTER_MIN_Y) - 1.0;
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        return EnumSet.of(
            AnalogOperation.PrinterMoveX,
            AnalogOperation.PrinterMoveY);
    }

    /**
     * Retrieve the set of digital operations that this task affects.
     * @return set of digital operations that this task affects.
     */
    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        return EnumSet.noneOf(DigitalOperation.class);
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        this.printerMechanism = this.getInjector().getInstance(PrinterMechanism.class);
    }

    /**
     * Run an iteration of the current task and apply any control changes 
     */
    @Override
    public void update()
    {
        this.setAnalogOperationState(AnalogOperation.PrinterMoveX, this.xCommand);
        this.setAnalogOperationState(AnalogOperation.PrinterMoveY, this.yCommand);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        return Math.abs(this.printerMechanism.getXLocation() - this.xPixel) <= PenMoveTask.AllowableDelta &&
                Math.abs(this.printerMechanism.getYLocation() - this.yPixel) <= PenMoveTask.AllowableDelta;
    }
}
