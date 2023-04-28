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

    //================================================== Autonomous ==============================================================

    public static final boolean TRAJECTORY_FORCE_BUILD = false;

    //================================================== Forklift ==============================================================

    public static final double FORKLIFT_DRIVE_DEAD_ZONE = 0.05;

    //================================================== Garage Door ==============================================================

    public static final double GARAGEDOOR_OPENING_POWER = 1.0;
    public static final double GARAGEDOOR_CLOSING_POWER = -1.0;

    //================================================== Elevator ==============================================================

    public static double ELEVATOR_MOTOR_KP = 1.0;
    public static double ELEVATOR_MOTOR_KI = 0.0;
    public static double ELEVATOR_MOTOR_KD = 0.1;
    public static double ELEVATOR_MOTOR_KF = 0.0;
    public static double ELEVATOR_MOTOR_KS = 1.0;
    public static double ELEVATOR_MOTOR_MIN_POWER = -1.0;
    public static double ELEVATOR_MOTOR_MAX_POWER = 1.0;

    //================================================== Shooter ==============================================================

    public static final double SHOOTER_SPIN_SPEED = 200.0;

    public static final double SHOOTER_ANGLE_MOTOR_KP = 0.5;
    public static final double SHOOTER_ANGLE_MOTOR_KI = 0.0;
    public static final double SHOOTER_ANGLE_MOTOR_KD = 0.0;
    public static final double SHOOTER_ANGLE_MOTOR_KF = 0.0;

    public static final double SHOOTER_FLY_WHEEL_MOTOR_KP = 0.2;
    public static final double SHOOTER_FLY_WHEEL_MOTOR_KI = 0.0;
    public static final double SHOOTER_FLY_WHEEL_MOTOR_KD = 0.0;
    public static final double SHOOTER_FLY_WHEEL_MOTOR_KF = 0.0025;

    public static final double SHOOTER_ANGLE_DEAD_ZONE = 0.05;

    //================================================== Printer ==============================================================

    public static final double PRINTER_X_MOTOR_KP = 0.01;
    public static final double PRINTER_X_MOTOR_KI = 0.0;
    public static final double PRINTER_X_MOTOR_KD = 0.0;
    public static final double PRINTER_X_MOTOR_KF = 0.0;

    public static final double PRINTER_Y_MOTOR_KP = 0.01;
    public static final double PRINTER_Y_MOTOR_KI = 0.0;
    public static final double PRINTER_Y_MOTOR_KD = 0.0;
    public static final double PRINTER_Y_MOTOR_KF = 0.0;
}
