package frc.lib.driver.states;

import java.util.EnumMap;
import java.util.EnumSet;

import frc.robot.TuningConstants;
import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperation;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.robotprovider.IJoystick;
import frc.lib.driver.buttons.ClickButton;
import frc.lib.driver.buttons.IButton;
import frc.lib.driver.buttons.SimpleButton;
import frc.lib.driver.buttons.ToggleButton;
import frc.lib.driver.descriptions.MacroOperationDescription;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.SetHelper;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.Shift;

import com.google.inject.Injector;

/**
 * The state of the current macro operation.
 *
 */
public class MacroOperationState extends OperationState implements IMacroOperationState
{
    private final IButton button;
    private final EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap;
    private final EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap;
    private final Injector injector;

    private IControlTask task;

    public MacroOperationState(
        MacroOperationDescription description,
        EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap,
        EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap,
        Injector injector)
    {
        super(description);

        this.analogOperationStateMap = analogOperationStateMap;
        this.digitalOperationStateMap = digitalOperationStateMap;
        this.injector = injector;

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

        this.task = null;
        this.button.clearState();
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    @Override
    public void setIsInterrupted(boolean enable)
    {
        if (enable)
        {
            this.button.clearState();
        }
    }

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    @Override
    public boolean getIsInterrupted()
    {
        return false;
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
        MacroOperationDescription description = (MacroOperationDescription)this.getDescription();

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

    public AnalogOperation[] getMacroCancelAnalogOperations()
    {
        return ((MacroOperationDescription)this.getDescription()).getMacroCancelAnalogOperations();
    }

    public DigitalOperation[] getMacroCancelDigitalOperations()
    {
        return ((MacroOperationDescription)this.getDescription()).getMacroCancelDigitalOperations();
    }

    public IOperation[] getAffectedOperations()
    {
        return ((MacroOperationDescription)this.getDescription()).getAffectedOperations();
    }

    public boolean getIsActive()
    {
        return this.button.isActivated();
    }

    public void run()
    {
        if (this.button.isActivated())
        {
            if (this.task == null)
            {
                this.setInterrupts(true);

                // start task
                this.task = ((MacroOperationDescription)this.getDescription()).constructTask();
                this.task.initialize(this.analogOperationStateMap, this.digitalOperationStateMap, this.injector);
                this.task.begin();
            }

            boolean shouldEnd = this.task.hasCompleted();
            boolean shouldCancel = !shouldEnd && this.task.shouldCancel();
            if (shouldEnd || shouldCancel)
            {
                if (shouldEnd)
                {
                    this.task.end();
                }
                else
                {
                    this.task.stop();
                }

                this.task = null;
                this.button.clearState();

                MacroOperationDescription description = (MacroOperationDescription)this.getDescription();
                if (description.shouldClearInterrupt())
                {
                    this.setInterrupts(false);
                }
            }
            else
            {
                this.task.update();
            }
        }
        else if (this.task != null)
        {
            // cancel task:
            this.task.stop();
            this.task = null;

            this.setInterrupts(false);
        }
    }

    public void cancel()
    {
        this.task = null;
        this.button.clearState();
    }

    private void setInterrupts(boolean enable)
    {
        for (IOperation operation : this.getAffectedOperations())
        {
            if (operation instanceof AnalogOperation)
            {
                this.analogOperationStateMap.get((AnalogOperation)operation).setIsInterrupted(enable);
            }
            else
            {
                ExceptionHelpers.Assert(operation instanceof DigitalOperation, "Expect operation of type DigitalOperation");
                this.digitalOperationStateMap.get((DigitalOperation)operation).setIsInterrupted(enable);
            }
        }
    }
}
