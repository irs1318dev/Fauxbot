package frc.robot.driver.common.states;

import java.util.Map;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.IOperation;
import frc.robot.driver.Shift;
import frc.robot.driver.common.IControlTask;
import frc.robot.driver.common.UserInputDeviceButton;
import frc.robot.driver.common.buttons.ClickButton;
import frc.robot.driver.common.buttons.IButton;
import frc.robot.driver.common.buttons.SimpleButton;
import frc.robot.driver.common.buttons.ToggleButton;
import frc.robot.driver.common.descriptions.MacroOperationDescription;
import frc.robot.driver.common.descriptions.UserInputDevice;

import com.google.inject.Injector;

/**
 * The state of the current macro operation.
 *
 */
public class MacroOperationState extends OperationState implements IMacroOperationState
{
    private final IButton button;
    private final Map<IOperation, OperationState> operationStateMap;
    private final Injector injector;

    private IControlTask task;

    public MacroOperationState(
        MacroOperationDescription description,
        Map<IOperation, OperationState> operationStateMap,
        Injector injector)
    {
        super(description);

        this.operationStateMap = operationStateMap;
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
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("Unexpected button type " + description.getButtonType().toString());
                }

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
     * Checks whether the operation state should change based on the driver and operator joysticks and component sensors. 
     * @param driver joystick to update from
     * @param operator joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick driver, IJoystick operator, Shift activeShifts)
    {
        MacroOperationDescription description = (MacroOperationDescription)this.getDescription();

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
            if (!relevantActiveShifts.equals(requiredShifts))
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

            case Operator:
                relevantJoystick = operator;
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

    public IOperation[] getMacroCancelOperations()
    {
        return ((MacroOperationDescription)this.getDescription()).getMacroCancelOperations();
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
                for (IOperation operation : this.getAffectedOperations())
                {
                    this.operationStateMap.get(operation).setIsInterrupted(true);
                }

                // start task
                this.task = ((MacroOperationDescription)this.getDescription()).constructTask();
                this.task.initialize(this.operationStateMap, this.injector);
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
                    for (IOperation operation : this.getAffectedOperations())
                    {
                        this.operationStateMap.get(operation).setIsInterrupted(false);
                    }
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

            for (IOperation operation : this.getAffectedOperations())
            {
                this.operationStateMap.get(operation).setIsInterrupted(false);
            }
        }
    }

    public void cancel()
    {
        this.task = null;
        this.button.clearState();
    }
}
