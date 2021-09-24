package frc.robot.common.robotprovider;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;

public class NavxWrapper implements INavx
{
    private final AHRS wrappedObject;

    public NavxWrapper()
    {
        this.wrappedObject = new AHRS(Port.kMXP);
    }

    public boolean isConnected()
    {
        return this.wrappedObject.isConnected();
    }

    public double getAngle()
    {
        return this.wrappedObject.getAngle();
    }

    public double getPitch()
    {
        return this.wrappedObject.getPitch();
    }

    public double getRoll()
    {
        return this.wrappedObject.getRoll();
    }

    public double getYaw()
    {
        return this.wrappedObject.getYaw();
    }

    public double getDisplacementX()
    {
        return this.wrappedObject.getDisplacementX();
    }

    public double getDisplacementY()
    {
        return this.wrappedObject.getDisplacementY();
    }

    public double getDisplacementZ()
    {
        return this.wrappedObject.getDisplacementZ();
    }

    public void reset()
    {
        this.wrappedObject.reset();
    }

    public void resetDisplacement()
    {
        this.wrappedObject.resetDisplacement();
    }
}