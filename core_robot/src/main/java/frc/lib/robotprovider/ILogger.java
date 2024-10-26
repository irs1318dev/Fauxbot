package frc.lib.robotprovider;

import frc.robot.LoggingKey;

/**
 * Logger interface, to describe what is required for logging data to some destination
 */
public interface ILogger
{
    /**
     * Write a boolean to the log
     * @param key to write to
     * @param value to write
     */
    void logBoolean(LoggingKey key, boolean value);

    /**
     * Write a boolean array to the log
     * @param key to write to
     * @param value to write
     */
    void logBooleanArray(LoggingKey key, boolean[] value);

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    void logNumber(LoggingKey key, double value);

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    void logNumber(LoggingKey key, Double value);

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    void logInteger(LoggingKey key, int value);

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    void logInteger(LoggingKey key, Integer value);

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    void logInteger(LoggingKey key, int value, String formatString);

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    void logString(LoggingKey key, String value);

    /**
     * Update the log, if appropriate..
     */
    void update();

    /**
     * Flush the output stream, if appropriate..
     */
    void flush();
}
