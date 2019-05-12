package frc.robot.common.robotprovider;

public interface IVisionListener<P extends IVisionPipeline>
{
    void copyPipelineOutputs(P pipeline);
}