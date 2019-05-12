package frc.robot.driver.common.states;

import java.util.Set;

import frc.robot.common.robotprovider.IJoystick;
import frc.robot.driver.Shift;

/**
 * The state of a current operation.
 *
 */
public interface IOperationState
{
    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    public void setIsInterrupted(boolean enable);

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    public boolean getIsInterrupted();

    /**
     * Checks whether the operation state should change based on the driver and co-driver joysticks and component sensors. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    public boolean checkInput(IJoystick driver, IJoystick coDriver, Set<Shift> activeShifts);
}