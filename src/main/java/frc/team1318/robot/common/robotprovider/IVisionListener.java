package frc.team1318.robot.common.robotprovider;

public interface IVisionListener<P extends IVisionPipeline>
{
    void copyPipelineOutputs(P pipeline);
}