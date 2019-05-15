package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Victor;

public class VictorWrapper implements IMotor
{
    private final Victor wrappedObject;

    public VictorWrapper(int channel)
    {
        this.wrappedObject = new Victor(channel);
    }

    public void set(double power)
    {
        this.wrappedObject.set(power);
    }
}
