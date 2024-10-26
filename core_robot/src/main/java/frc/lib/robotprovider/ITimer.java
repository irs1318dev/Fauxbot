package frc.lib.robotprovider;

/**
 * Represents a timer to keep track of how much time has elapsed
 */
public interface ITimer
{
    /**
     * Starts the timer running, if it is not currently
     */
    void start();

    /**
     * Stops the timer
     */
    void stop();

    /**
     * Gets the amount of time that has elapsed between when the timer was started and either now (if running) or when the timer was stopped
     * @return elapsed time in seconds
     */
    double get();

    /**
     * resets the time elapsed to 0
     */
    void reset();
}
