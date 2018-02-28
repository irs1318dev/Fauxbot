package org.usfirst.frc.team1318.robot.driver.common.descriptions;

import org.usfirst.frc.team1318.robot.driver.Shift;

/**
 * Describes an operation.
 *
 */
public abstract class OperationDescription
{
    private final OperationType type;
    private final UserInputDevice userInputDevice;
    private final Shift requiredShift;

    protected OperationDescription(OperationType type, UserInputDevice userInputDevice, Shift requiredShift)
    {
        this.type = type;
        this.userInputDevice = userInputDevice;
        this.requiredShift = requiredShift;
    }

    public OperationType getType()
    {
        return this.type;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }

    public Shift getRequiredShift()
    {
        return this.requiredShift;
    }
}
