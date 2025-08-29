package frc.lib.driver;

import frc.lib.robotprovider.*;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.robot.driver.*;

/**
 * Interface describing the Driver that operates the robot.  This is either autonomous or teleop/user driver in the real world, a mock for unit tests, or a fake for Fauxbot.
 *
 */
public interface IDriver extends IOperationRetriever
{
    /**
     * Checks whether the driver is in autonomous mode
     */
    public RobotMode getMode();

    /**
     * Tell the driver that some time has passed
     */
    public void update();

    /**
     * Tell the driver that robot operation is stopping
     */
    public void stop();

    /**
     * Prepares the autonomous routine
     */
    public void prepAutoMode();

    /**
     * Starts the autonomous period of the match (e.g. begins auto routine)
     * @param mode that is starting
     */
    public void startMode(RobotMode mode);

    /**
     * Instructs the joystick to rumble (if supported)
     * @param device device to attempt to rumble
     * @param type whether left or right rumbler
     * @param value between 0.0 for no rumble and 1.0 for full rumble
     */
    public void setRumble(UserInputDevice device, JoystickRumbleType type, double value);

    /**
     * Updates the driver to be in the specified context.
     * @param context to apply
     */
    public void setContext(OperationContext context);
}
