package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderWrapper implements IEncoder
{
    private final Encoder wrappedObject;

    public EncoderWrapper(int channelA, int channelB)
    {
        this.wrappedObject = new Encoder(channelA, channelB);
    }

    public double getRate()
    {
        return this.wrappedObject.getRate();
    }

    public double getDistance()
    {
        return this.wrappedObject.getDistance();
    }

    public int get()
    {
        return this.wrappedObject.get();
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        this.wrappedObject.setDistancePerPulse(distancePerPulse);
    }

    public void reset()
    {
        this.wrappedObject.reset();
    }
}
