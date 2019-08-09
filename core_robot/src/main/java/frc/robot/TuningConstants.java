package frc.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.mechanisms.GarageDoorMechanism;

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
        mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        //mechanismList.add(injector.getInstance(SomeMechanism.class));
        return mechanismList;
    }

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;
    public static final double DEAD_ZONE = 0.1;

    //================================================== Forklift ==============================================================

    //================================================== Garage Door ==============================================================

    public static final double GARAGE_DOOR_OPENING_POWER = 1.0;
    public static final double GARAGE_DOOR_CLOSING_POWER = -1.0;

    //================================================== Elevator ==============================================================

    //================================================== Shooter ==============================================================

    //================================================== Printer ==============================================================
}
