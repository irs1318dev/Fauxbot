package frc.robot.driver.common.descriptions;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.Shift;
import frc.robot.driver.common.AnalogAxis;

/**
 * Describes an operation that will give a double value (typically between -1 and 1).
 * 
 */
public class AnalogOperationDescription extends OperationDescription
{
    private final AnalogAxis userInputDeviceAxis;
    private final boolean shouldInvert;
    private final double deadZone;

    private final AnalogSensor sensor;

    /**
     * Initializes a new AnalogOperationDescription that works without user interaction
     * @param operation the analog operation being described
     */
    public AnalogOperationDescription(AnalogOperation operation)
    {
        this(operation, UserInputDevice.None, AnalogAxis.NONE, false, 0.0);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZone the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        boolean shouldInvert,
        double deadZone)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, null, shouldInvert, deadZone);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZone the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        Shift relevantShifts,
        Shift requiredShifts,
        boolean shouldInvert,
        double deadZone)
    {
        super(operation, OperationType.Analog, userInputDevice, relevantShifts, requiredShifts);

        this.userInputDeviceAxis = userInputDeviceAxis;
        this.shouldInvert = shouldInvert;
        this.deadZone = deadZone;

        this.sensor = AnalogSensor.None;
    }

    /**
     * Initializes a new AnalogOperationDescription based on a sensor
     * @param operation the analog operation being described
     * @param sensor that will provide the value to use
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        AnalogSensor sensor)
    {
        super(operation, OperationType.Analog, UserInputDevice.Sensor, null, null);

        this.userInputDeviceAxis = AnalogAxis.NONE;
        this.shouldInvert = false;
        this.deadZone = 0.0;

        this.sensor = sensor;
    }

    public AnalogAxis getUserInputDeviceAxis()
    {
        return this.userInputDeviceAxis;
    }

    public AnalogSensor getSensor()
    {
        return this.sensor;
    }

    public boolean getShouldInvert()
    {
        return this.shouldInvert;
    }

    public double getDeadZone()
    {
        return this.deadZone;
    }

    public double getDefaultValue()
    {
        return 0.0;
    }
}
