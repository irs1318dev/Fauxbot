package frc.robot.driver.common;

/**
 * All constants that describe how the name of each button on the joystick maps to its button number.
 * 
 * Button guide:
 * -----------------------
 * Logitech Xtreme 3D Pro:
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
 * POV - hat (-1 when not pressed, 0-360.  0 is forward/up, 90 is right, 180 is back/down, 270 is left)
 * -----------------------
 * XBox One Controller:
 * 1 - A
 * 2 - B
 * 3 - X
 * 4 - Y
 * 5 - LB (left button)
 * 6 - RB (right button)
 * 7 - Select (two squares)
 * 8 - Start (hamburger)
 * 9 - LS (left stick)
 * 10 - RS (right stick)
 * POV - DPAD (-1 when not pressed, 0-360.  0 is forward/up, 90 is right, 180 is back/down, 270 is left)
 * -----------------------
 * PS4 Controller:
 * 1 - Square
 * 2 - X
 * 3 - Circle (O)
 * 4 - Triangle
 * 5 - LB (left button/L1)
 * 6 - RB (right button/R1)
 * 7 - ???
 * 8 - ???
 * 9 - Share
 * 10 - Options
 * 11 - LS (left stick)
 * 12 - RS (right stick)
 * 13 - Playstation
 * POV - DPAD (-1 when not pressed, 0-360.  0 is forward/up, 90 is right, 180 is back/down, 270 is left)
 * 
 */
public enum UserInputDeviceButton
{
    // None - represents non-button:
    NONE(-1),

    // Joystick (Logitech Xtreme 3D Pro) constants:
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

    // XBox One controller constants:
    XBONE_A_BUTTON(1),
    XBONE_B_BUTTON(2),
    XBONE_X_BUTTON(3),
    XBONE_Y_BUTTON(4),
    XBONE_LEFT_BUTTON(5), // LB
    XBONE_RIGHT_BUTTON(6), // RB
    XBONE_SELECT_BUTTON(7), // aka "View", the one with the squares
    XBONE_START_BUTTON(8), // aka "Menu", the hamburgler one
    XBONE_LEFT_STICK_BUTTON(9), // LS
    XBONE_RIGHT_STICK_BUTTON(10), // RS

    // Playstation 4 controller constants:
    PS4_SQUARE_BUTTON(1),
    PS4_X_BUTTON(2),
    PS4_CIRCLE_BUTTON(3),
    PS4_TRIANGLE_BUTTON(4),
    PS4_LEFT_BUTTON(5), // LB
    PS4_RIGHT_BUTTON(6), // RB
    PS4_SHARE_BUTTON(9),
    PS4_OPTIONS_BUTTON(10),
    PS4_LEFT_STICK_BUTTON(11), // LS
    PS4_RIGHT_STICK_BUTTON(12), // RS
    PS4_PLAYSTATION_BUTTON(13),

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
    BUTTON_PAD_BUTTON_16(16),

    // Other - Some specified analog axis within a certain range
    POV(24),
    ANALOG_AXIS_RANGE(25);

    public final int Value;
    private UserInputDeviceButton(int value)
    {
        this.Value = value;
    }
}
