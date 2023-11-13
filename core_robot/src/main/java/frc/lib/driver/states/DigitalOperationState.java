package frc.lib.driver.states;

import frc.robot.TuningConstants;

import java.util.EnumSet;

import frc.lib.driver.UserInputDeviceButton;
import frc.lib.robotprovider.IJoystick;
import frc.lib.driver.buttons.ClickButton;
import frc.lib.driver.buttons.IButton;
import frc.lib.driver.buttons.SimpleButton;
import frc.lib.driver.buttons.ToggleButton;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.SetHelper;
import frc.robot.driver.Shift;

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
                ExceptionHelpers.Assert(false, "Unexpected button type " + description.getButtonType().toString());
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
     * Checks whether the operation state should change based on the joysticks and active stifts. 
     * @param joysticks to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick[] joysticks, EnumSet<Shift> activeShifts)
    {
        DigitalOperationDescription description = (DigitalOperationDescription)this.getDescription();

        UserInputDevice userInputDevice = description.getUserInputDevice();
        if (userInputDevice == UserInputDevice.None)
        {
            return false;
        }

        EnumSet<Shift> relevantShifts = description.getRelevantShifts();
        EnumSet<Shift> requiredShifts = description.getRequiredShifts();
        if (relevantShifts != null && requiredShifts != null)
        {
            EnumSet<Shift> relevantActiveShifts = SetHelper.Intersection(relevantShifts, activeShifts);
            if (!relevantActiveShifts.equals(requiredShifts))
            {
                this.button.updateState(false);
                return false;
            }
        }

        IJoystick relevantJoystick = joysticks[userInputDevice.getId()];
        if (relevantJoystick == null || !relevantJoystick.isConnected())
        {
            ExceptionHelpers.Assert(TuningConstants.EXPECT_UNUSED_JOYSTICKS, "Unexpected user input device " + userInputDevice.toString());
            return false;
        }

        // find the appropriate button and grab the value from the relevant joystick
        boolean buttonPressed;
        UserInputDeviceButton relevantButton = description.getUserInputDeviceButton();
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
        ExceptionHelpers.Assert(this.isInterrupted, "Cannot set interrupt state for non-interrupted digital operations (" + this.getDescription().getOperation().toString() + ")");
        this.interruptValue = value;
    }
}
