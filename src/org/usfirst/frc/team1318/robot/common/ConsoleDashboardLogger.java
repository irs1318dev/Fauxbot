package org.usfirst.frc.team1318.robot.common;

/**
 * Logger that logs current values to a dashboard.
 *
 */
public class ConsoleDashboardLogger extends StringLogger implements IDashboardLogger
{
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
        System.out.println(logKey + ": " + value);
    }
}
