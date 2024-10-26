package frc.lib.robotprovider;

/**
 * Represents interactions with a Compressor plugged into a Pneumatics Control Module or Pneumatics Hub
 */
public interface ICompressor
{
    /**
     * Enables the compressor in analog-sensor mode (only for Pneumatics Hub)
     * @param minPressurePSI minimum PSI under which it should enable compressor
     * @param maxPressurePSI maximum PSI over which it should disable compressor
     */
    void enableAnalog(double minPressurePSI, double maxPressurePSI);

    /**
     * Enables the compressor in hybrid-sensor mode (only for Pneumatics Hub)
     * @param minPressurePSI minimum PSI under which it should enable compressor
     * @param maxPressurePSI maximum PSI over which it should disable compressor
     */
    void enableHybrid(double minPressurePSI, double maxPressurePSI);

    /**
     * Enables the compressor in digital-sensor mode
     */
    void enableDigital();

    /**
     * Query the analog sensor pressure.
     * @return The analog sensor pressure, in PSI
     */
    double getPressure();

    /**
     * Forcibly disable the compressor
     */
    void disable();
}
