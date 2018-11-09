package frc.team1318.robot.common.robotprovider;

public interface IVisionPipeline
{
    /**
     * Processes the image input and sets the result objects.
     * Implementations should make these objects accessible.
     */
    void process(IMat image);
}