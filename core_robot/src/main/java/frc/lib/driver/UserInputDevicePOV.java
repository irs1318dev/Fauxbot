package frc.lib.driver;

/**
 * All constants that describe how the name of each direction on the POV (i.e. DPAD) on the joystick maps to its POV value.
 *
 * XBox One Controller (and Logitech F310):
 * PS4 Controller:
 * Logitech Xtreme 3D Pro:
 * POV - DPAD/hat (-1 when not pressed, 0-360. 0 is forward/up, 90 is right, 180 is back/down, 270 is left)
 * 
 * Razer Tartarus button pad:
 * None.
 */
public enum UserInputDevicePOV
{
    NONE(-1),
    UP(0),
    RIGHT(90),
    DOWN(180),
    LEFT(270);

    public final int Value;

    private UserInputDevicePOV(int value)
    {
        this.Value = value;
    }
}
