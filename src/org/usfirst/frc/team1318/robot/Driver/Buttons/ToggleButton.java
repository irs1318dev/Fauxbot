package org.usfirst.frc.team1318.robot.Driver.Buttons;

/**
 * Defines a simple button that switches between true and false.
 * 
 * Toggle on press behavior:
 * 
 *     button pressed:        _________________
 *                           |                 |
 * button not pressed: ______|                 |________
 *                           ^ takes effect when first pressed
 * 
 * Toggle on release behavior:
 * 
 *     button pressed:        _________________
 *                           |                 |
 * button not pressed: ______|                 |________
 *                                             ^ takes effect when first released
 * 
 * 
 * @author Will
 *
 */
public class ToggleButton implements IButton
{
    private final boolean toggleOnPress;

    private boolean currentState;
    private boolean prevButtonState;

    /**
     * Initializes a new ToggleButton
     */
    public ToggleButton()
    {
        this(true);
    }

    /**
     * Initializes a new ToggleButton
     * @param toggleOnPress indicates whether we should toggle when the button is first pressed or when released
     */
    public ToggleButton(boolean toggleOnPress)
    {
        this.currentState = false;
        this.prevButtonState = false;

        this.toggleOnPress = toggleOnPress;
    }

    /**
     * update the state of the button based on information from the user input device.
     * @param buttonState the current position of the button (whether it is currently pressed)
     */
    public void updateState(boolean buttonState)
    {
        // if button has switched state, check if we want to toggle
        if (this.prevButtonState != buttonState &&
            (this.toggleOnPress && buttonState || !this.toggleOnPress && !buttonState))
        {
            this.currentState = !this.currentState;
        }

        this.prevButtonState = buttonState;
    }

    /**
     * gets a value indicating whether the button is activated 
     * @return true for active, otherwise false
     */
    public boolean isActivated()
    {
        return this.currentState;
    }

    public void clearState()
    {
        this.currentState = false;
        this.prevButtonState = false;
    }
}
