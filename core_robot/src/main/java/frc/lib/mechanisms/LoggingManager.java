package frc.lib.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import frc.robot.LoggingKey;
import frc.robot.SettingsManager;
import frc.lib.robotprovider.*;

/**
 * Logging manager class, to help log data to Shuffleboard/SmartDashboard/AdvantageKit/etc.
 */
@Singleton
public class LoggingManager implements ILogger
{
    private ILogger currentLogger;

    /**
     * Initializes a new instance of the LoggingManager class.
     * Guice constructor.
     */
    @Inject
    public LoggingManager()
    {
    }

    /**
     * Initializes a new instance of the LoggingManager class.
     * Unit-test constructor
     * @param logger to use
     */
    public LoggingManager(ILogger logger)
    {
        this.currentLogger = logger;
    }

    /**
     * Refresh the current logger that is being used based on the logger creator function
     * @param injector to use to get the appropriate logger
     */
    public void refresh(Injector injector)
    {
        this.currentLogger = SettingsManager.getLogger(injector);
    }

    /**
     * Write a boolean to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBoolean(LoggingKey key, boolean value)
    {
        this.currentLogger.logBoolean(key, value);
    }

    /**
     * Write a boolean array to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logBooleanArray(LoggingKey key, boolean[] value)
    {
        this.currentLogger.logBooleanArray(key, value);
    }

    /**
     * Write a number (double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, double value)
    {
        this.currentLogger.logNumber(key, value);
    }

    /**
     * Write a number (nullable Double) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logNumber(LoggingKey key, Double value)
    {
        this.currentLogger.logNumber(key, value);
    }

    /**
     * Write a number (integer) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, int value)
    {
        this.currentLogger.logInteger(key, value);
    }

    /**
     * Write a number (nullable Integer) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logInteger(LoggingKey key, Integer value)
    {
        this.currentLogger.logInteger(key, value);
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
        this.currentLogger.logInteger(key, value, formatString);
    }

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(LoggingKey key, String value)
    {
        this.currentLogger.logString(key, value);
    }

    /**
     * Update the log, if appropriate..
     */
    @Override
    public void update()
    {
        this.currentLogger.update();
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
        this.currentLogger.flush();
    }
}
