package frc.robot.common.robotprovider;

public class FauxbotUsbCamera implements IUsbCamera
{
    public FauxbotUsbCamera()
    {
    }

    public boolean setResolution(int width, int height)
    {
        return true;
    }

    public void setExposureAuto()
    {
    }

    public void setBrightness(int brightness)
    {
    }

    public boolean setFPS(int fps)
    {
        return true;
    }

    public void setExposureManual(int value)
    {
    }

    public <T extends IVisionPipeline> Thread createVisionThread(IVisionListener<T> listener, T pipeline)
    {
        return new Thread();
    }
}