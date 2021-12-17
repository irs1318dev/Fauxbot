package frc.robot;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = true;
    public static boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    //================================================== Magic Values ==============================================================

    public static final double MAGIC_NULL_VALUE = -1318.0;
    public static final double PERRY_THE_PLATYPUS = 0.0;

    //================================================== Logging  ==============================================================

    public static final int CALENDAR_YEAR = 2021;
    public static final boolean LOG_TO_FILE = TuningConstants.COMPETITION_ROBOT;
    public static final boolean LOG_FILE_ONLY_COMPETITION_MATCHES = true;
    public static final long LOG_FILE_REQUIRED_FREE_SPACE = 50 * 1024 * 1024; // require at least 50 MB of space
    public static final int LOG_FLUSH_THRESHOLD = 25;

    //================================================== Autonomous ==============================================================

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;

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
