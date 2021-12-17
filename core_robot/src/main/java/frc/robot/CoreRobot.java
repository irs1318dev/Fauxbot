package frc.robot;

import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.IDriver;

import java.util.Calendar;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class for the FRC ? [competition name] Competition
 * Robot for IRS1318 - [robot name]
 * 
 * General design comments:
 * We have the following primary types of objects dealt with here:
 * - Driver - describes the driver/operator of the robot ("autonomous" or "user")
 * - Mechanisms - define the logic that controls a mechanism given inputs/outputs.
 * - Logger - defines what should be logged and to where (dashboard, etc.).
 * 
 * @author Will
 */
public class CoreRobot<T extends AbstractModule>
{
    private final T module;

    // Driver - used both for autonomous and teleop mode.
    private IDriver driver;

    // Mechanisms and injector
    private MechanismManager mechanisms;
    private LoggingManager logger;
    private Injector injector;

    private ITimer timer;
    private boolean timerStarted;

    private int loggerUpdates;

    public CoreRobot(T module)
    {
        this.module = module;
    }

    /**
     * Robot-wide initialization code should go here.
     * This default Robot-wide initialization code will be called when
     * the robot is first powered on.  It will be called exactly 1 time.
     */
    public void robotInit()
    {
        // create mechanisms
        Injector injector = this.getInjector();

        IRobotProvider robotProvider = injector.getInstance(IRobotProvider.class);
        IPreferences preferences = robotProvider.getPreferences();

        //SettingsManager.initAndUpdatePreferences(preferences, TuningConstants.class);
        //SettingsManager.initAndUpdatePreferences(preferences, HardwareConstants.class);
        //SettingsManager.initAndUpdatePreferences(preferences, ElectronicsConstants.class);
        //SettingsManager.initAndUpdatePreferences(preferences, VisionConstants.class);

        // create driver
        this.driver = injector.getInstance(IDriver.class);

        this.mechanisms = injector.getInstance(MechanismManager.class);
        this.logger = injector.getInstance(LoggingManager.class);
        this.logger.refresh(injector);

        this.logger.logString(LoggingKey.RobotState, "Init");

        this.timer = injector.getInstance(ITimer.class);
        this.logger.logNumber(LoggingKey.RobotTime, this.timer.get());
        this.timerStarted = false;

        // reset number of logger updates
        this.loggerUpdates = 0;
    }

    /**
     * Initialization code for disabled mode should go here.
     * This code will be called each time the robot enters disabled mode.
     */
    public void disabledInit()
    {
        this.timer.stop();
        this.timer.reset();
        this.timerStarted = false;

        if (this.driver != null)
        {
            this.driver.stop();
        }

        if (this.mechanisms != null)
        {
            this.mechanisms.stop();
        }

        this.logger.logString(LoggingKey.RobotState, "Disabled");
        this.logger.update();
        this.logger.flush();
    }

    /**
     * Initialization code for autonomous mode should go here.
     * This code will be called each time the robot enters autonomous mode.
     */
    public void autonomousInit()
    {
        this.driver.startMode(RobotMode.Autonomous);

        this.generalInit();

        // log that we are in autonomous mode
        this.logger.logString(LoggingKey.RobotState, "Autonomous");
    }

    /**
     * Initialization code for teleop mode should go here.
     * This code will be called each time the robot enters teleop mode.
     */
    public void teleopInit()
    {
        this.driver.startMode(RobotMode.Teleop);

        this.generalInit();

        // log that we are in teleop mode
        this.logger.logString(LoggingKey.RobotState, "Teleop");
    }

    /**
     * Initialization code for test mode should go here.
     * This code will be called each time the robot enters test mode.
     */
    public void testInit()
    {
        this.driver.startMode(RobotMode.Test);

        this.generalInit();

        // log that we are in test mode
        this.logger.logString(LoggingKey.RobotState, "Test");
    }

    /**
     * Periodic code for disabled mode should go here.
     * This code will be called periodically at a regular rate while the robot is in disabled mode.
     */
    public void disabledPeriodic()
    {
    }

    /**
     * Periodic code for autonomous mode should go here.
     * This code will be called periodically at a regular rate while the robot is in autonomous mode.
     */
    public void autonomousPeriodic()
    {
        this.generalPeriodic();
    }

    /**
     * Periodic code for teleop mode should go here.
     * This code will be called periodically at a regular rate while the robot is in teleop mode.
     */
    public void teleopPeriodic()
    {
        this.generalPeriodic();
    }

    /**
     * Periodic code for test mode should go here.
     * This code will be called periodically at a regular rate while the robot is in test mode.
     */
    public void testPeriodic()
    {
        this.generalPeriodic();
    }

    /**
     * Lazily initializes and retrieves the injector.
     * @return the injector to use for this robot
     */
    public Injector getInjector()
    {
        if (this.injector == null)
        {
            this.injector = Guice.createInjector(this.module);
        }

        return this.injector;
    }

    /**
     * General initialization code for teleop/autonomous mode should go here.
     */
    private void generalInit()
    {
        Injector injector = this.getInjector();
        this.logger.refresh(injector);

        // log match information
        IRobotProvider robotProvider = injector.getInstance(IRobotProvider.class);
        this.logger.logString(LoggingKey.RobotMatch, this.generateMatchString(robotProvider.getDriverStation()));

        if (!this.timerStarted)
        {
            this.timer.start();
            this.timerStarted = true;
        }
    }

    /**
     * General periodic code for teleop/autonomous mode should go here.
     */
    private void generalPeriodic()
    {
        this.mechanisms.readSensors();

        this.driver.update();

        // run each mechanism
        this.mechanisms.update();

        this.logger.logNumber(LoggingKey.RobotTime, this.timer.get());
        this.logger.update();

        if (this.loggerUpdates++ > TuningConstants.LOG_FLUSH_THRESHOLD)
        {
            // lazily flush the log, in case of power-off.
            this.logger.flush();
        }
    }

    private String generateMatchString(IDriverStation driverStation)
    {
        String eventName = driverStation.getEventName();
        MatchType matchType = driverStation.getMatchType();
        int matchNumber = driverStation.getMatchNumber();
        int replayNumber = driverStation.getReplayNumber();
        Alliance alliance = driverStation.getAlliance();
        int location = driverStation.getLocation();
        RobotMode mode = driverStation.getMode();

        if (eventName != null && matchType != MatchType.None && matchNumber > 0 && alliance != Alliance.Invalid && location >= 1 && location <= 3)
        {
            // a la "2020 Glacier Peak - Q03 (R2).autonomous"
            return
                String.format(
                    "%1$d %2$s - %3$s%4$02d%5$s (%6$s%7$d).%8$s",
                    TuningConstants.CALENDAR_YEAR,
                    eventName,
                    matchType.value,
                    matchNumber,
                    replayNumber == 0 ? "" : String.format("R%1$d", replayNumber),
                    alliance.value,
                    location,
                    mode.toString().toLowerCase());
        }

        return String.format("%1$d.csv", Calendar.getInstance().getTime().getTime());
    }
}
