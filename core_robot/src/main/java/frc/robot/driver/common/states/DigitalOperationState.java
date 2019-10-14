package frc.robot.driver.common.states;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.Shift;
import frc.robot.driver.common.UserInputDeviceButton;
import frc.robot.driver.common.buttons.ClickButton;
import frc.robot.driver.common.buttons.IButton;
import frc.robot.driver.common.buttons.SimpleButton;
import frc.robot.driver.common.buttons.ToggleButton;
import frc.robot.driver.common.descriptions.DigitalOperationDescription;
import frc.robot.driver.common.descriptions.UserInputDevice;

/**
 * The state of the current digital operation.
 *
 */
public class DigitalOperationState extends OperationState
{
    private final IButton button;
    private boolean isInterrupted;
    private boolean interruptValue;

    public DigitalOperationState(DigitalOperationDescription description)
    {
        super(description);

        this.isInterrupted = false;
        this.interruptValue = false;
        switch (description.getButtonType())
        {
            case Simple:
                this.button = new SimpleButton();
                break;

            case Click:
                this.button = new ClickButton();
                break;

            case Toggle:
                this.button = new ToggleButton();
                break;

            default:
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("Unexpected button type " + description.getButtonType().toString());
                }

                this.button = null;
                break;
        }
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    @Override
    public void setIsInterrupted(boolean enable)
    {
        this.isInterrupted = enable;
        if (!enable)
        {
            this.interruptValue = false;
        }
    }

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    @Override
    public boolean getIsInterrupted()
    {
        return this.isInterrupted;
    }

    /**
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick driver, IJoystick coDriver, Shift activeShifts)
    {
        DigitalOperationDescription description = (DigitalOperationDescription)this.getDescription();

        UserInputDevice userInputDevice = description.getUserInputDevice();
        if (userInputDevice == UserInputDevice.None)
        {
            return false;
        }

        Shift relevantShifts = description.getRelevantShifts();
        Shift requiredShifts = description.getRequiredShifts();
        if (relevantShifts != null && requiredShifts != null)
        {
            Shift relevantActiveShifts = Shift.Intersect(relevantShifts, activeShifts);
            if (relevantActiveShifts.hasFlag(requiredShifts))
            {
                this.button.updateState(false);
                return false;
            }
        }

        IJoystick relevantJoystick;
        UserInputDeviceButton relevantButton;
        switch (userInputDevice)
        {
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
                    throw new RuntimeException("Unexpected user input device " + description.getUserInputDevice().toString());
                }

                return false;
        }

        boolean buttonPressed;
        if (relevantJoystick != null)
        {
            // find the appropriate button and grab the value from the relevant joystick
            relevantButton = description.getUserInputDeviceButton();

            if (relevantButton == UserInputDeviceButton.POV)
            {
                buttonPressed = relevantJoystick.getPOV() == description.getUserInputDevicePovValue();
            }
            else if (relevantButton == UserInputDeviceButton.ANALOG_AXIS_RANGE)
            {
                double value = relevantJoystick.getAxis(description.getUserInputDeviceAxis().Value);
                buttonPressed =
                    value >= description.getUserInputDeviceRangeMin() &&
                    value <= description.getUserInputDeviceRangeMax();
            }
            else if (relevantButton != UserInputDeviceButton.NONE)
            {
                buttonPressed = relevantJoystick.getRawButton(relevantButton.Value);
            }
            else
            {
                buttonPressed = false;
            }
        }
        else
        {
            // grab the appropriate sensor output.
            // e.g.: if (description.getSensor() == DigitalSensor.None) ...
            buttonPressed = false;
        }

        this.button.updateState(buttonPressed);
        return buttonPressed;
    }

    public boolean getState()
    {
        if (this.isInterrupted)
        {
            return this.interruptValue;
        }

        return this.button.isActivated();
    }

    public void setInterruptState(boolean value)
    {
        if (!this.isInterrupted)
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("Cannot set interrupt state for non-interrupted digital operations (" + this.getDescription().getOperation().toString() + ")");
            }
        }

        this.interruptValue = value;
    }
}
