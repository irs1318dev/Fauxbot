package frc.robot;

import java.io.IOException;
import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.common.robotprovider.Alliance;
import frc.robot.common.robotprovider.CSVLogger;
import frc.robot.common.robotprovider.IDriverStation;
import frc.robot.common.robotprovider.IFile;
import frc.robot.common.robotprovider.ILogger;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.common.robotprovider.ISmartDashboardLogger;
import frc.robot.common.robotprovider.MatchType;
import frc.robot.common.robotprovider.MultiLogger;
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
    public static boolean THROW_EXCEPTIONS = !TuningConstants.COMPETITION_ROBOT;

    public static final int CALENDAR_YEAR = 2020;
    public static final boolean LOG_TO_FILE = true; //TuningConstants.COMPETITION_ROBOT;
    public static final boolean LOG_FILE_ONLY_COMPETITION_MATCHES = false; // true;
    public static final long LOG_FILE_REQUIRED_FREE_SPACE = 50 * 1024 * 1024; // require at least 50 MB of space
    public static final int LOG_FLUSH_THRESHOLD = 25;

    public static final double MAGIC_NULL_VALUE = -1318.0;

    public static List<IMechanism> GetActiveMechanisms(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<IMechanism>();
        // mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        // mechanismList.add(injector.getInstance(ForkliftMechanism.class));
        // mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        // mechanismList.add(injector.getInstance(PrinterMechanism.class));
        mechanismList.add(injector.getInstance(ShooterMechanism.class));
        // mechanismList.add(injector.getInstance(SomeMechanism.class));
        return mechanismList;
    }

    public static ILogger GetLogger(Injector injector)
    {
        ISmartDashboardLogger smartDashboardLogger = injector.getInstance(ISmartDashboardLogger.class);
        if (!TuningConstants.LOG_TO_FILE)
        {
            return smartDashboardLogger;
        }

        IRobotProvider robotProvider = injector.getInstance(IRobotProvider.class);
        IDriverStation driverStation = robotProvider.getDriverStation();
        MatchType matchType = driverStation.getMatchType();
        if (matchType == MatchType.None && TuningConstants.LOG_FILE_ONLY_COMPETITION_MATCHES)
        {
            return smartDashboardLogger;
        }

        // NI's Linux RTOS automatically mounts USB Sticks as /U/ and /V/, so long as they are formatted as FAT32
        // https://knowledge.ni.com/KnowledgeArticleDetails?id=kA00Z000000P7iaSAC&l=en-US
        // https://www.ni.com/en-us/support/documentation/supplemental/18/file-system-compatibility-with-the-ni-linux-real-time-os.html
        IFile rootDirectory = injector.getInstance(IFile.class);
        rootDirectory.open("/U/");
        if (!rootDirectory.exists() || rootDirectory.getFreeSpace() < TuningConstants.LOG_FILE_REQUIRED_FREE_SPACE)
        {
            return smartDashboardLogger;
        }

        String eventName = driverStation.getEventName();
        int matchNumber = driverStation.getMatchNumber();
        int replayNumber = driverStation.getReplayNumber();
        Alliance alliance = driverStation.getAlliance();
        int location = driverStation.getLocation();
        IFile file;
        if (eventName == null ||
            matchType == MatchType.None ||
            matchNumber == 0 ||
            alliance == Alliance.Invalid ||
            location <= 0 ||
            location >= 4)
        {
            if (TuningConstants.LOG_FILE_ONLY_COMPETITION_MATCHES)
            {
                // strange 
                return smartDashboardLogger;
            }
            else
            {
                // don't know important information, let's just put it under the root of the USB stick
                IFile directory = injector.getInstance(IFile.class);
                directory.open("/U/other/");
                directory.mkdir();

                file = injector.getInstance(IFile.class);
                file.open(String.format("/U/other/%1$d.csv", Calendar.getInstance().getTime().getTime()));
                if (file.exists())
                {
                    // file already exists
                    return smartDashboardLogger;
                }
            }
        }
        else
        {
            String directoryPath = String.format("/U/%1$d - %2$s/", TuningConstants.CALENDAR_YEAR, eventName);
            IFile directory = injector.getInstance(IFile.class);
            directory.open(directoryPath);
            directory.mkdir();

            // name the file a la "/U/2020 - Glacier Peak/Q03 (R2).auto.csv" or "/U/2020 - Glacier Peak/Q12R1 (B3).tele.csv"
            boolean isAuto = driverStation.isAutonomous();
            file = injector.getInstance(IFile.class);
            String fileName =
                String.format(
                    "%1$s%2$s%3$02d%4$s (%5$s%6$d).%7$s.csv",
                    directoryPath,
                    matchType.value,
                    matchNumber,
                    replayNumber == 0 ? "" : String.format("R%1$d", replayNumber),
                    alliance.value,
                    location,
                    isAuto ? "auto" : "tele");

            file.open(fileName);
            if (file.exists())
            {
                for (int i = 2; i <= 5; i++)
                {
                    // start adding .2, .3, etc. to the name, a la "/U/2020 - Glacier Peak/Q03 (R2).2.csv"
                    fileName =
                        String.format(
                            "%1$s%2$s%3$02d%4$s (%5$s%6$d).%7$s.%8$d.csv",
                            directoryPath,
                            matchType.value,
                            matchNumber,
                            replayNumber == 0 ? "" : String.format("R%1$d", replayNumber),
                            alliance.value,
                            location,
                            isAuto ? "auto" : "tele",
                            i);

                    file.open(fileName);
                    if (!file.exists())
                    {
                        break;
                    }
                }
            }
        }

        try
        {
            CSVLogger csvLogger = new CSVLogger(file.openWriter());
            return new MultiLogger(csvLogger, smartDashboardLogger);
        }
        catch (IOException ex)
        {
            return smartDashboardLogger;
        }
    }

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
