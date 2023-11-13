package frc.lib.driver.descriptions;

import java.util.EnumSet;

import frc.lib.driver.IOperation;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.Shift;

/**
 * Describes an operation.
 *
 */
public abstract class OperationDescription<TOperation extends IOperation>
{
    private final TOperation operation;
    private final OperationType type;
    private final UserInputDevice userInputDevice;
    private final EnumSet<Shift> relevantShifts;
    private final EnumSet<Shift> requiredShifts;
    private final double userInputDeviceRangeMin;
    private final double userInputDeviceRangeMax;

    protected OperationDescription(TOperation operation, OperationType type, UserInputDevice userInputDevice, double userInputDeviceRangeMin, double userInputDeviceRangeMax, EnumSet<Shift> relevantShifts, EnumSet<Shift> requiredShifts)
    {
        this.operation = operation;
        this.type = type;
        this.userInputDevice = userInputDevice;
        this.userInputDeviceRangeMin = userInputDeviceRangeMin;
        this.userInputDeviceRangeMax = userInputDeviceRangeMax;
        this.relevantShifts = relevantShifts;
        this.requiredShifts = requiredShifts;

        ExceptionHelpers.Assert((relevantShifts == null) == (requiredShifts == null), "Either both or neither of relevant and required shifts should be null");
        ExceptionHelpers.Assert(relevantShifts == null || requiredShifts == null || relevantShifts.containsAll(requiredShifts), "relevant shifts must contain required shifts");
    }

    public TOperation getOperation()
    {
        return this.operation;
    }

    public OperationType getType()
    {
        return this.type;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }

    public EnumSet<Shift> getRelevantShifts()
    {
        return this.relevantShifts;
    }

    public EnumSet<Shift> getRequiredShifts()
    {
        return this.requiredShifts;
    }

    public double getUserInputDeviceRangeMin()
    {
        return this.userInputDeviceRangeMin;
    }

    public double getUserInputDeviceRangeMax()
    {
        return this.userInputDeviceRangeMax;
    }
}
