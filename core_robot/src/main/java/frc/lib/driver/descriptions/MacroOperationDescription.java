package frc.lib.driver.descriptions;

import java.util.EnumSet;
import java.util.function.Supplier;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperation;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.buttons.ButtonType;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.MacroOperation;
import frc.robot.driver.Shift;

public class MacroOperationDescription extends OperationDescription<MacroOperation>
{
    private final boolean clearInterrupt;
    private final UserInputDeviceButton userInputDeviceButton;
    private final int userInputDevicePovValue;
    private final AnalogAxis userInputDeviceAxis;
    private final ButtonType buttonType;
    private final Supplier<IControlTask> taskSupplier;
    private final IOperation[] affectedOperations;
    private final AnalogOperation[] macroCancelAnalogOperations;
    private final DigitalOperation[] macroCancelDigitalOperations;

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null,
            null,
            buttonType,
            taskSupplier,
            affectedOperations,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            relevantShifts,
            requiredShifts,
            buttonType,
            taskSupplier,
            affectedOperations,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null,
            null,
            buttonType,
            taskSupplier,
            affectedOperations,
            macroCancelOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            userInputDeviceButton,
            -1,
            AnalogAxis.NONE,
            0.0,
            0.0,
            relevantShifts,
            requiredShifts,
            buttonType,
            taskSupplier,
            affectedOperations,
            macroCancelOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        int povValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
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
            buttonType,
            taskSupplier,
            affectedOperations,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        int povValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
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
            buttonType,
            taskSupplier,
            affectedOperations,
            null);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param operation the macro operation being described
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the macro
     * @param requiredShifts the shift button(s) that must be applied to perform macro
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        int povValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
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
            buttonType,
            taskSupplier,
            affectedOperations,
            macroCancelOperations);
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
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            null,
            null,
            buttonType,
            taskSupplier,
            affectedOperations,
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
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            null,
            null,
            buttonType,
            taskSupplier,
            affectedOperations,
            macroCancelOperations);
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
     * @param buttonType the behavior type to use for the operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            relevantShifts,
            requiredShifts,
            buttonType,
            taskSupplier,
            affectedOperations,
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
     * @param buttonType the behavior type to use for the operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    public MacroOperationDescription(
        MacroOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
    {
        this(
            true,
            operation,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            -1,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            relevantShifts,
            requiredShifts,
            buttonType,
            taskSupplier,
            affectedOperations,
            macroCancelOperations);
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
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     * @param macroCancelOperations the list of operations that can be used to cancel this macro
     */
    private MacroOperationDescription(
        boolean clearInterrupt,
        MacroOperation operation,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<Shift> relevantShifts,
        EnumSet<Shift> requiredShifts,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        IOperation[] affectedOperations,
        IOperation[] macroCancelOperations)
    {
        super(operation, OperationType.None, userInputDevice, axisRangeMinValue, axisRangeMaxValue, relevantShifts, requiredShifts);

        this.clearInterrupt = clearInterrupt;
        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.buttonType = buttonType;
        this.taskSupplier = taskSupplier;
        this.affectedOperations = affectedOperations;

        IOperation[] cancelOperations = macroCancelOperations;
        if (macroCancelOperations == null)
        {
            cancelOperations = affectedOperations;
        }

        ExceptionHelpers.Assert(cancelOperations != null, "expect there to be non-null affected or cancel operations");
        if (cancelOperations != null)
        {
            EnumSet<AnalogOperation> analogCancelOperations = EnumSet.noneOf(AnalogOperation.class);
            EnumSet<DigitalOperation> digitalCancelOperations = EnumSet.noneOf(DigitalOperation.class);
            for (IOperation cancelOperation : cancelOperations)
            {
                if (cancelOperation instanceof AnalogOperation)
                {
                    analogCancelOperations.add((AnalogOperation)cancelOperation);
                }
                else if (cancelOperation instanceof DigitalOperation)
                {
                    digitalCancelOperations.add((DigitalOperation)cancelOperation);
                }
                else
                {
                    ExceptionHelpers.Assert(false, "expect all affected/cancel operations to be either Analog or Digital operations, value: " + cancelOperation.toString());
                }
            }

            this.macroCancelAnalogOperations = analogCancelOperations.toArray(new AnalogOperation[0]);
            this.macroCancelDigitalOperations = digitalCancelOperations.toArray(new DigitalOperation[0]);
        }
        else
        {
            this.macroCancelAnalogOperations = new AnalogOperation[0];
            this.macroCancelDigitalOperations = new DigitalOperation[0];
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

    public int getUserInputDevicePovValue()
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

    public AnalogOperation[] getMacroCancelAnalogOperations()
    {
        return this.macroCancelAnalogOperations;
    }

    public DigitalOperation[] getMacroCancelDigitalOperations()
    {
        return this.macroCancelDigitalOperations;
    }

    public IOperation[] getAffectedOperations()
    {
        return this.affectedOperations;
    }
}
