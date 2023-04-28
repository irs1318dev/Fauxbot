package frc.lib.driver.descriptions;

import frc.lib.driver.IOperation;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.Shift;

/**
 * Describes an operation.
 *
 */
public abstract class OperationDescription
{
    private final IOperation operation;
    private final OperationType type;
    private final UserInputDevice userInputDevice;
    private final Shift relevantShifts;
    private final Shift requiredShifts;
    private final double userInputDeviceRangeMin;
    private final double userInputDeviceRangeMax;

    protected OperationDescription(IOperation operation, OperationType type, UserInputDevice userInputDevice, double userInputDeviceRangeMin, double userInputDeviceRangeMax, Shift relevantShifts, Shift requiredShifts)
    {
        this.operation = operation;
        this.type = type;
        this.userInputDevice = userInputDevice;
        this.userInputDeviceRangeMin = userInputDeviceRangeMin;
        this.userInputDeviceRangeMax = userInputDeviceRangeMax;
        this.relevantShifts = relevantShifts;
        this.requiredShifts = requiredShifts;

        ExceptionHelpers.Assert((relevantShifts == null) == (requiredShifts == null), "Either both or neither of relevant and required shifts should be null");
        ExceptionHelpers.Assert(relevantShifts == null || requiredShifts == null || relevantShifts.hasFlag(requiredShifts), "relevant shifts must contain required shifts");
    }

    public IOperation getOperation()
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

    public Shift getRelevantShifts()
    {
        return this.relevantShifts;
    }

    public Shift getRequiredShifts()
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
