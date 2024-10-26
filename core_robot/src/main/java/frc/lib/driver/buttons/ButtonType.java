package frc.lib.driver.buttons;

/**
 * The supported button types.
 * 
 * Simple buttons activate whenever they are pressed and remain activated while they are pressed, becoming deactivated when released.
 * Click buttons activate when pressed and remain activated for exactly one update cycle, remaining deactivated until the button is released and then pressed again.
 * Toggle buttons activate when pressed and remain activated even after released, becoming deactivated the next time it is pressed (and even after released), alternating between activated/deactivated each time it is pressed.
 *
 */
public enum ButtonType
{
    Simple,
    Click,
    Toggle;
}
