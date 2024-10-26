package frc.lib.robotprovider;

/**
 * Represents some kind of joystick or controller used for the robot
 */
public interface IJoystick
{
    /**
     * Gets a value indicating if there is a joystick plugged into that slot
     * @return true if connected, otherwise false
     */
    boolean isConnected();

    /**
     * Get the extent to which the current analog input axis is pushed away from its origin
     * @param relevantAxis representing which stick/axis
     * @return 0.0 if at origin point, otherwise -1.0 if full reverse or 1.0 if full forward
     */
    double getAxis(int relevantAxis);

    /**
     * Gets the current POV (d-pad) value
     * @return -1 if unpressed, otherwise a value indicating the direction
     */
    int getPOV();

    /**
     * Get whether the specified button is currently pressed
     * @param value representing which button
     * @return true if pressed, otherwise false
     */
    boolean getRawButton(int value);

    /**
     * Makes the joystick rumble
     * @param type whether to rumble left or right
     * @param value value between 0.0 and 1.0 (percentage rumble strength)
     */
    void setRumble(JoystickRumbleType type, double value);
}
