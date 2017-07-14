package org.usfirst.frc.team1318.robot.driver.descriptions;

/**
 * Describes an operation.
 *
 */
public abstract class OperationDescription
{
    private final OperationType type;
    private final UserInputDevice userInputDevice;

    protected OperationDescription(OperationType type, UserInputDevice userInputDevice)
    {
        this.type = type;
        this.userInputDevice = userInputDevice;
    }

    public OperationType getType()
    {
        return this.type;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }
}
