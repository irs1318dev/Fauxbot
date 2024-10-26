package frc.lib.robotprovider;

import frc.robot.LoggingKey;

/**
 * Class to make it easier to log to multiple ILoggers
 */
public class MultiLogger implements ILogger
{
    private final ILogger[] loggers;

    /**
     * Initializes a new instance of the MultiLogger class
     * @param loggers to log to
     */
    public MultiLogger(ILogger... loggers)
    {
        this.loggers = loggers;
    }

    /**
     * Write a boolean to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(LoggingKey key, boolean value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logBoolean(key, value);
        }
    }

    /**
     * Write a boolean array to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBooleanArray(LoggingKey key, boolean[] value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logBooleanArray(key, value);
        }
    }

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, double value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logNumber(key, value);
        }
    }

    /**
     * Write a number (Double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, Double value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logNumber(key, value);
        }
    }

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, int value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logInteger(key, value);
        }
    }

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, Integer value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logInteger(key, value);
        }
    }

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    @Override
    public void logInteger(LoggingKey key, int value, String formatString)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logInteger(key, value, formatString);
        }
    }

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(LoggingKey key, String value)
    {
        for (ILogger logger : this.loggers)
        {
            logger.logString(key, value);
        }
    }

    /**
     * Update the log, if appropriate..
     */
    @Override
    public void update()
    {
        for (ILogger logger : this.loggers)
        {
            logger.update();
        }
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
        for (ILogger logger : this.loggers)
        {
            logger.flush();
        }
    }
}
