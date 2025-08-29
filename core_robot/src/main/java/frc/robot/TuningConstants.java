package frc.robot;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All constants related to tuning the operation of the robot.
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = false;
    public static final boolean USE_ADVANTAGE_KIT = true;
    public static final boolean READ_SENSORS_WHILE_DISABLED = true;
    public static final boolean LOG_NULL_WHILE_DISABLED = true && !TuningConstants.READ_SENSORS_WHILE_DISABLED;
    public static final boolean RETREIVE_PDH_FIRST = true;
    public static final boolean TRACER_ENABLED = false;

    public static boolean THROW_EXCEPTIONS = true;
    public static boolean LOG_EXCEPTIONS = true;
    public static double LOOP_DURATION = 0.02; // we expect the robot's main loop to run at roughly ~50 Hz, or 1 update per 20ms (0.02s)
    public static int LOOPS_PER_SECOND = 50; // we expect the robot's main loop to run at roughly ~50 Hz, or 1 update per 20ms (0.02s)

    public static final boolean EXPECT_UNUSED_JOYSTICKS = true;
    public static final boolean PERFORM_COSTLY_TASKS_WHILE_DISABLED = true;

    //================================================== Magic Values ==============================================================

    public static final double MAGIC_NULL_VALUE = -1318.0;
    public static final double ZERO = 0.0;
    public static final double ENDGAME_START_TIME = 30.0;
    public static final double ENDGAME_CLIMB_TIME = 5.0;

    //================================================== Logging  ==============================================================

    public static final int CALENDAR_YEAR = 2025;
    public static final boolean LOG_TO_FILE = false; // TuningConstants.COMPETITION_ROBOT;
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
