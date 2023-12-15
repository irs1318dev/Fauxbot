package frc.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = true;
    public static boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;
    public static boolean LOG_EXCEPTIONS = true;
    public static double LOOP_DURATION = 0.02; // we expect the robot's main loop to run at roughly ~50 Hz, or 1 update per 20ms (0.02s)
    public static int LOOPS_PER_SECOND = 50; // we expect the robot's main loop to run at roughly ~50 Hz, or 1 update per 20ms (0.02s)

    public static final boolean EXPECT_UNUSED_JOYSTICKS = true;

    //================================================== Magic Values ==============================================================

    public static final double MAGIC_NULL_VALUE = -1318.0;
    public static final double ZERO = 0.0;
    public static final double ENDGAME_START_TIME = 30.0;
    public static final double ENDGAME_CLIMB_TIME = 5.0;

    //================================================== Logging  ==============================================================

    public static final int CALENDAR_YEAR = 2023;
    public static final boolean LOG_TO_FILE = true; // TuningConstants.COMPETITION_ROBOT;
    public static final boolean LOG_FILE_ONLY_COMPETITION_MATCHES = false;
    public static final long LOG_FILE_REQUIRED_FREE_SPACE = 50 * 1024 * 1024; // require at least 50 MB of space
    public static final int LOG_FLUSH_THRESHOLD = 25;
    public static final boolean USE_LOGGING_FREQUENCY = true; // TuningConstants.COMPETITION_ROBOT;
    public static final int DEFAULT_LOGGING_FREQUENCY = 10; // number of entries to ignore between logging

    //================================================== Autonomous ==============================================================

    public static final boolean TRAJECTORY_FORCE_BUILD = false;

    //================================================== Forklift ==============================================================

    //================================================== Garage Door ==============================================================

    //================================================== Elevator ==============================================================

    //================================================== Shooter ==============================================================

    //================================================== Printer ==============================================================
}
