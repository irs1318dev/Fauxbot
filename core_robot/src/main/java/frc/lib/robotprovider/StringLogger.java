package frc.lib.robotprovider;

import frc.robot.LoggingKey;

/**
 * String Logger abstract class to make it easier to write loggers that log strings
 */
public abstract class StringLogger implements ILogger
{
    private int loggingCounter;

    protected StringLogger()
    {
        this.loggingCounter = 0;
    }

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    public void logString(LoggingKey key, String value)
    {
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, value);
        }
    }

    /**
     * Write a boolean to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(LoggingKey key, boolean value)
    {
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.valueOf(value));
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
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            String str = "";
            if (value != null)
            {
                for (int i = 0; i < value.length; i++)
                {
                    if (i > 0)
                    {
                        str += ",";
                    }

                    str += String.valueOf(value[i]);
                }
            }

            this.internalLogString(key, str);
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
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.valueOf(value));
        }
    }

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, Double value)
    {
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.valueOf(value));
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
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.valueOf(value));
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
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.valueOf(value));
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
        if ((this.loggingCounter % key.loggingFrequency) == 0)
        {
            this.internalLogString(key, String.format(formatString, value));
        }
    }

    /**
     * Update the log, if appropriate..
     */
    @Override
    public void update()
    {
        this.loggingCounter++;
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
    }

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    protected abstract void internalLogString(LoggingKey key, String value);
}
