package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper implements IJoystick
{
    private final Joystick wrappedObject;

    public JoystickWrapper(int port)
    {
        this.wrappedObject = new Joystick(port);
    }

    public double getAxis(int relevantAxis)
    {
        return this.wrappedObject.getRawAxis(relevantAxis);
    }

    public int getPOV()
    {
        return this.wrappedObject.getPOV();
    }

    public boolean getRawButton(int value)
    {
        return this.wrappedObject.getRawButton(value);
    }
}
