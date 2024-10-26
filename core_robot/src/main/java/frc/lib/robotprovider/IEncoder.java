package frc.lib.robotprovider;

/**
 * Represents a quadrature encoder that determines distance based on pulses sent to two digital input channels
 */
public interface IEncoder
{
    /**
     * Retreives the current velocity (distance/second) of the encoder
     * @return the current velocity of the encoder
     */
    double getRate();

    /**
     * Retrieve the distance (ticks * distance/pulse)
     * @return distance in configured units
     */
    double getDistance();

    /**
     * Retrieve the number of ticks since startup or last reset
     * @return number of ticks
     */
    int get();

    /**
     * Configure the distance per pulse to use for distance calculation
     * @param distancePerPulse multiplier, in some units
     */
    void setDistancePerPulse(double distancePerPulse);

    /**
     * Reset the current value to 0
     */
    void reset();
}
