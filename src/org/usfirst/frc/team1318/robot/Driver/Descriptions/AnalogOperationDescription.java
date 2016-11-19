package org.usfirst.frc.team1318.robot.Driver.Descriptions;

import org.usfirst.frc.team1318.robot.Driver.Buttons.AnalogAxis;

/**
 * Describes an operation that will give a double value (typically between -1 and 1).
 * 
 */
public class AnalogOperationDescription extends OperationDescription
{
    private final AnalogAxis userInputDeviceAxis;
    private final AnalogSensor sensor;

    /**
     * Initializes a new AnalogOperationDescription based on a user interaction
     * @param userInputDevice which device will indicate the operation (driver or codriver joystick) 
     * @param userInputDeviceAxis the axis on the device that will indicate the operation
     */
    public AnalogOperationDescription(UserInputDevice userInputDevice, AnalogAxis userInputDeviceAxis)
    {
        super(OperationType.Analog, userInputDevice);

        this.userInputDeviceAxis = userInputDeviceAxis;
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

    public double getDefaultValue()
    {
        return 0.0;
    }
}
