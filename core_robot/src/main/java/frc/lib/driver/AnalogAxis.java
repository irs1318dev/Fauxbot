package frc.lib.driver;

/**
 * All constants that describe how the name of each axis on the joystick maps to its axis number.
 * 
 * All values are continuous -1.0 to 1.0, unless stated otherwise**
 * 
 * Axis guide:
 * -----------------------
 * Logitech Xtreme 3D Pro:
 * 0 - X (stick, X)
 * 1 - Y (stick, Y)
 * 2 - Twist (stick, twist)
 * 3 - Throttle: -1 to 1
 * -----------------------
 * XBox One Controller (and Logitech F310):
 * 0 - LS_X (left stick, X)
 * 1 - LS_Y (left stick, Y)
 * 2 - LT (left trigger): 0 to 1
 * 3 - RT (right trigger): 0 to 1
 * 4 - RS_X (right stick, X)
 * 5 - RS_Y (right stick, Y)
 * -----------------------
 * PS4 Controller:
 * 0 - LS_X (left stick, X)
 * 1 - LS_Y (left stick, Y)
 * 2 - RS_X (right stick, X)
 * 3 - LT (left trigger, L2): -1 to 1
 * 4 - RT (right trigger, R2): -1 to 1
 * 5 - RS_Y (right stick, Y)
 */
public enum AnalogAxis
{
    NONE(-1),

    // Logitech Xtreme 3D Pro joystick axes
    JOYSTICK_X(0),
    JOYSTICK_Y(1),
    JOYSTICK_TWIST(2),
    JOYSTICK_THROTTLE(3),

    // Xbox One controller axes
    XBONE_LSX(0),
    XBONE_LSY(1),
    XBONE_LT(2),
    XBONE_RT(3),
    XBONE_RSX(4),
    XBONE_RSY(5),

    // PlayStation 4 controller axes
    PS4_LSX(0),
    PS4_LSY(1),
    PS4_LT(3),
    PS4_RT(4),
    PS4_RSX(2),
    PS4_RSY(5);

    /**
     * The WPILIB "value" of the given axis
     */
    public final int Value;
    private AnalogAxis(int value)
    {
        this.Value = value;
    }
}