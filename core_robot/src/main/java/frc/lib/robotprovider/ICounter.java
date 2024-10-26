package frc.lib.robotprovider;

/**
 * Represents a Counter that counts the number of pulses that have been seen on a Digital Input channel
 */
public interface ICounter
{
    /**
     * Retrieves the current number of pulses that have been read by the counter since it was created (or last reset)
     * @return number of pulses read
     */
    int get();

    /**
     * Resets the counter to zero (0).
     */
    void reset();
}
