package frc.robot.common;

import java.util.function.Function;

import com.google.inject.Injector;

import frc.robot.LoggingKey;
import frc.robot.common.robotprovider.ILogger;
import frc.robot.common.robotprovider.IPoint;

public class LoggingManager implements ILogger
{
    private final Function<Injector, ILogger> loggerCreator;

    private ILogger currentLogger;

    public LoggingManager(Function<Injector, ILogger> loggerCreator)
    {
        this.loggerCreator = loggerCreator;
    }

    /**
     * Refresh the current logger that is being used based on the logger creator function
     */
    public void refresh(Injector injector)
    {
        this.currentLogger = this.loggerCreator.apply(injector);
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
     * Write a number (Double) to the log
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
     * Write a point (x,y or N/A) to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logPoint(LoggingKey key, IPoint value)
    {
        this.currentLogger.logPoint(key, value);
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
