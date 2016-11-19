package org.usfirst.frc.team1318.robot.Driver.States;

import org.usfirst.frc.team1318.robot.ComponentManager;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.AnalogOperationDescription;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

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
     * @param components to update from
     * @return true if there was any active user input that triggered a state change
     */
    @SuppressWarnings("unused")
    @Override
    public boolean checkInput(Joystick driver, Joystick coDriver, ComponentManager components)
    {
        AnalogOperationDescription description = (AnalogOperationDescription)this.getDescription();

        Joystick relevantJoystick;
        AxisType relevantAxis;
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

        double newValue;
        double oldValue = this.currentValue;
        if (relevantJoystick != null)
        {
            boolean invert = false;
            relevantAxis = AnalogOperationState.fromAxis(description.getUserInputDeviceAxis());
            if (relevantAxis == null)
            {
                return false;
            }

            if (relevantAxis == AxisType.kY && ElectronicsConstants.INVERT_Y_AXIS)
            {
                invert = true;
            }
            else if (relevantAxis == AxisType.kX && ElectronicsConstants.INVERT_X_AXIS)
            {
                invert = true;
            }

            newValue = relevantJoystick.getAxis(relevantAxis);
            if (invert)
            {
                newValue *= -1.0;
            }
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
                throw new RuntimeException("cannot set interrupt state for non-interrupted analog operations");
            }
        }

        this.interruptValue = value;
    }

    public static AxisType fromAxis(AnalogAxis axis)
    {
        switch (axis)
        {
            case None:
                return null;

            case X:
                return AxisType.kX;

            case Y:
                return AxisType.kY;

            case Z:
                return AxisType.kZ;

            case Twist:
                return AxisType.kTwist;

            case Throttle:
                return AxisType.kThrottle;

            default:
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("unknown axis type " + axis);
                }

                return null;
        }
    }
}
