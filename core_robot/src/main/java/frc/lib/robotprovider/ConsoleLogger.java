package frc.lib.robotprovider;

import frc.robot.LoggingKey;

/**
 * Logger that logs current values to the console (standard output, shown in DriverStation).
 */
public class ConsoleLogger extends StringLogger
{
    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void internalLogString(LoggingKey key, String value)
    {
        System.out.println(key.value + ": " + value);
    }
}
