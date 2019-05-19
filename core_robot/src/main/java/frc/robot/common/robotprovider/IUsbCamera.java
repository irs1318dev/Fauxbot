package frc.robot.common.robotprovider;

public interface IUsbCamera
{
    /**
     * Set the resolution.
     * @param width desired width
     * @param height desired height
     * @return True if set successfully
     */
    public boolean setResolution(int width, int height);

    /**
     * Set the exposure to auto aperture.
     */
    public void setExposureAuto();

    /**
     * Set the brightness, as a percentage (0-100).
     */
    public void setBrightness(int brightness);

    /**
     * Set the frames per second (FPS).
     * @param fps desired FPS
     * @return True if set successfully
     */
    public boolean setFPS(int fps);

    /**
     * Set the exposure to manual, as a percentage (0-100).
     */
    public void setExposureManual(int value);

    /**
     * create a vision thread using the listener and the pipeline
     * @param listener
     * @param pipeline
     * @return
     */
    public <T extends IVisionPipeline> Thread createVisionThread(IVisionListener<T> listener, T pipeline);
}