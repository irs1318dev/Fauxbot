package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Servo;

public class ServoWrapper implements IServo
{
    private final Servo wrappedObject;

    public ServoWrapper(int channel)
    {
        this.wrappedObject = new Servo(channel);
    }

    public void set(double value)
    {
        this.wrappedObject.set(value);
    }
}
