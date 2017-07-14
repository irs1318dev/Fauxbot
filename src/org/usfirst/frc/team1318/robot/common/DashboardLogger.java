package org.usfirst.frc.team1318.robot.common;

/**
 * Logger that logs current values to a dashboard.
 *
 */
public class DashboardLogger
{
    private enum DashboardMode
    {
        Console;
    }
    
    /**
     * Default instance
     */
    private static DashboardMode defaultMode = DashboardMode.Console;
    
    /**
     * Write a boolean to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    public static void putBoolean(String key, boolean value)
    {
        if (DashboardLogger.defaultMode == DashboardMode.Console)
        {
            System.out.println(key + ": " + value);
        }
        else
        {
            
        }
    }

    /**
     * Write a number (double) to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    public static void putDouble(String key, double value)
    {
        if (DashboardLogger.defaultMode == DashboardMode.Console)
        {
            System.out.println(key + ": " + value);
        }
        else
        {
            
        }
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    public static void putInteger(String key, int value)
    {
        DashboardLogger.putInteger(key, value, null);
    }

    /**
     * Write a number (integer) to the smart dashboard
     * @param key to write to
     * @param value to write
     * @param formatString to use
     */
    public static void putInteger(String key, int value, String formatString)
    {
        if (DashboardLogger.defaultMode == DashboardMode.Console)
        {
            String valueOutput;
            if (formatString != null && !formatString.isEmpty())
            {
                valueOutput = String.format(formatString, value);
            }
            else
            {
                valueOutput = Integer.toString(value); 
            }
            
            System.out.println(key + ": " + valueOutput);
        }
        else
        {
            
        }
    }

    /**
     * Write a string to the smart dashboard
     * @param key to write to
     * @param value to write
     */
    public static void putString(String key, String value)
    {
        if (DashboardLogger.defaultMode == DashboardMode.Console)
        {
            System.out.println(key + ": " + value);
        }
        else
        {
            
        }
    }

    /**
     * Get a boolean from the smart dashboard
     * @param key to retrieve
     * @return value from smart dashboard
     */
    public static boolean getBoolean(String key)
    {
        return false;
    }

    /**
      * Get a number (double) from the smart dashboard
      * @param key to retrieve
      * @return value from smart dashboard
      */
    public static double getNumber(String key)
    {
        return 0.0;
    }

    /**
     * Get a string from the smart dashboard
     * @param key to retrieve
     * @return value from smart dashboard
     */
    public static String getString(String key)
    {
        return null;
    }
}
