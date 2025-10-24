package frc.lib.robotprovider;

/**
 * Represents an encoder based on DutyCycle that considers the pulses that have been seen on a Digital Input channel
 */
public interface IDutyCycleEncoder
{
    /**
     * Retrieve the number of rotations since startup or last reset
     * @return number of rotations
     */
    double get();

    /**
     * Retrieve the distance (rotations * distance/rotation)
     * @return distance in configured units
     */
    double getDistance();

    /**
     * Retrieve the absolute position
     * @return the absolute position, not accounting for rollovers
     */
    double getAbsolutePosition();

    /**
     * Retrieve the frequency of the duty cycle
     * @return the frequency in Hz
     */
    int getFrequency();

    /**
     * Checks whether the sensor is connected, based on the configured threshold
     * @return true if the encoder is connected, otherwise false
     */
    boolean isConnected();

    /**
     * Configure the frequency threshold below which we will consider the encoder disconnected
     * @param frequency threshold to use
     */
    void setConnectedFrequencyThreshold(int frequency);

    /**
     * Configure the distance per rotation to use for distance calculation
     * @param distancePerRotation multiplier, in some units
     */
    void setDistancePerRotation(double distancePerRotation);

    /**
     * Configure the duty cycle range to expect from the encoder
     * @param min duty cycle (0 to 1)
     * @param max duty cycle (0 to 1)
     */
    void setDutyCycleRange(double min, double max);

    /**
     * Whether to invert the value of the DutyCycle Encoder.
     * @param inverted whether to swap the value from the default.
     */
    void setInverted(boolean inverted);

    /**
     * Configure the position offset of the encoder
     * @param offset to use (0 to 1)
     */
    void setPositionOffset(double offset);

    /**
     * Reset the current value to 0
     */
    void reset();
}