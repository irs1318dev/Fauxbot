package frc.robot.common.robotprovider;

public interface ICompressor
{
    /**
     * Enables the compressor in analog-sensor mode (only for Pneumatics Hub)
     * @param minPressurePSI minimum PSI under which it should enable compressor
     * @param maxPressurePSI maximum PSI over which it should disable compressor
     */
    void enableAnalog(double minPressurePSI, double maxPressurePSI);

    /**
     * Enables the compressor in digital-sensor mode
     */
    void enableDigital();

    /**
     * Forcibly disable the compressor
     */
    void disable();
}
