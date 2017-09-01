package org.usfirst.frc.team1318.robot.driver.common.states;

import java.util.Map;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.wpilib.IJoystick;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.IControlTask;
import org.usfirst.frc.team1318.robot.driver.common.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.ClickButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.IButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.SimpleButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.ToggleButton;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;

import com.google.inject.Injector;

/**
 * The state of the current macro operation.
 *
 */
public class MacroOperationState extends OperationState
{
    private final IButton button;
    private final Map<Operation, OperationState> operationStateMap;
    private final Injector injector;

    private IControlTask task;

    public MacroOperationState(
        MacroOperationDescription description,
        Map<Operation, OperationState> operationStateMap,
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
                    throw new RuntimeException("unexpected button type " + description.getButtonType().toString());
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
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(IJoystick driver, IJoystick coDriver)
    {
        MacroOperationDescription description = (MacroOperationDescription)this.getDescription();

        IJoystick relevantJoystick;
        UserInputDeviceButton relevantButton;
        switch (description.getUserInputDevice())
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
                    throw new RuntimeException("unexpected user input device " + description.getUserInputDevice().toString());
                }

                return false;
        }

        boolean buttonPressed;
        if (relevantJoystick != null)
        {
            // find the appropriate button and grab the value from the relevant joystick
            relevantButton = description.getUserInputDeviceButton();

            if (relevantButton == UserInputDeviceButton.JOYSTICK_POV)
            {
                buttonPressed = relevantJoystick.getPOV() == description.getUserInputDevicePovValue();
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

    public Operation[] getAffectedOperations()
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
                for (Operation operation : this.getAffectedOperations())
                {
                    this.operationStateMap.get(operation).setIsInterrupted(true);
                }

                // start task
                this.task = ((MacroOperationDescription)this.getDescription()).constructTask();
                this.task.initialize(this.operationStateMap, this.injector);
                this.task.begin();
            }
            else
            {
                boolean shouldEnd = this.task.hasCompleted();
                boolean shouldCancel = this.task.shouldCancel();
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
                        for (Operation operation : this.getAffectedOperations())
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
        }
        else if (this.task != null)
        {
            // cancel task:
            this.task.stop();
            this.task = null;

            for (Operation operation : this.getAffectedOperations())
            {
                this.operationStateMap.get(operation).setIsInterrupted(false);
            }
        }
    }
}
