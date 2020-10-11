package frc.robot.common.robotprovider;

import javax.inject.Singleton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LoggingKey;

/**
 * Logger that logs current values to a dashboard.
 *
 */
@Singleton
public class SmartDashboardLogger implements ISmartDashboardLogger
{
    /**
     * Write a boolean to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(LoggingKey key, boolean value)
    {
        if (SmartDashboard.getBoolean(key.value, !value) != value)
        {
            SmartDashboard.putBoolean(key.value, value);
        }
    }

    /**
     * Write a boolean array to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBooleanArray(LoggingKey key, boolean[] value)
    {
        SmartDashboard.putBooleanArray(key.value, value);
    }

    /**
     * Write a number (double) to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, double value)
    {
        if (SmartDashboard.getNumber(key.value, value + 0.5) != value)
        {
            SmartDashboard.putNumber(key.value, value);
        }
    }

    /**
     * Write a number (double) to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, Double value)
    {
        String valueString = "N/A";
        if (value != null)
        {
            valueString = "" + value;
        }

        SmartDashboard.putString(key.value, valueString);
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, int value)
    {
        this.logInteger(key, value, null);
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    @Override
    public void logInteger(LoggingKey key, int value, String formatString)
    {
        if (SmartDashboard.getNumber(key.value, value + 0.5) != value)
        {
            SmartDashboard.putNumber(key.value, value);
        }
    }

    /**
     * Write a point (x,y or N/A) to the smart dashboard
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
     * Write a string to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(LoggingKey key, String value)
    {
        if (SmartDashboard.getString(key.value, null) != value)
        {
            SmartDashboard.putString(key.value, value);
        }
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
