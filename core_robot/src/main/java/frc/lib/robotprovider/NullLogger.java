package frc.lib.robotprovider;

import frc.robot.LoggingKey;

/**
 * Logger that skips logging.
 */
public class NullLogger extends StringLogger
{
    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void internalLogString(LoggingKey key, String value)
    {
    }
}
