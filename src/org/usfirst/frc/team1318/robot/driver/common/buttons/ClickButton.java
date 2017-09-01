package org.usfirst.frc.team1318.robot.driver.common.buttons;

/**
 * Defines a simple button that activates when clicked and deactivates when measured
 * 
 * Register on press behavior:
 * 
 *     button pressed:        _________________
 *                           |                 |
 * button not pressed: ______|                 |________
 *                           ^ takes effect when first pressed
 *                               ^ cleared after next update
 * 
 * Register on release behavior:
 * 
 *     button pressed:        _________________
 *                           |                 |
 * button not pressed: ______|                 |________
 *                                             ^ takes effect when first released
 *                                                 ^ cleared after next update
 * 
 */
public class ClickButton implements IButton
{
    private final boolean registerOnPress;

    private boolean isActivated;
    private boolean prevButtonState;

    /**
     * Initializes a new ClickButton
     */
    public ClickButton()
    {
        this(true);
    }

    /**
     * Initializes a new ClickButton
     * @param registerOnPress indicates whether we should activate when the button is first pressed or when released
     */
    public ClickButton(boolean registerOnPress)
    {
        this.isActivated = false;
        this.prevButtonState = false;

        this.registerOnPress = registerOnPress;
    }

    /**
     * update the state of the button based on information from the user input device.
     * @param buttonState the current position of the button (whether it is currently pressed)
     */
    public void updateState(boolean buttonState)
    {
        // if button has switched state, check if we want to activate
        if ((buttonState && !this.prevButtonState && this.registerOnPress) ||
            (!buttonState && this.prevButtonState && !this.registerOnPress))
        {
            this.isActivated = true;
        }
        else
        {
            this.isActivated = false;
        }

        this.prevButtonState = buttonState;
    }

    /**
     * gets a value indicating whether the button is activated 
     * @return true for active, otherwise false
     */
    public boolean isActivated()
    {
        return this.isActivated;
    }

    public void clearState()
    {
        this.isActivated = false;
        this.prevButtonState = false;
    }
}
