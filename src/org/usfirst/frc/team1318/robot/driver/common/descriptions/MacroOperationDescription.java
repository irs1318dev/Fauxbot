package org.usfirst.frc.team1318.robot.driver.common.descriptions;

import java.util.function.Supplier;

import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.IControlTask;
import org.usfirst.frc.team1318.robot.driver.common.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.ButtonType;

public class MacroOperationDescription extends OperationDescription
{
    private final boolean clearInterrupt;
    private final UserInputDeviceButton userInputDeviceButton;
    private final int userInputDevicePovValue;
    private final DigitalSensor sensor;
    private final Operation[] affectedOperations;
    private final Supplier<IControlTask> taskSupplier;
    private final ButtonType buttonType;

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(true, userInputDevice, userInputDeviceButton, 0, DigitalSensor.None, buttonType, taskSupplier, affectedOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction
     * @param clearInterrupt whether to clear the interruption of the operations when the macro completes
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param userInputDeviceButton the button on the device that performs the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        boolean clearInterrupt,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(clearInterrupt, userInputDevice, userInputDeviceButton, 0, DigitalSensor.None, buttonType, taskSupplier, affectedOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        UserInputDevice userInputDevice,
        int povValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(true, userInputDevice, UserInputDeviceButton.JOYSTICK_POV, povValue, DigitalSensor.None, buttonType, taskSupplier,
            affectedOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a user interaction on the POV
     * @param clearInterrupt whether to clear the interruption of the operations when the macro completes
     * @param userInputDevice which device will perform the macro operation (driver or codriver joystick)
     * @param povValue the value of the POV (hat) used to perform the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        boolean clearInterrupt,
        UserInputDevice userInputDevice,
        int povValue,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(clearInterrupt, userInputDevice, UserInputDeviceButton.JOYSTICK_POV, povValue, DigitalSensor.None, buttonType, taskSupplier,
            affectedOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a sensor
     * @param sensor the sensor that triggers the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        DigitalSensor sensor,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(true, UserInputDevice.Sensor, UserInputDeviceButton.NONE, 0, sensor, buttonType, taskSupplier, affectedOperations);
    }

    /**
     * Initializes a new MacroOperationDescription based on a sensor
     * @param clearInterrupt whether to clear the interruption of the operations when the macro completes
     * @param sensor the sensor that triggers the macro operation
     * @param buttonType the behavior type to use for the macro operation
     * @param taskSupplier the function that creates the tasks that should be performed by the macro
     * @param affectedOperations the list of operations that will be utilized by this macro
     */
    public MacroOperationDescription(
        boolean clearInterrupt,
        DigitalSensor sensor,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        this(clearInterrupt, UserInputDevice.Sensor, UserInputDeviceButton.NONE, 0, sensor, buttonType, taskSupplier, affectedOperations);
    }

    private MacroOperationDescription(
        boolean clearInterrupt,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        DigitalSensor sensor,
        ButtonType buttonType,
        Supplier<IControlTask> taskSupplier,
        Operation... affectedOperations)
    {
        super(OperationType.None, userInputDevice);

        this.clearInterrupt = clearInterrupt;
        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.sensor = sensor;
        this.affectedOperations = affectedOperations;
        this.taskSupplier = taskSupplier;
        this.buttonType = buttonType;
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

    public DigitalSensor getSensor()
    {
        return this.sensor;
    }

    public ButtonType getButtonType()
    {
        return this.buttonType;
    }

    public IControlTask constructTask()
    {
        return this.taskSupplier.get();
    }

    public Operation[] getAffectedOperations()
    {
        return this.affectedOperations;
    }
}
