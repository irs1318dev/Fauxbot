package org.usfirst.frc.team1318.robot.common;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Logger that logs current values to a dashboard.
 *
 */
public class SmartDashboardLogger implements IDashboardLogger
{
    /**
     * Write a boolean to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(String component, String key, boolean value)
    {
        String logKey = String.format("%s.%s", component, key);
        if (SmartDashboard.getBoolean(logKey, !value) != value)
        {
            SmartDashboard.putBoolean(logKey, value);
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
        String logKey = String.format("%s.%s", component, key);
        if (SmartDashboard.getNumber(logKey, value + 0.5) != value)
        {
            SmartDashboard.putNumber(logKey, value);
        }
    }

    /**
     * Write a number (double) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(String component, String key, Double value)
    {
        String logKey = String.format("%s.%s", component, key);
        String valueString = "N/A";
        if (value != null)
        {
            valueString = "" + value;
        }

        SmartDashboard.putString(logKey, valueString);
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
        this.logInteger(component, key, value, null);
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
        String logKey = String.format("%s.%s", component, key);
        if (SmartDashboard.getNumber(logKey, value + 0.5) != value)
        {
            SmartDashboard.putNumber(logKey, value);
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
        String valueString = "N/A";
        if (value != null)
        {
            valueString = String.format("", value.x, value.y);
        }

        this.logString(component, key, valueString);
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
        String logKey = String.format("%s.%s", component, key);
        if (SmartDashboard.getString(logKey, null) != value)
        {
            SmartDashboard.putString(logKey, value);
        }
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
    }
}
