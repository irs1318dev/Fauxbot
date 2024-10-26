package frc.lib.robotprovider;

/**
 * Represents a simple Digital Input channel
 */
public interface IDigitalInput
{
    /**
     * Retrieve the current value of the Digital Input
     * @return true when the signal is on (V+), false when the signal is off (Gnd)
     */
    boolean get();
}
