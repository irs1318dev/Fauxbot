package frc.robot;

/**
 * All constants describing the physical structure of the robot (distances and sizes of things).
 * 
 * @author Will
 * 
 */
public class HardwareConstants
{
    public static final boolean ROBOT_EXISTS = true;
    //================================================= Vision ======================================================

    // Vision Alignment 
    public static final double CAMERA_PITCH = 22.5; // in degrees
    public static final double CAMERA_X_OFFSET = 0.0; // in inches
    public static final double CAMERA_Z_OFFSET = 18.0; // in inches
    public static final double VISIONTARGET_Z_OFFSET = 90.25; // in inches
    public static final double CAMERA_TO_TARGET_Z_OFFSET = HardwareConstants.VISIONTARGET_Z_OFFSET - HardwareConstants.CAMERA_Z_OFFSET;
    public static final double CAMERA_YAW = 0.0; // in degrees

    //================================================== DriveTrain ==============================================================

    public static final double DRIVETRAIN_ENCODER_DEGREES_PER_VOLT = -72.0;

    public static final boolean[] DRIVETRAIN_STEER_MOTOR_INVERT_OUTPUT = new boolean[] { false, false, false, false };
    public static final boolean[] DRIVETRAIN_STEER_MOTOR_INVERT_SENSOR = new boolean[] { false, false, false, false };
    public static final boolean[] DRIVETRAIN_DRIVE_MOTOR_INVERT_OUTPUT = new boolean[] { false, false, false, false };
    public static final boolean[] DRIVETRAIN_DRIVE_MOTOR_INVERT_SENSOR = new boolean[] { false, false, false, false };

    //public static final double[] DRIVETRAIN_STEER_MOTOR_ABSOLUTE_OFFSET = new double[] { -101.0, 68.0, -101.0, -65.0 };
    //public static final double[] DRIVETRAIN_STEER_MOTOR_ABSOLUTE_OFFSET = new double[] { -136.0, 66.0, -94.0, -80.0 };
    public static final double[] DRIVETRAIN_STEER_MOTOR_ABSOLUTE_OFFSET = new double[] { -135.0, 65.0, -96.0, -14.0 };

    public static final double DRIVETRAIN_STEER_ENCODER_PULSES_PER_REVOLUTION = 2048.0;
    public static final double DRIVETRAIN_STEER_GEAR_RATIO = 18.0; // set correctly
    public static final double DRIVETRAIN_STEER_DEGREES = 360.0;
    public static final double DRIVETRAIN_STEER_PULSE_DISTANCE = HardwareConstants.DRIVETRAIN_STEER_DEGREES / (HardwareConstants.DRIVETRAIN_STEER_GEAR_RATIO * HardwareConstants.DRIVETRAIN_STEER_ENCODER_PULSES_PER_REVOLUTION);
    public static final double DRIVETRAIN_STEER_TICKS_PER_DEGREE = (HardwareConstants.DRIVETRAIN_STEER_GEAR_RATIO * HardwareConstants.DRIVETRAIN_STEER_ENCODER_PULSES_PER_REVOLUTION) / HardwareConstants.DRIVETRAIN_STEER_DEGREES;

    public static final double DRIVETRAIN_DRIVE_ENCODER_PULSES_PER_REVOLUTION = 2048.0;
    public static final double DRIVETRAIN_DRIVE_GEAR_RATIO = 8.95; // confirmed
    public static final double DRIVETRAIN_DRIVE_WHEEL_DIAMETER = 4.0; // (in inches)
    public static final double DRIVETRAIN_DRIVE_WHEEL_CIRCUMFERENCE = Math.PI * HardwareConstants.DRIVETRAIN_DRIVE_WHEEL_DIAMETER;
    public static final double DRIVETRAIN_DRIVE_PULSE_DISTANCE = HardwareConstants.DRIVETRAIN_DRIVE_WHEEL_CIRCUMFERENCE / (HardwareConstants.DRIVETRAIN_DRIVE_GEAR_RATIO * HardwareConstants.DRIVETRAIN_DRIVE_ENCODER_PULSES_PER_REVOLUTION);
    public static final double DRIVETRAIN_DRIVE_TICKS_PER_INCH = (HardwareConstants.DRIVETRAIN_DRIVE_GEAR_RATIO * HardwareConstants.DRIVETRAIN_DRIVE_ENCODER_PULSES_PER_REVOLUTION) / HardwareConstants.DRIVETRAIN_DRIVE_WHEEL_CIRCUMFERENCE;
    public static final double DRIVETRAIN_DRIVE_MOTOR_VELOCITY_TO_INCHES_PER_SECOND = 10.0 * HardwareConstants.DRIVETRAIN_DRIVE_PULSE_DISTANCE; // converts #ticks per 100ms into inches per second.
    public static final double DRIVETRAIN_DRIVE_INCHES_PER_SECOND_TO_MOTOR_VELOCITY = 0.1 * HardwareConstants.DRIVETRAIN_DRIVE_TICKS_PER_INCH; // converts inches per second into #ticks per 100ms.

    public static final double DRIVETRAIN_HORIZONTAL_WHEEL_SEPERATION_DISTANCE = 21.5; // (in inches)
    public static final double DRIVETRAIN_VERTICAL_WHEEL_SEPERATION_DISTANCE = 25.5; // (in inches)
    public static final double DRIVETRAIN_HORIZONTAL_WHEEL_CENTER_DISTANCE = HardwareConstants.DRIVETRAIN_HORIZONTAL_WHEEL_SEPERATION_DISTANCE / 2.0; // (in inches)
    public static final double DRIVETRAIN_VERTICAL_WHEEL_CENTER_DISTANCE = HardwareConstants.DRIVETRAIN_VERTICAL_WHEEL_SEPERATION_DISTANCE / 2.0; // (in inches)
}