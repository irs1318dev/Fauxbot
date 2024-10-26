package frc.lib.robotprovider;

/**
 * Represents a DutyCycle that considers the pulses that have been seen on a Digital Input channel
 */
public interface IDutyCycle
{
    /**
     * Retrieve the ratio of high to low of the digital input signal
     * @return a value between 0 and 1 representing what percentage of the time is spent high vs low
     */
    double getOutput();

    /**
     * Retrieve the frequency of the duty cycle signal
     * @return the frequency in Hz
     */
    int getFrequency();
}
