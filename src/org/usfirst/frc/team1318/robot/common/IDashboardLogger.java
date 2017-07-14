package org.usfirst.frc.team1318.robot.common;

import org.opencv.core.Point;

public interface IDashboardLogger
{
    /**
     * Write a boolean to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logBoolean(String component, String key, boolean value);

    /**
     * Write a number (double) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logNumber(String component, String key, double value);

    /**
     * Write a number (double) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logNumber(String component, String key, Double value);

    /**
     * Write a number (integer) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logInteger(String component, String key, int value);

    /**
     * Write a number (integer) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    void logInteger(String component, String key, int value, String formatString);

    /**
     * Write a point (x,y or N/A) to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logPoint(String component, String key, Point value);

    /**
     * Write a string to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    void logString(String component, String key, String value);

    /**
     * Flush the output stream, if appropriate..
     */
    void flush();
}
