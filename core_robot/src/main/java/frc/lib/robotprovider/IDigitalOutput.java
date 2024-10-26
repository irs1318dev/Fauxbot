package frc.lib.robotprovider;

/**
 * Represents a simple Digital Output channel
 */
public interface IDigitalOutput
{
    /**
     * Applies the provided value to the digital output channel
     * @param value true sets it on (V+), false sets it off (Gnd)
     */
    void set(boolean value);
}
