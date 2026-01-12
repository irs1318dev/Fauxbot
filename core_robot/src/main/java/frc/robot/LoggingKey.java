package frc.robot;

import frc.lib.robotprovider.LoggingType;

/**
 * Keys describing logging
 */
public enum LoggingKey
{
    RobotState("r/state", LoggingType.String, false, 1, true),
    RobotTime("r/time", LoggingType.Number, false, 1, true),
    RobotCrash("r/crash", LoggingType.String, false, true),
    DriverMode("driver/mode", LoggingType.String, false, 1, true),
    DriverActiveMacros("driver/activeMacros", LoggingType.String, false, 1, true),
    DriverActiveShifts("driver/activeShifts", LoggingType.String, false),
    DriverActiveContext("driver/activeContext", LoggingType.String, false),
    AutonomousSelection("auto/selected", LoggingType.String, false),
    AutonomousDSMessage("auto/dsMessage", LoggingType.String, false),
    ShooterAngle("shooter.angle", LoggingType.Number, false),
    ShooterSpeed("shooter.speed", LoggingType.Number, false);

    public final String value;
    public final LoggingType type;
    public final boolean isInput;
    public final int loggingFrequency;
    public final boolean shouldLogToCsv;

    private LoggingKey(String value, LoggingType type)
    {
        this(value, type, false, TuningConstants.DEFAULT_LOGGING_FREQUENCY, false);
    }

    private LoggingKey(String value, LoggingType type, boolean isInput)
    {
        this(value, type, isInput, TuningConstants.DEFAULT_LOGGING_FREQUENCY, false);
    }

    private LoggingKey(String value, LoggingType type, boolean isInput, int loggingFrequency)
    {
        this(value, type, isInput, loggingFrequency, false);
    }

    private LoggingKey(String value, LoggingType type, boolean isInput, boolean shouldLogToCsv)
    {
        this(value, type, isInput, TuningConstants.DEFAULT_LOGGING_FREQUENCY, shouldLogToCsv);
    }

    private LoggingKey(String value, LoggingType type, boolean isInput, int loggingFrequency, boolean shouldLogToCsv)
    {
        if (loggingFrequency <= 0)
        {
            loggingFrequency = TuningConstants.DEFAULT_LOGGING_FREQUENCY;
        }

        this.value = value;
        this.type = type;
        this.isInput = isInput;
        this.loggingFrequency = loggingFrequency;
        this.shouldLogToCsv = shouldLogToCsv;
    }
}
