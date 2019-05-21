package frc.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;

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
<<<<<<< HEAD
        //mechanismList.add(injector.getInstance(SomeMechanism.class));
=======
        //mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        //mechanismList.add(injector.getInstance(ForkliftMechanism.class));
        //mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        mechanismList.add(injector.getInstance(ShooterMechanism.class));
        //mechanismList.add(injector.getInstance(PrinterMechanism.class));
>>>>>>> ReferenceImplementation
        return mechanismList;
    }

    public static final boolean CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE = true;
    public static final double DEAD_ZONE = 0.1;

    //================================================== Forklift ==============================================================

    //================================================== Garage Door ==============================================================

    //================================================== Elevator ==============================================================

    //================================================== Shooter ==============================================================

    //================================================== Printer ==============================================================
}
