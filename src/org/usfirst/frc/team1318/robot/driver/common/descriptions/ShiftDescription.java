package org.usfirst.frc.team1318.robot.driver.common.descriptions;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.wpilib.IJoystick;
import org.usfirst.frc.team1318.robot.driver.common.UserInputDeviceButton;

public class ShiftDescription
{
    private final UserInputDevice userInputDevice;
    private final UserInputDeviceButton userInputDeviceButton;

    public ShiftDescription(UserInputDevice userInputDevice, UserInputDeviceButton userInputDeviceButton)
    {
        this.userInputDevice = userInputDevice;
        this.userInputDeviceButton = userInputDeviceButton;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }

    public UserInputDeviceButton getUserInputDeviceButton()
    {
        return this.userInputDeviceButton;
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

            if (relevantButton == UserInputDeviceButton.JOYSTICK_POV)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("unexpected user input device button " + this.getUserInputDeviceButton().toString());
                }

                return false;
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
