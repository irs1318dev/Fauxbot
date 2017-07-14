package org.usfirst.frc.team1318.robot.driver.descriptions;

import org.usfirst.frc.team1318.robot.driver.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.driver.buttons.ButtonType;

/**
 * Describes an operation that will give a boolean (true or false) value.
 * 
 */
public class DigitalOperationDescription extends OperationDescription
{
    private final UserInputDeviceButton userInputDeviceButton;
    private final int userInputDevicePovValue;
    private final ButtonType buttonType;
    private final DigitalSensor sensor;

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param userInputDevice which device will perform the operation (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType)
    {
        this(userInputDevice, userInputDeviceButton, 0, DigitalSensor.None, buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        int povValue,
        ButtonType buttonType)
    {
        this(userInputDevice, UserInputDeviceButton.JOYSTICK_POV, povValue, DigitalSensor.None, buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a sensor
     * @param sensor the sensor that triggers the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalSensor sensor,
        ButtonType buttonType)
    {
        this(UserInputDevice.Sensor, UserInputDeviceButton.NONE, 0, sensor, buttonType);
    }

    private DigitalOperationDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        DigitalSensor sensor,
        ButtonType buttonType)
    {
        super(OperationType.Digital, userInputDevice);

        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.sensor = sensor;
        this.buttonType = buttonType;
    }

    public UserInputDeviceButton getUserInputDeviceButton()
    {
        return this.userInputDeviceButton;
    }

    public int getUserInputDevicePovValue()
    {
        return this.userInputDevicePovValue;
    }

    public DigitalSensor getSensor()
    {
        return this.sensor;
    }

    public ButtonType getButtonType()
    {
        return this.buttonType;
    }
}
