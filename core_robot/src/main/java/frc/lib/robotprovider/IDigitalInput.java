package frc.lib.robotprovider;

/**
 * Represents a simple Digital Input channel
 */
public interface IDigitalInput
{
    /**
     * Retrieve the current value of the Digital Input
     * @return true when the signal is on (V+), false when the signal is off (Gnd), opposite when inverted.
     */
    boolean get();

    /**
     * Whether to invert the value of the DigitalInput.
     * @param inverted whether to swap the value from the default.
     */
    void setInverted(boolean inverted);
}
