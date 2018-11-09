package frc.team1318.robot.common.robotprovider;

public class FauxbotNavx implements INavx
{
    public FauxbotNavx()
    {
    }

    public boolean isConnected()
    {
        return true;
    }

    public double getAngle()
    {
        return 0.0;
    }

    public double getDisplacementX()
    {
        return 0.0;
    }

    public double getDisplacementY()
    {
        return 0.0;
    }

    public double getDisplacementZ()
    {
        return 0.0;
    }

    public void reset()
    {
    }

    public void resetDisplacement()
    {
    }
}