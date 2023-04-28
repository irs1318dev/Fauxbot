package frc.lib.driver.descriptions;

/**
 * The various input devices currently supported by the robot
 *
 */
public enum UserInputDevice
{
    None(-1),
    Driver(0),
    Codriver(1),
    Test1(2),
    Test2(3),
    MaxCount(4);

    private final int joystickId;
    private UserInputDevice(int joystickId)
    {
        this.joystickId = joystickId;
    }

    public int getId()
    {
        return this.joystickId;
    }
}
