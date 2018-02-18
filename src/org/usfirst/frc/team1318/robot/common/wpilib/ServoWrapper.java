package org.usfirst.frc.team1318.robot.common.wpilib;

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
