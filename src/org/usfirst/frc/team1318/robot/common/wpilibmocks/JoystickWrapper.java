package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickWrapper implements IJoystick
{
    private final Joystick wrappedObject;

    public JoystickWrapper(int port)
    {
        this.wrappedObject = new Joystick(port);
    }

    public double getAxis(AxisType relevantAxis)
    {
        return this.wrappedObject.getAxis(relevantAxis);
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
