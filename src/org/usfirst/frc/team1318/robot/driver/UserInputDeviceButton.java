package org.usfirst.frc.team1318.robot.driver;

/**
 * All constants that describe how the name of each button on the joystick maps to its button number.
 * 
 * Button guide:
 * -----------------------
 * Logitech Xtreme 3D Pro
 * 1 - stick trigger
 * 2 - stick thumb button
 * 3 - stick bottom left
 * 4 - stick bottom right
 * 5 - stick top left
 * 6 - stick top right
 * 7 - base top left
 * 8 - base top right
 * 9 - base middle left
 * 10 - base middle right
 * 11 - base bottom left
 * 12 - base bottom right
 * POV (hat): -1 when not pressed, 0-360.  0 is forward/up, 90 is right, 180 is back/down, 270 is left.
 * -----------------------
 * 
 */
public enum UserInputDeviceButton
{
    // None - represents non-button:
    NONE(-1),

    // Joystick constants:
    JOYSTICK_STICK_TRIGGER_BUTTON(1),
    JOYSTICK_STICK_THUMB_BUTTON(2),
    JOYSTICK_STICK_BOTTOM_LEFT_BUTTON(3),
    JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON(4),
    JOYSTICK_STICK_TOP_LEFT_BUTTON(5),
    JOYSTICK_STICK_TOP_RIGHT_BUTTON(6),
    JOYSTICK_BASE_TOP_LEFT_BUTTON(7),
    JOYSTICK_BASE_TOP_RIGHT_BUTTON(8),
    JOYSTICK_BASE_MIDDLE_LEFT_BUTTON(9),
    JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON(10),
    JOYSTICK_BASE_BOTTOM_LEFT_BUTTON(11),
    JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON(12),
    JOYSTICK_POV(13),

    // Button Pad button constants:
    BUTTON_PAD_BUTTON_1(1),
    BUTTON_PAD_BUTTON_2(2),
    BUTTON_PAD_BUTTON_3(3),
    BUTTON_PAD_BUTTON_4(4),
    BUTTON_PAD_BUTTON_5(5),
    BUTTON_PAD_BUTTON_6(6),
    BUTTON_PAD_BUTTON_7(7),
    BUTTON_PAD_BUTTON_8(8),
    BUTTON_PAD_BUTTON_9(9),
    BUTTON_PAD_BUTTON_10(10),
    BUTTON_PAD_BUTTON_11(11),
    BUTTON_PAD_BUTTON_12(12),
    BUTTON_PAD_BUTTON_13(13),
    BUTTON_PAD_BUTTON_14(14),
    BUTTON_PAD_BUTTON_15(15),
    BUTTON_PAD_BUTTON_16(16);

    public final int Value;
    private UserInputDeviceButton(int value)
    {
        this.Value = value;
    }
}
