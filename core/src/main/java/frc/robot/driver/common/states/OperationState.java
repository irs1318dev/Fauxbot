package frc.robot.driver.common.states;

import java.util.Set;

import frc.robot.TuningConstants;
import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.Shift;
import frc.robot.driver.common.descriptions.AnalogOperationDescription;
import frc.robot.driver.common.descriptions.DigitalOperationDescription;
import frc.robot.driver.common.descriptions.OperationDescription;

/**
 * The state of a current operation.
 *
 */
public abstract class OperationState implements IOperationState
{
    private final OperationDescription description;

    protected OperationState(OperationDescription description)
    {
        this.description = description;
    }

    protected OperationDescription getDescription()
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
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    public abstract boolean checkInput(IJoystick driver, IJoystick coDriver, Set<Shift> activeShifts);

    /**
     * Create the state corresponding to the description
     * @param description to use for creating the state
     * @return state corresponding to the description
     */
    public static OperationState createFromDescription(OperationDescription description)
    {
        if (description instanceof AnalogOperationDescription)
        {
            return new AnalogOperationState((AnalogOperationDescription)description);
        }
        else if (description instanceof DigitalOperationDescription)
        {
            return new DigitalOperationState((DigitalOperationDescription)description);
        }

        if (TuningConstants.THROW_EXCEPTIONS)
        {
            throw new RuntimeException("unknown type of description " + description.getClass().getName());
        }

        return null;
    }
}
