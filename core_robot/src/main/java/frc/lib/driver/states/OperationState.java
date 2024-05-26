package frc.lib.driver.states;

import frc.lib.robotprovider.IJoystick;

import java.util.EnumSet;

import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.descriptions.OperationDescription;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.Shift;

/**
 * The state of a current operation.
 *
 */
public abstract class OperationState implements IOperationState
{
    private final OperationDescription<?> description;

    protected OperationState(OperationDescription<?> description)
    {
        this.description = description;
    }

    protected OperationDescription<?> getDescription()
    {
        return this.description;
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    public abstract void setIsInterrupted(boolean enable);

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    public abstract boolean getIsInterrupted();

    /**
     * Checks whether the operation state should change based on the joysticks and component sensors. 
     * @param joysticks to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    public abstract boolean checkInput(IJoystick[] joysticks, EnumSet<Shift> activeShifts);

    /**
     * Create the state corresponding to the description
     * @param description to use for creating the state
     * @return state corresponding to the description
     */
    public static OperationState createFromDescription(OperationDescription<?> description)
    {
        if (description instanceof AnalogOperationDescription)
        {
            return new AnalogOperationState((AnalogOperationDescription)description);
        }
        else if (description instanceof DigitalOperationDescription)
        {
            return new DigitalOperationState((DigitalOperationDescription)description);
        }

        ExceptionHelpers.Assert(false, "unknown type of description " + description.getClass().getName());
        return null;
    }
}
