package org.usfirst.frc.team1318.robot.driver.buttons;

/**
 * Defines a simple button that activates when pressed and deactivates when released
 * 
 *     button pressed:        _________________
 *                           |                 |
 * button not pressed: ______|                 |________
 *                            ^ active
 *                                              ^ not active
 * 
 */
public class SimpleButton implements IButton
{
    private final boolean activateOnPress;

    private boolean activated;

    /**
     * Initializes a new SimpleButton
     */
    public SimpleButton()
    {
        this(true);
    }

    /**
     * Initializes a new SimpleButton
     * @param activateOnPress indicates whether to consider the button activated when the key is pressed or when released 
     */
    public SimpleButton(boolean activateOnPress)
    {
        this.activateOnPress = activateOnPress;
    }

    /**
     * update the state of the button based on information from the user input device.
     * @param buttonState the current position of the button (whether it is currently pressed)
     */
    public void updateState(boolean buttonState)
    {
        if ((this.activateOnPress && buttonState)
            || (!this.activateOnPress && !buttonState))
        {
            this.activated = true;
        }
        else
        {
            this.activated = false;
        }
    }

    /**
     * gets a value indicating whether the button is activated 
     * @return true for active, otherwise false
     */
    public boolean isActivated()
    {
        return this.activated;
    }

    public void clearState()
    {
    }
}
