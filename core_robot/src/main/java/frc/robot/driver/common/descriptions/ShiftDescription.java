package frc.robot.driver.common.descriptions;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.common.AnalogAxis;
import frc.robot.driver.common.UserInputDeviceButton;

public class ShiftDescription
{
    private final UserInputDevice userInputDevice;
    private final UserInputDeviceButton userInputDeviceButton;
    private final int userInputDevicePovValue;
    private final AnalogAxis userInputDeviceAxis;
    private final double userInputDeviceAxisRangeMin;
    private final double userInputDeviceAxisRangeMax;

    public ShiftDescription(UserInputDevice userInputDevice, UserInputDeviceButton userInputDeviceButton)
    {
        this(userInputDevice, userInputDeviceButton, -1, AnalogAxis.NONE, 0.0, 0.0);
    }

    public ShiftDescription(UserInputDevice userInputDevice, int povValue)
    {
        this(userInputDevice, UserInputDeviceButton.POV, povValue, AnalogAxis.NONE, 0.0, 0.0);
    }

    public ShiftDescription(
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue)
    {
        this(userInputDevice, UserInputDeviceButton.ANALOG_AXIS_RANGE, -1, analogAxis, axisRangeMinValue, axisRangeMaxValue);
    }

    private ShiftDescription(
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        int povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue)
    {
        this.userInputDevice = userInputDevice;
        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.userInputDeviceAxisRangeMin = axisRangeMinValue;
        this.userInputDeviceAxisRangeMax = axisRangeMaxValue;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }

    public UserInputDeviceButton getUserInputDeviceButton()
    {
        return this.userInputDeviceButton;
    }

    public int getUserInputDevicePovValue()
    {
        return this.userInputDevicePovValue;
    }

    public boolean checkInput(IJoystick driver, IJoystick coDriver)
    {
        IJoystick relevantJoystick;
        UserInputDeviceButton relevantButton;
        switch (this.getUserInputDevice())
        {
            case None:
                return false;

            case Driver:
                relevantJoystick = driver;
                break;

            case CoDriver:
                relevantJoystick = coDriver;
                break;

            case Sensor:
                relevantJoystick = null;

            default:
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("unexpected user input device " + this.getUserInputDevice().toString());
                }

                return false;
        }

        if (relevantJoystick != null)
        {
            // find the appropriate button and grab the value from the relevant joystick
            relevantButton = this.getUserInputDeviceButton();

            if (relevantButton == UserInputDeviceButton.POV)
            {
                return relevantJoystick.getPOV() == this.getUserInputDevicePovValue();
            }
            else if (relevantButton == UserInputDeviceButton.ANALOG_AXIS_RANGE)
            {
                double value = relevantJoystick.getAxis(this.userInputDeviceAxis.Value);
                return
                    value >= this.userInputDeviceAxisRangeMin &&
                    value <= this.userInputDeviceAxisRangeMax;
            }
            else if (relevantButton != UserInputDeviceButton.NONE)
            {
                return relevantJoystick.getRawButton(relevantButton.Value);
            }
            else
            {
                return false;
            }
        }
        else
        {
            // grab the appropriate sensor output.
            // e.g.: if (description.getSensor() == DigitalSensor.None) ...
            return false;
        }
    }
}
