package frc.robot.common.robotprovider;

import org.opencv.core.Mat;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;

public class UsbCameraWrapper implements IUsbCamera
{
    private final UsbCamera wrappedObject;

    public UsbCameraWrapper(String name, int dev)
    {
        this.wrappedObject = new UsbCamera(name, dev);
    } 

    public boolean setResolution(int width, int height)
    {
        return this.wrappedObject.setResolution(width, height);
    }

    public void setExposureAuto()
    {
        this.wrappedObject.setExposureAuto();
    }

    public void setBrightness(int brightness)
    {
        this.wrappedObject.setBrightness(brightness);
    }

    public boolean setFPS(int fps)
    {
        return this.wrappedObject.setFPS(fps);
    }

    public void setExposureManual(int value)
    {
        this.wrappedObject.setExposureManual(value);
    }

    public <T extends IVisionPipeline> Thread createVisionThread(IVisionListener<T> listener, T pipeline)
    {
        return new VisionThread(this.wrappedObject, new VisionPipelineWrapper<T>(pipeline), new VisionListenerWrapper<T>(listener));
    }

    private class VisionPipelineWrapper<T extends IVisionPipeline> implements VisionPipeline
    {
        private final T wrappedObject;

        public VisionPipelineWrapper(T visionPipeline)
        {
            this.wrappedObject = visionPipeline;
        }

        public void process(Mat image)
        {
            this.wrappedObject.process(new MatWrapper(image));
        }
    }

    private class VisionListenerWrapper<T extends IVisionPipeline> implements VisionRunner.Listener<VisionPipeline>
    {
        private final IVisionListener<T> wrappedObject;

        public VisionListenerWrapper(IVisionListener<T> visionListener)
        {
            this.wrappedObject = visionListener;
        }

        @SuppressWarnings(value="unchecked")
        public void copyPipelineOutputs(VisionPipeline pipeline)
        {
            this.wrappedObject.copyPipelineOutputs(((VisionPipelineWrapper<T>)pipeline).wrappedObject);
        }
    }
}