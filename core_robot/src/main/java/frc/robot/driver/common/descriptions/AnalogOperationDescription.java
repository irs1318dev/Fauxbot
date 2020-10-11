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
    private final AnalogAxis userInputDeviceSecondaryAxis;
    private final boolean shouldInvert;
    private final boolean shouldInvertSecondary;
    private final double deadZoneMin;
    private final double deadZoneMax;
    private final double multiplier;
    private final double defaultValue;
    private final ResultCalculator resultCalculator;

    private final AnalogSensor sensor;

    /**
     * Initializes a new AnalogOperationDescription that works without user interaction
     * @param operation the analog operation being described
     */
    public AnalogOperationDescription(AnalogOperation operation)
    {
        this(operation, UserInputDevice.None, AnalogAxis.NONE, null, null, null, false, false, 0.0, 0.0, 1.0, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription that works without user interaction
     * @param operation the analog operation being described
     * @param defaultValue the default value to use when not interrupted
     */
    public AnalogOperationDescription(AnalogOperation operation, double defaultValue)
    {
        this(operation, UserInputDevice.None, AnalogAxis.NONE, null, null, null, false, false, 0.0, 0.0, 1.0, defaultValue, null);
    }


    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        boolean shouldInvert,
        double deadZone)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, null, null, shouldInvert, false, -deadZone, deadZone, 1.0, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        boolean shouldInvert,
        double deadZoneMin,
        double deadZoneMax)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, null, null, shouldInvert, false, deadZoneMin, deadZoneMax, 1.0, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param multiplier the multiplier to use to extend the range from [-1, 1] so that it instead goes to [-multiplier, multiplier]
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        boolean shouldInvert,
        double deadZoneMin,
        double deadZoneMax,
        double multiplier)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, null, null, shouldInvert, false, deadZoneMin, deadZoneMax, multiplier, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        Shift relevantShifts,
        Shift requiredShifts,
        boolean shouldInvert,
        double deadZoneMin,
        double deadZoneMax)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, relevantShifts, requiredShifts, shouldInvert, false, deadZoneMin, deadZoneMax, 1.0, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param multiplier the multiplier to use to extend the range from [-1, 1] so that it instead goes to [-multiplier, multiplier]
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        Shift relevantShifts,
        Shift requiredShifts,
        boolean shouldInvert,
        double deadZoneMin,
        double deadZoneMax,
        double multiplier)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, relevantShifts, requiredShifts, shouldInvert, false, deadZoneMin, deadZoneMax, multiplier, 0.0, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param multiplier the multiplier to use to extend the range from [-1, 1] so that it instead goes to [-multiplier, multiplier]
     * @param defaultValue the default value to use if nothing is specified
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        Shift relevantShifts,
        Shift requiredShifts,
        boolean shouldInvert,
        double deadZoneMin,
        double deadZoneMax,
        double multiplier,
        double defaultValue)
    {
        this(operation, userInputDevice, userInputDeviceAxis, null, relevantShifts, requiredShifts, shouldInvert, false, deadZoneMin, deadZoneMax, multiplier, defaultValue, null);
    }

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param operation the analog operation being described
     * @param userInputDevice which device will indicate the operation (driver or operator joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param userInputDeviceSecondaryAxis the secondary axis on the device that will indicate the operation
     * @param relevantShifts the shifts that should be considered when checking if we should perform the operation
     * @param requiredShifts the shift button(s) that must be applied to perform operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param shouldInvertSecondary whether we should invert the secondary axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZoneMin the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param deadZoneMax the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     * @param multiplier the multiplier to use to extend the range from [-1, 1] so that it instead goes to [-multiplier, multiplier]
     * @param defaultValue the default value to use if nothing is specified
     * @param resultCalculator the calculator for the result based on the two values
     */
    public AnalogOperationDescription(
        AnalogOperation operation,
        UserInputDevice userInputDevice,
        AnalogAxis userInputDeviceAxis,
        AnalogAxis userInputDeviceSecondaryAxis,
        Shift relevantShifts,
        Shift requiredShifts,
        boolean shouldInvert,
        boolean shouldInvertSecondary,
        double deadZoneMin,
        double deadZoneMax,
        double multiplier,
        double defaultValue,
        ResultCalculator resultCalculator)
    {
        super(operation, OperationType.Analog, userInputDevice, -1.0, 1.0, relevantShifts, requiredShifts);

        this.userInputDeviceAxis = userInputDeviceAxis;
        this.userInputDeviceSecondaryAxis = userInputDeviceSecondaryAxis;
        this.shouldInvert = shouldInvert;
        this.shouldInvertSecondary = shouldInvertSecondary;
        this.deadZoneMin = deadZoneMin;
        this.deadZoneMax = deadZoneMax;
        this.multiplier = multiplier;
        this.defaultValue = defaultValue;
        this.resultCalculator = resultCalculator;

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
        super(operation, OperationType.Analog, UserInputDevice.Sensor, -1.0, 1.0, null, null);

        this.userInputDeviceAxis = AnalogAxis.NONE;
        this.userInputDeviceSecondaryAxis = AnalogAxis.NONE;
        this.shouldInvert = false;
        this.shouldInvertSecondary = false;
        this.deadZoneMin = 0.0;
        this.deadZoneMax = 0.0;
        this.multiplier = 1.0;
        this.defaultValue = 0.0;
        this.resultCalculator = null;

        this.sensor = sensor;
    }

    public AnalogAxis getUserInputDeviceAxis()
    {
        return this.userInputDeviceAxis;
    }

    public AnalogAxis getUserInputDeviceSecondaryAxis()
    {
        return this.userInputDeviceSecondaryAxis;
    }

    public AnalogSensor getSensor()
    {
        return this.sensor;
    }

    public boolean getShouldInvert()
    {
        return this.shouldInvert;
    }

    public boolean getShouldInvertSecondary()
    {
        return this.shouldInvertSecondary;
    }

    public double getDeadZoneMin()
    {
        return this.deadZoneMin;
    }

    public double getDeadZoneMax()
    {
        return this.deadZoneMax;
    }

    public double getMultiplier()
    {
        return this.multiplier;
    }

    public double getDefaultValue()
    {
        return this.defaultValue;
    }

    public ResultCalculator getResultCalculator()
    {
        return this.resultCalculator;
    }

    public interface ResultCalculator
    {
        public double calculate(double primaryValue, double secondaryValue);
    }
}
