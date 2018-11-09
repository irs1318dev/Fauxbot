package frc.team1318.robot;

import java.util.*;

import com.google.inject.Injector;

import frc.team1318.robot.common.*;
import frc.team1318.robot.mechanisms.*;

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
        mechanismList.add(injector.getInstance(ForkliftMechanism.class));
        //mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        //mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        return mechanismList;
    }

    //================================================== DriveTrain ==============================================================

    public static final double DEAD_ZONE = 0.1;
    public static final double DRIVETRAIN_MAX_POWER_LEVEL = 1.0; 
}
