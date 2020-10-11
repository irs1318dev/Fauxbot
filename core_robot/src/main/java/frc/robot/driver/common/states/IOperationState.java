package frc.robot.driver.common.states;

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
     * Checks whether the operation state should change based on the driver and operator joysticks and component sensors. 
     * @param driver joystick to update from
     * @param operator joystick to update from
     * @param activeShifts to update from
     * @return true if there was any active user input that triggered a state change
     */
    public boolean checkInput(IJoystick driver, IJoystick operator, Shift activeShifts);
}