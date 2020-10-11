package frc.robot.driver.common.descriptions;

import frc.robot.TuningConstants;
import frc.robot.driver.IOperation;
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

        if (TuningConstants.THROW_EXCEPTIONS)
        {
            if ((relevantShifts == null) != (requiredShifts == null))
            {
                throw new RuntimeException("Either both or neither of relevant and required shifts should be null");
            }

            if (relevantShifts != null && requiredShifts != null)
            {
                if (!relevantShifts.hasFlag(requiredShifts))
                {
                    throw new RuntimeException("relevant shifts must contain required shifts");
                }
            }
        }
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
