package frc.robot.driver.common.descriptions;

import frc.robot.driver.DigitalOperation;
import frc.robot.driver.Shift;
import frc.robot.driver.common.AnalogAxis;
import frc.robot.driver.common.UserInputDeviceButton;
import frc.robot.driver.common.buttons.ButtonType;

/**
 * Describes an operation that will give a boolean (true or false) value.
 * 
 */
public class DigitalOperationDescription extends OperationDescription
{
    private final UserInputDeviceButton userInputDeviceButton;
    private final int userInputDevicePovValue;
    private final AnalogAxis userInputDeviceAxis;
    private final ButtonType buttonType;
    private final DigitalSensor sensor;

    /**
     * Initializes a new DigitalOperationDescription without any user interaction
     * @param operation the digital operation being described
     */
    public DigitalOperationDescription(
        DigitalOperation operation)
    {
        this(
            operation,
            UserInputDevice.None,
            UserInputDeviceButton.NONE,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            DigitalSensor.None,
            null,
            null,
            ButtonType.Simple);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param operation the digital operation being described
     * @param userInputDevice which device will perform the operation (driver or operator joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            DigitalSensor.None,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param operation the digital operation being described
     * @param userInputDevice which device will perform the operation (driver or operator joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        Shift relevantShifts,
        Shift requiredShifts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            DigitalSensor.None,
            relevantShifts,
            requiredShifts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        int povValue,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.POV,
            povValue,
            AnalogAxis.NONE,
            0.0,
            0.0,
            DigitalSensor.None,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        int povValue,
        Shift relevantShifts,
        Shift requiredShifts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.POV,
            povValue,
            AnalogAxis.NONE,
            0.0,
            0.0,
            DigitalSensor.None,
            relevantShifts,
            requiredShifts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            DigitalSensor.None,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        Shift relevantShifts,
        Shift requiredShifts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            DigitalSensor.None,
            relevantShifts,
            requiredShifts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a sensor
     * @param operation the digital operation being described
     * @param sensor the sensor that triggers the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        DigitalSensor sensor,
        ButtonType buttonType)
    {
        this(
            operation,
            UserInputDevice.Sensor,
            UserInputDeviceButton.NONE,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            sensor,
            null,
            null,
            buttonType);
    }

    private DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        DigitalSensor sensor,
        Shift relevantShifts,
        Shift requiredShifts,
        ButtonType buttonType)
    {
        super(operation, OperationType.Digital, userInputDevice, axisRangeMinValue, axisRangeMaxValue, relevantShifts, requiredShifts);

        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
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

    public AnalogAxis getUserInputDeviceAxis()
    {
        return this.userInputDeviceAxis;
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
