package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickWrapper implements IJoystick
{
    private final Joystick wrappedObject;

    public JoystickWrapper(int port)
    {
        this.wrappedObject = new Joystick(port);
    }

    @SuppressWarnings("deprecation")
    public double getAxis(AnalogAxis relevantAxis)
    {
        switch (relevantAxis)
        {
            case X:
                return this.wrappedObject.getAxis(AxisType.kX);
            case Y:
                return this.wrappedObject.getAxis(AxisType.kY);
            case Z:
                return this.wrappedObject.getAxis(AxisType.kZ);
            case Twist:
                return this.wrappedObject.getAxis(AxisType.kTwist);
            case Throttle:
                return this.wrappedObject.getAxis(AxisType.kThrottle);

            case None:
            default:
                return 0.0;
        }
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
