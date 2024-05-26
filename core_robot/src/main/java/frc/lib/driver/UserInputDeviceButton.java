package frc.lib.driver;

/**
 * All constants that describe how the name of each button on the joystick maps to its button number.
 * 
 * Note: as of 2022 (at least), only 16 buttons are supported by WPILIB/DriverStation because it sends button presses
 * over the network using a short bit-map (16-bit number with 1s when pressed, 0s when not pressed).
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
 * Razer Tartarus button pad
 * 1 - Button 01
 * 2 - Button 02
 * 3 - Button 03
 * 4 - Button 04
 * 5 - Button 05
 * 6 - Button 06
 * 7 - Button 07
 * 8 - Button 08
 * 9 - Button 09
 * 10 - Button 10
 * 11 - Button 11
 * 12 - Button 12
 * 13 - Button 13
 * 14 - Button 14
 * 15 - Button 15
 * 16 - Button 16
 * POV - Not supported... DPAD on the Tartarus is used for X/Y axes (up/right positive y/x, respectively).
 * -----------------------
 * XBox One Controller (and Logitech F310):
 * 1 - A (1)
 * 2 - B (2)
 * 3 - X (4)
 * 4 - Y (8)
 * 5 - LB (16,  left button)
 * 6 - RB (32, right button)
 * 7 - Select (64, two squares)
 * 8 - Start (128, hamburger)
 * 9 - LS (256, left stick)
 * 10 - RS (512, right stick)
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

    // XBox One controller constants:
    XBONE_A_BUTTON(1), // 1
    XBONE_B_BUTTON(2), // 2
    XBONE_X_BUTTON(3), // 4
    XBONE_Y_BUTTON(4), // 8
    XBONE_LEFT_BUTTON(5), // 16 (LB)
    XBONE_RIGHT_BUTTON(6), // 32 (RB)
    XBONE_SELECT_BUTTON(7), // 64 (aka "View", the one with the squares)
    XBONE_START_BUTTON(8), // 128 (aka "Menu", the hamburgler one)
    XBONE_LEFT_STICK_BUTTON(9), // 256 (LS)
    XBONE_RIGHT_STICK_BUTTON(10), // 512 (RS)

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

    // Other - Some specified analog axis within a certain range
    POV(24),
    ANALOG_AXIS_RANGE(25);

    public final int Value;
    private UserInputDeviceButton(int value)
    {
        this.Value = value;
    }
}
