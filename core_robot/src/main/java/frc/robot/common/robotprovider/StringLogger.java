package frc.robot.common.robotprovider;

import frc.robot.LoggingKey;

public abstract class StringLogger implements ILogger
{
    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    public abstract void logString(LoggingKey key, String value);

    /**
     * Write a boolean to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(LoggingKey key, boolean value)
    {
        this.logString(key, String.valueOf(value));
    }

    /**
     * Write a boolean array to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBooleanArray(LoggingKey key, boolean[] value)
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

        this.logString(key, str);
    }

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, double value)
    {
        this.logString(key, String.valueOf(value));
    }

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, Double value)
    {
        this.logString(key, String.valueOf(value));
    }

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, int value)
    {
        this.logString(key, String.valueOf(value));
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
        this.logString(key, String.format(formatString, value));
    }

    /**
     * Write a point (x,y or N/A) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logPoint(LoggingKey key, IPoint value)
    {
        String valueString = "N/A";
        if (value != null)
        {
            valueString = String.format("(%f, %f)", value.getX(), value.getY());
        }

        this.logString(key, valueString);
    }

    /**
     * Update the log, if appropriate..
     */
    @Override
    public void update()
    {
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
    }
}
