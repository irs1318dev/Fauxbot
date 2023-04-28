package frc.robot;

/**
 * Keys describing logging 
 */
public enum LoggingKey
{
    RobotState("r.state", true),
    RobotTime("r.time", true),
    RobotMatch("r.match"),
    RobotCrash("r.crash", true),
    DriverMode("driver.mode"),
    DriverActiveMacros("driver.activeMacros", true),
    DriverActiveShifts("driver.activeShifts"),
    AutonomousSelection("auto.selected"),
    AutonomousDSMessage("auto.dsMessage");

    public final String value;
    public final boolean shouldLog;
    private LoggingKey(String value)
    {
        this(value, false);
    }

    private LoggingKey(String value, boolean shouldLog)
    {
        this.value = value;
        this.shouldLog = shouldLog;
    }
}
