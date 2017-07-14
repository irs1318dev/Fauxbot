package org.usfirst.frc.team1318.robot.common;

import org.opencv.core.Point;

public class MultiLogger implements IDashboardLogger
{
    private final IDashboardLogger[] loggers;

    /**
     * Initializes a new instance of the MultiLogger class
     * @param loggers to log to
     */
    public MultiLogger(IDashboardLogger... loggers)
    {
        this.loggers = loggers;
    }

    /**
     * Write a boolean to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(String component, String key, boolean value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logBoolean(component, key, value);
        }
    }

    /**
     * Write a number (double) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, double value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logNumber(component, key, value);
        }
    }

    /**
     * Write a number (Double) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, Double value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logNumber(component, key, value);
        }
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(String component, String key, int value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logInteger(component, key, value);
        }
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    @Override
    public void logInteger(String component, String key, int value, String formatString)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logInteger(component, key, value, formatString);
        }
    }

    /**
     * Write a point (x,y or N/A) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logPoint(String component, String key, Point value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logPoint(component, key, value);
        }
    }

    /**
     * Write a string to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(String component, String key, String value)
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.logString(component, key, value);
        }
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
        for (IDashboardLogger logger : this.loggers)
        {
            logger.flush();
        }
    }
}
