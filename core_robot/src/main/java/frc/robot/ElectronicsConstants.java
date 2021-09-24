package frc.robot;

/**
 * All constants describing how the electronics are plugged together.
 * 
 * @author Will
 * 
 */
public class ElectronicsConstants
{
    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_XBONE_LEFT_X_AXIS = false;
    public static final boolean INVERT_XBONE_RIGHT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_XBONE_LEFT_Y_AXIS = true;
    public static final boolean INVERT_XBONE_RIGHT_Y_AXIS = true;

    // change INVERT_X_AXIS to true if positive on the joystick isn't to the right, and negative isn't to the left
    public static final boolean INVERT_PS4_LEFT_X_AXIS = false;
    public static final boolean INVERT_PS4_RIGHT_X_AXIS = false;

    // change INVERT_Y_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_PS4_LEFT_Y_AXIS = true;
    public static final boolean INVERT_PS4_RIGHT_Y_AXIS = true;

    // change INVERT_THROTTLE_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_THROTTLE_AXIS = true;

    // change INVERT_TRIGGER_AXIS to true if positive on the joystick isn't forward, and negative isn't backwards.
    public static final boolean INVERT_TRIGGER_AXIS = false;

    public static final int PCM_A_MODULE = 0; // Module A
    public static final int PCM_B_MODULE = 1; // Module B

    public static final int JOYSTICK_DRIVER_PORT = 0;
    public static final int JOYSTICK_CO_DRIVER_PORT = 1;

    //================================================== Vision ==============================================================

    public static final int VISION_RING_LIGHT_DIO = -1;

    //================================================== Indicator Lights ==============================================================

    public static final int INDICATOR_LIGHT_X_DIO = -1;

    //================================================== DriveTrain ==============================================================

    public static final int DRIVETRAIN_STEER_MOTOR_1_CAN_ID = 1;
    public static final int DRIVETRAIN_DRIVE_MOTOR_1_CAN_ID = 2;
    public static final int DRIVETRAIN_STEER_MOTOR_2_CAN_ID = 3;
    public static final int DRIVETRAIN_DRIVE_MOTOR_2_CAN_ID = 4;
    public static final int DRIVETRAIN_STEER_MOTOR_3_CAN_ID = 5;
    public static final int DRIVETRAIN_DRIVE_MOTOR_3_CAN_ID = 6;
    public static final int DRIVETRAIN_STEER_MOTOR_4_CAN_ID = 7;
    public static final int DRIVETRAIN_DRIVE_MOTOR_4_CAN_ID = 8;

    public static final int[] DRIVETRAIN_STEER_MOTOR_CAN_ID = new int[] { ElectronicsConstants.DRIVETRAIN_STEER_MOTOR_1_CAN_ID, ElectronicsConstants.DRIVETRAIN_STEER_MOTOR_2_CAN_ID, ElectronicsConstants.DRIVETRAIN_STEER_MOTOR_3_CAN_ID, ElectronicsConstants.DRIVETRAIN_STEER_MOTOR_4_CAN_ID};
    public static final int[] DRIVETRAIN_DRIVE_MOTOR_CAN_ID = new int[]{ ElectronicsConstants.DRIVETRAIN_DRIVE_MOTOR_1_CAN_ID, ElectronicsConstants.DRIVETRAIN_DRIVE_MOTOR_2_CAN_ID, ElectronicsConstants.DRIVETRAIN_DRIVE_MOTOR_3_CAN_ID, ElectronicsConstants.DRIVETRAIN_DRIVE_MOTOR_4_CAN_ID };

    public static final int DRIVETRAIN_ABSOLUTE_ENCODER_1_ANALOG_INPUT = 0;
    public static final int DRIVETRAIN_ABSOLUTE_ENCODER_2_ANALOG_INPUT = 1;
    public static final int DRIVETRAIN_ABSOLUTE_ENCODER_3_ANALOG_INPUT = 2;
    public static final int DRIVETRAIN_ABSOLUTE_ENCODER_4_ANALOG_INPUT = 3;

    public static final int[] DRIVETRAIN_ABSOLUTE_ENCODER_ANALOG_INPUT = new int[] { ElectronicsConstants.DRIVETRAIN_ABSOLUTE_ENCODER_1_ANALOG_INPUT, ElectronicsConstants.DRIVETRAIN_ABSOLUTE_ENCODER_2_ANALOG_INPUT, ElectronicsConstants.DRIVETRAIN_ABSOLUTE_ENCODER_3_ANALOG_INPUT, ElectronicsConstants.DRIVETRAIN_ABSOLUTE_ENCODER_4_ANALOG_INPUT };

    //================================================== Garage Door =======================================================

    public static final int GARAGEDOOR_MOTOR_PWM_CHANNEL = 0;

    public static final int GARAGEDOOR_THROUGHBEAMSENSOR_DIGITAL_CHANNEL = 0;
    public static final int GARAGEDOOR_OPENSENSOR_DIGITAL_CHANNEL = 1;
    public static final int GARAGEDOOR_CLOSEDSENSOR_DIGITAL_CHANNEL = 2;

    //================================================== Elevator ==========================================================

    public static final int ELEVATOR_MOTOR_PWM_CHANNEL = 0;

    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_A = 0;
    public static final int ELEVATOR_ENCODER_DIGITAL_CHANNEL_B = 1;

   //================================================== Forklift =======================================================

    public static final int FORKLIFT_DRIVE_LEFT_MOTOR_CAN_ID = 0;
    public static final int FORKLIFT_DRIVE_RIGHT_MOTOR_CAN_ID = 1; 

    public static final int FORKLIFT_LIFTER_FORWARD_PCM_CHANNEL = 7;
    public static final int FORKLIFT_LIFTER_BACKWARD_PCM_CHANNEL = 8;

    //================================================== Shooter =======================================================

    public static final int SHOOTER_ANGLE_MOTOR_CAN_ID = 0;
    public static final int SHOOTER_FLY_WHEEL_MOTOR_CAN_ID = 1;

    public static final int SHOOTER_KICKER_FORWARD_PCM_CHANNEL = 7;
    public static final int SHOOTER_KICKER_BACKWARD_PCM_CHANNEL = 8;

    //================================================== Printer =======================================================

    public static final int PRINTER_X_MOTOR_CAN_ID = 0;
    public static final int PRINTER_Y_MOTOR_CAN_ID = 1; 

    public static final int PRINTER_PEN_FORWARD_PCM_CHANNEL = 7;
    public static final int PRINTER_PEN_BACKWARD_PCM_CHANNEL = 8;
}
