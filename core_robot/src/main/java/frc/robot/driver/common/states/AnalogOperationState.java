package frc.robot.driver.common.states;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.Shift;
import frc.robot.driver.common.AnalogAxis;
import frc.robot.driver.common.descriptions.AnalogOperationDescription;
import frc.robot.driver.common.descriptions.UserInputDevice;

/**
 * The state of the current analog operation.
 *
 */
public class AnalogOperationState extends OperationState
{
    private double currentValue;
    private boolean isInterrupted;
    private double interruptValue;

    public AnalogOperationState(AnalogOperationDescription description)
    {
        super(description);

        this.currentValue = 0.0;
        this.isInterrupted = false;
        this.interruptValue = 0.0;
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
            this.interruptValue = 0.0;
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
        AnalogOperationDescription description = (AnalogOperationDescription)this.getDescription();

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
                return false;
            }
        }

        IJoystick relevantJoystick;
        AnalogAxis relevantAxis;
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

        double newValue;
        double oldValue = this.currentValue;
        if (relevantJoystick != null)
        {
            relevantAxis = description.getUserInputDeviceAxis();
            if (relevantAxis == null)
            {
                return false;
            }

            newValue = relevantJoystick.getAxis(relevantAxis.Value);
            if (description.getShouldInvert())
            {
                newValue *= -1.0;
            }

            newValue = this.adjustForDeadZone(newValue, description.getDeadZone());
        }
        else
        {
            // grab the appropriate sensor output.
            // e.g.: if (description.getSensor() == AnalogSensor.None) ...
            newValue = 0.0;
        }

        this.currentValue = newValue;
        return this.currentValue != oldValue;
    }

    public double getState()
    {
        if (this.isInterrupted)
        {
            return this.interruptValue;
        }

        return this.currentValue;
    }

    public void setInterruptState(double value)
    {
        if (!this.isInterrupted)
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("Cannot set interrupt state for non-interrupted analog operations (" + this.getDescription().getOperation().toString() + ")");
            }
        }

        this.interruptValue = value;
    }

    /**
     * Adjust the value as a part of dead zone calculation
     * @param value to adjust
     * @param deadZone to consider
     * @return adjusted value for deadZone
     */
    private double adjustForDeadZone(double value, double deadZone)
    {
        if (value < deadZone && value > -deadZone)
        {
            return 0.0;
        }

        double sign = 1.0;
        if (value < 0.0)
        {
            sign = -1.0;
        }

        // scale so that we have the area just outside the deadzone be the starting point
        return (value - sign * deadZone) / (1 - deadZone);
    }
}
