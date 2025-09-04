package frc.lib.driver.descriptions;

import java.util.EnumSet;
import java.util.function.Supplier;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IControlTask;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.UserInputDevicePOV;
import frc.lib.driver.buttons.ButtonType;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.MacroOperation;
import frc.robot.driver.OperationContext;
import frc.robot.driver.Shift;

public class MacroOperationDescription extends OperationDescription<MacroOperation>
{
    private final boolean clearInterrupt;
    private final UserInputDeviceButton userInputDeviceButton;
    private final UserInputDevicePOV userInputDevicePovValue;
    private final AnalogAxis userInputDeviceAxis;
    private final ButtonType buttonType;
    private final Supplier<IControlTask> taskSupplier;

    private final EnumSet<AnalogOperation> affectedAnalogOperations;
    private final EnumSet<DigitalOperation> affectedDigitalOperations;

    private final EnumSet<AnalogOperation> macroCancelAnalogOperations;
    private final EnumSet<DigitalOperation> macroCancelDigitalOperations;

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on an axis
     * @param operation the macro operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param buttonType the behavior type to use for the operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on an axis
     * @param operation the macro operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param buttonType the behavior type to use for the operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on an axis
     * @param operation the macro operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            null,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on an axis
     * @param operation the macro operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the operation
     * @param axisRangeMinValue the min value of the range that triggers the operation
     * @param axisRangeMaxValue the max value of the range that triggers the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        this(
            true,
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
            buttonType,
            taskSupplier,
            macroCancelAnalogOperations,
            macroCancelDigitalOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param clearInterrupt whether to clear the interruption of the operations when the macro completes
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param relevantContexts the contexts that should be considered when checking if we should perform the operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param macroCancelAnalogOperations the list of analog operations that indicate that this macro should be canceled
     * @param macroCancelDigitalOperations the list of digital operations that indicate that this macro should be canceled
     */
    private MacroOperationDescription(
        boolean clearInterrupt,
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        UserInputDevicePOV povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        EnumSet<OperationContext> relevantContexts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        EnumSet<AnalogOperation> macroCancelAnalogOperations,
        EnumSet<DigitalOperation> macroCancelDigitalOperations)
    {
        super(operation, OperationType.None, userInputDevice, axisRangeMinValue, axisRangeMaxValue, relevantShifts, requiredShifts, relevantContexts);

        this.clearInterrupt = clearInterrupt;
        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.buttonType = buttonType;
        this.taskSupplier = taskSupplier;

        IControlTask exampleTask = taskSupplier.get();
        ExceptionHelpers.Assert(exampleTask != null, "expect task supplier to return a non-null task");

        this.affectedAnalogOperations = exampleTask.getAffectedAnalogOperations();
        this.affectedDigitalOperations = exampleTask.getAffectedDigitalOperations();

        this.macroCancelAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        this.macroCancelAnalogOperations.addAll(this.affectedAnalogOperations);
        if (macroCancelAnalogOperations != null)
        {
            this.macroCancelAnalogOperations.addAll(macroCancelAnalogOperations);
        }

        this.macroCancelDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
        this.macroCancelDigitalOperations.addAll(this.affectedDigitalOperations);
        if (macroCancelDigitalOperations != null)
        {
            this.macroCancelDigitalOperations.addAll(macroCancelDigitalOperations);
        }
    }

    public boolean shouldClearInterrupt()
    {
        return this.clearInterrupt;
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

    public IControlTask constructTask()
    {
        return this.taskSupplier.get();
    }

    public EnumSet<AnalogOperation> getMacroCancelAnalogOperations()
    {
        return this.macroCancelAnalogOperations;
    }

    public EnumSet<DigitalOperation> getMacroCancelDigitalOperations()
    {
        return this.macroCancelDigitalOperations;
    }

    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        return this.affectedAnalogOperations;
    }

    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        return this.affectedDigitalOperations;
    }
}
