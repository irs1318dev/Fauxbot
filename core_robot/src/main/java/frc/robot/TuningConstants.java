package frc.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.mechanisms.*;

/**
 * All constants related to tuning the operation of the robot.
 * 
 * @author Will
 * 
 */
public class TuningConstants
{
    public static final boolean COMPETITION_ROBOT = false;
    public static final boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    public static List<IMechanism> GetActiveMechanisms(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<IMechanism>();
        //mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        //mechanismList.add(injector.getInstance(ForkliftMechanism.class));
        //mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        mechanismList.add(injector.getInstance(ShooterMechanism.class));
        //mechanismList.add(injector.getInstance(PrinterMechanism.class));
        return mechanismList;
    }

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;
    public static final double DEAD_ZONE = 0.1;

    //================================================== Forklift ==============================================================

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

    public static final double SHOOTER_SPIN_SPEED = 100.0;

	public static final double SHOOTER_ANGLE_MOTOR_KP = 0.5;
	public static final double SHOOTER_ANGLE_MOTOR_KI = 0.0;
	public static final double SHOOTER_ANGLE_MOTOR_KD = 0.0;
    public static final double SHOOTER_ANGLE_MOTOR_KF = 0.0;

	public static final double SHOOTER_FLY_WHEEL_MOTOR_KP = 0.1;
	public static final double SHOOTER_FLY_WHEEL_MOTOR_KI = 0.0;
	public static final double SHOOTER_FLY_WHEEL_MOTOR_KD = 0.0;
    public static final double SHOOTER_FLY_WHEEL_MOTOR_KF = 0.005;

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
