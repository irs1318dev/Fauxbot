package org.usfirst.frc.team1318.robot.Driver.Buttons;

/**
 * Interface for different types of button to handle how the code responds to user input devices.
 * 
 */
public interface IButton
{
    /**
     * update the state of the button based on information from the user input device.
     * @param buttonState the current position of the button (whether it is currently pressed) 
     */
    public void updateState(boolean buttonState);

    /**
     * gets a value indicating whether the button is activated 
     * @return true for active, otherwise false 
     */
    public boolean isActivated();

    /**
     * clear the button value
     */
    public void clearState();
}
