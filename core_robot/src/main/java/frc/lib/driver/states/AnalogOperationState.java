package frc.lib.driver.states;

import frc.robot.TuningConstants;
import frc.lib.driver.AnalogAxis;
import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.IJoystick;
import frc.robot.driver.Shift;

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

        double defaultValue = description.getDefaultValue();
        this.currentValue = defaultValue;
        this.isInterrupted = false;
        this.interruptValue = defaultValue;
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
            this.interruptValue = ((AnalogOperationDescription)this.getDescription()).getDefaultValue();
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
    public boolean checkInput(IJoystick[] joysticks, Shift activeShifts)
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
            if (!relevantActiveShifts.equals(requiredShifts))
            {
                this.currentValue = description.getDefaultValue();
                return false;
            }
        }

        IJoystick relevantJoystick = joysticks[userInputDevice.getId()];
        if (relevantJoystick == null || !relevantJoystick.isConnected())
        {
            ExceptionHelpers.Assert(TuningConstants.EXPECT_UNUSED_JOYSTICKS, "Unexpected user input device " + userInputDevice.toString());
            this.currentValue = description.getDefaultValue();
            return false;
        }

        AnalogAxis relevantAxis = description.getUserInputDeviceAxis();
        if (relevantAxis == null || relevantAxis == AnalogAxis.NONE)
        {
            return false;
        }

        double oldValue = this.currentValue;
        double newValue = relevantJoystick.getAxis(relevantAxis.Value);
        if (description.getShouldInvert())
        {
            newValue *= -1.0;
        }

        AnalogAxis secondaryAxis = description.getUserInputDeviceSecondaryAxis();
        if (secondaryAxis != null && secondaryAxis != AnalogAxis.NONE)
        {
            double secondaryValue = relevantJoystick.getAxis(secondaryAxis.Value);
            if (description.getShouldInvertSecondary())
            {
                secondaryValue *= -1.0;
            }

            // don't adjust for dead zone, simply check for having both within dead zone
            if (this.withinDeadZone(newValue, secondaryValue, description.getDeadZoneMin(), description.getDeadZoneMax(), description.getUseSquaredMagnitudeForDeadZone()))
            {
                this.currentValue = description.getDefaultValue();
                return false;
            }

            AnalogOperationDescription.ResultCalculator calculator = description.getResultCalculator();
            if (calculator == null)
            {
                ExceptionHelpers.Assert(false, "No result calculator provided despite having secondary axis!");
                this.currentValue = description.getDefaultValue();
                return false;
            }

            newValue = calculator.calculate(newValue, secondaryValue);
        }
        else
        {
            newValue = this.adjustForDeadZone(newValue, description.getDeadZoneMin(), description.getDeadZoneMax(), description.getDefaultValue(), description.getMultiplier(), description.getExp());
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
        ExceptionHelpers.Assert(this.isInterrupted, "Cannot set interrupt state for non-interrupted analog operations (" + this.getDescription().getOperation().toString() + ")");
        this.interruptValue = value;
    }

    /**
     * Adjust the value as a part of dead zone calculation
     * @param value to adjust
     * @param deadZoneMin to consider
     * @param deadZoneMax to consider
     * @param multiplier to scale the result range
     * @param exp to adjust the result
     * @return adjusted value for deadZone, exponential, and multiplier
     */
    private double adjustForDeadZone(double value, double deadZoneMin, double deadZoneMax, double defaultValue, double multiplier, double exp)
    {
        if (value < deadZoneMax && value > deadZoneMin)
        {
            return defaultValue;
        }

        // scale so that we have the area just outside the deadzone be the starting point
        double deadZone = deadZoneMax;
        if (value < 0.0)
        {
            deadZone = deadZoneMin;
        }

        // adjust the result to exclude the deadzone
        double result = (value - deadZone) / (1.0 - Math.abs(deadZone));

        // adjust 
        if (exp != 1.0)
        {
            if (result < 0.0 && (exp % 2.0) != 1.0)
            {
                result = -Math.pow(result, exp);
            }
            else
            {
                result = Math.pow(result, exp);
            }
        }

        // adjust the result range based on the multiplier
        return multiplier * result;
    }

    /**
     * Adjust the value as a part of dead zone calculation
     * @param value1 to check
     * @param value2 to check
     * @param deadZoneMin to consider
     * @param deadZoneMax to consider
     * @param useSquaredMagnitude for the deadzone check instead of piecemeal check
     * @return whether both are within the deadzone
     */
    private boolean withinDeadZone(double value1, double value2, double deadZoneMin, double deadZoneMax, boolean useSquaredMagnitude)
    {
        if (useSquaredMagnitude)
        {
            return Helpers.WithinRange(value1 * value1 + value2 * value2, deadZoneMin, deadZoneMax);
        }

        return Helpers.WithinRange(value1, deadZoneMax, deadZoneMin) && 
            Helpers.WithinRange(value2, deadZoneMax, deadZoneMin);
    }
}
