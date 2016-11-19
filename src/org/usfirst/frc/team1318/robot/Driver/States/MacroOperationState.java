package org.usfirst.frc.team1318.robot.Driver.States;

import java.util.Map;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Driver.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ClickButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.IButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.SimpleButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ToggleButton;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.MacroOperationDescription;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The state of the current macro operation.
 *
 */
public class MacroOperationState extends OperationState
{
    private final IButton button;
    private final Map<Operation, OperationState> operationStateMap;
    private final ComponentManager components;

    private IControlTask task;

    public MacroOperationState(
        MacroOperationDescription description,
        Map<Operation, OperationState> operationStateMap,
        ComponentManager components)
    {
        super(description);

        this.operationStateMap = operationStateMap;
        this.components = components;

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
     * @param components to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkInput(Joystick driver, Joystick coDriver, ComponentManager components)
    {
        MacroOperationDescription description = (MacroOperationDescription)this.getDescription();

        Joystick relevantJoystick;
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
                this.task.initialize(this.operationStateMap, this.components);
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
