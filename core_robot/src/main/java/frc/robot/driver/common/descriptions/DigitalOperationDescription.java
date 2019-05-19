package frc.robot.driver.common.descriptions;

import frc.robot.common.robotprovider.AnalogAxis;
import frc.robot.driver.Shift;
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
    private final double userInputDeviceAxisRangeMin;
    private final double userInputDeviceAxisRangeMax;
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
        this(
            userInputDevice,
            userInputDeviceButton,
            Shift.Any,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param userInputDevice which device will perform the operation (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param requiredShift the shift button that must be applied to perform macro
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        Shift requiredShift,
        ButtonType buttonType)
    {
        this(
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.None,
            0.0,
            0.0,
            DigitalSensor.None,
            requiredShift,
            buttonType);
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
        this(
            userInputDevice,
            povValue,
            Shift.Any,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param requiredShift the shift button that must be applied to perform macro
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        int povValue,
        Shift requiredShift,
        ButtonType buttonType)
    {
        this(
            userInputDevice,
            UserInputDeviceButton.JOYSTICK_POV,
            povValue,
            AnalogAxis.None,
            0.0,
            0.0,
            DigitalSensor.None,
            requiredShift,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType)
    {
        this(
            userInputDevice,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            Shift.Any,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param requiredShift the shift button that must be applied to perform macro
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        Shift requiredShift,
        ButtonType buttonType)
    {
        this(
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            DigitalSensor.None,
            requiredShift,
            buttonType);
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
        this(
            UserInputDevice.Sensor,
            UserInputDeviceButton.NONE,
            -1,
            AnalogAxis.None,
            0.0,
            0.0,
            sensor,
            Shift.Any,
            buttonType);
    }

    private DigitalOperationDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        DigitalSensor sensor,
        Shift requiredShift,
        ButtonType buttonType)
    {
        super(OperationType.Digital, userInputDevice, requiredShift);

        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.userInputDeviceAxisRangeMin = axisRangeMinValue;
        this.userInputDeviceAxisRangeMax = axisRangeMaxValue;
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

    public double getUserInputDeviceRangeMin()
    {
        return this.userInputDeviceAxisRangeMin;
    }

    public double getUserInputDeviceRangeMax()
    {
        return this.userInputDeviceAxisRangeMax;
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
