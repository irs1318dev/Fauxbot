package frc.robot;

/**
 * Keys describing logging 
 */
public enum LoggingKey
{
    RobotState("r.state", 1, true),
    RobotTime("r.time", 1, true),
    RobotMatch("r.match", 50),
    RobotCrash("r.crash", true),
    DriverMode("driver.mode", 1, true),
    DriverActiveMacros("driver.activeMacros", true),
    DriverActiveShifts("driver.activeShifts"),
    AutonomousSelection("auto.selected"),
    AutonomousDSMessage("auto.dsMessage"),
    ShooterAngle("shooter.angle"),
    ShooterSpeed("shooter.speed");

    public final String value;
    public final int loggingFrequency;
    public final boolean shouldLogToCsv;
    private LoggingKey(String value)
    {
        this(value, TuningConstants.DEFAULT_LOGGING_FREQUENCY, false);
    }

    private LoggingKey(String value, int loggingFrequency)
    {
        this(value, loggingFrequency, false);
    }

    private LoggingKey(String value, boolean shouldLogToCsv)
    {
        this(value, TuningConstants.DEFAULT_LOGGING_FREQUENCY, shouldLogToCsv);
    }

    private LoggingKey(String value, int loggingFrequency, boolean shouldLogToCsv)
    {
        if (loggingFrequency <= 0)
        {
            loggingFrequency = TuningConstants.DEFAULT_LOGGING_FREQUENCY;
        }

        this.value = value;
        this.loggingFrequency = loggingFrequency;
        this.shouldLogToCsv = shouldLogToCsv;
    }
}
