package org.usfirst.frc.team1318.robot.driver.descriptions;

import org.usfirst.frc.team1318.robot.driver.buttons.AnalogAxis;

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
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     * @param shouldInvert whether we should invert the axis so that -1 and 1 are on the opposite ends as where they are designed to be in hardware
     * @param deadZone the amount in the center of the axis (around 0) that should be ignored to account for joystick sensors imprecision
     */
    public AnalogOperationDescription(UserInputDevice userInputDevice, AnalogAxis userInputDeviceAxis, boolean shouldInvert, double deadZone)
    {
        super(OperationType.Analog, userInputDevice);

        this.userInputDeviceAxis = userInputDeviceAxis;
        this.shouldInvert = shouldInvert;
        this.deadZone = deadZone;

        this.sensor = AnalogSensor.None;
    }

    /**
     * Initializes a new AnalogOperationDescription based on a sensor
     * @param sensor that will provide the value to use
     */
    public AnalogOperationDescription(AnalogSensor sensor)
    {
        super(OperationType.Analog, UserInputDevice.Sensor);

        this.userInputDeviceAxis = AnalogAxis.None;
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
