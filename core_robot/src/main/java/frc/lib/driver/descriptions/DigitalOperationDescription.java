package frc.lib.driver.descriptions;

import java.util.EnumSet;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.UserInputDevicePOV;
import frc.lib.driver.buttons.ButtonType;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.OperationContext;
import frc.robot.driver.Shift;

/**
 * Describes an operation that will give a boolean (true or false) value.
 */
public class DigitalOperationDescription extends OperationDescription<DigitalOperation>
{
    private final UserInputDeviceButton userInputDeviceButton;
    private final UserInputDevicePOV userInputDevicePovValue;
    private final AnalogAxis userInputDeviceAxis;
    private final ButtonType buttonType;

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
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null,
            null,
            null,
            ButtonType.Simple);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param operation the digital operation being described
     * @param userInputDevice which device will perform the operation (driver or codriver joystick) 
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
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param operation the digital operation being described
     * @param userInputDevice which device will perform the operation (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            userInputDeviceButton,
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null,
            null,
            relevantContexts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction
     * @param operation the digital operation being described
     * @param userInputDevice which device will perform the operation (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            userInputDeviceButton,
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            relevantShifts,
            requiredShifts,
            relevantContexts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
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
            null,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<OperationContext> relevantContexts,
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
            null,
            null,
            relevantContexts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on the POV
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
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
            relevantShifts,
            requiredShifts,
            relevantContexts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
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
            UserInputDevicePOV.NONE,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            null,
            null,
            null,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            UserInputDevicePOV.NONE,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            null,
            null,
            relevantContexts,
            buttonType);
    }

    /**
     * Initializes a new DigitalOperationDescription based on a user interaction on an axis
     * @param operation the digital operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType)
    {
        this(
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            UserInputDevicePOV.NONE,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            relevantShifts,
            requiredShifts,
            relevantContexts,
            buttonType);
    }

    private DigitalOperationDescription(
        DigitalOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        UserInputDevicePOV povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType)
    {
        super(operation, OperationType.Digital, userInputDevice, axisRangeMinValue, axisRangeMaxValue, relevantShifts, requiredShifts, relevantContexts);

        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.buttonType = buttonType;
    }

    public UserInputDeviceButton getUserInputDeviceButton()
    {
        return this.userInputDeviceButton;
    }

    public UserInputDevicePOV getUserInputDevicePovValue()
    {
        return this.userInputDevicePovValue;
    }

    public AnalogAxis getUserInputDeviceAxis()
    {
        return this.userInputDeviceAxis;
    }

    public ButtonType getButtonType()
    {
        return this.buttonType;
    }
}
