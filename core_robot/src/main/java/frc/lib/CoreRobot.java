package frc.lib;

import frc.lib.driver.IDriver;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.mechanisms.LoggingManager;
import frc.lib.mechanisms.MechanismManager;
import frc.lib.robotprovider.*;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;

import java.util.Calendar;
import java.util.Optional;
import java.util.OptionalInt;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class for the FRC Competition Robot
 * Robot for IRS1318
 * 
 * General design comments:
 * We have the following primary types of objects dealt with here:
 * - Driver - describes the driver/codriver of the robot ("autonomous" or "user")
 * - Mechanisms - define the logic that controls a mechanism given inputs/outputs.
 * - Logger - defines what should be logged and to where (dashboard, etc.).
 * 
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

    private RobotMode currentMode;
    private int loggerUpdates;

    private int disabledCount;
    private boolean completedCostlyTasks;

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

        this.completedCostlyTasks = false;
    }

    /**
     * Initialization code for disabled mode should go here.
     * This code will be called each time the robot enters disabled mode.
     */
    public void disabledInit()
    {
        this.currentMode = RobotMode.Disabled;

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
        this.disabledCount = 0;
    }

    /**
     * Initialization code for autonomous mode should go here.
     * This code will be called each time the robot enters autonomous mode.
     */
    public void autonomousInit()
    {
        this.generalInit(RobotMode.Autonomous);

    }

    /**
     * Initialization code for teleop mode should go here.
     * This code will be called each time the robot enters teleop mode.
     */
    public void teleopInit()
    {
        this.generalInit(RobotMode.Teleop);
    }

    /**
     * Initialization code for test mode should go here.
     * This code will be called each time the robot enters test mode.
     */
    public void testInit()
    {
        this.generalInit(RobotMode.Test);
    }

    /**
     * Initialization code for simulation mode should go here.
     * This code will be called each time the robot enters simulation mode.
     */
    public void simulationInit()
    {
        this.generalInit(RobotMode.Simulation);
    }

    /**
     * Periodic code for disabled mode should go here.
     * This code will be called periodically at a regular rate while the robot is in disabled mode.
     */
    public void disabledPeriodic()
    {
        if (!TuningConstants.LOG_NULL_WHILE_DISABLED)
        {
            return;
        }

        this.disabledCount++;
        if ((this.disabledCount % 500) == 0)
        {
            if (!this.completedCostlyTasks)
            {
                if (TuningConstants.PERFORM_COSTLY_TASKS_WHILE_DISABLED)
                {
                    // perform costly tasks here
                }

                this.completedCostlyTasks = true;
            }

            this.disabledCount = 0;
        }

        for (LoggingKey key : LoggingKey.values())
        {
            if (key == LoggingKey.RobotState)
            {
                this.logger.logString(LoggingKey.RobotState, "Disabled");
            }

            switch (key.type)
            {
                case Boolean:
                    this.logger.logBoolean(key, false);
                    break;

                case Integer:
                    this.logger.logInteger(key, 0);
                    break;

                case NullableInteger:
                    this.logger.logInteger(key, null);
                    break;

                case Number:
                    this.logger.logNumber(key, 0.0);
                    break;

                case NullableNumber:
                    this.logger.logNumber(key, null);
                    break;

                case String:
                    this.logger.logString(key, "");
                    break;

                default:
                    // skip
                    break;
            }
        }

        this.logger.update();
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
     * Periodic code for simulation mode should go here.
     * This code will be called periodically at a regular rate while the robot is in simulation mode.
     */
    public void simulationPeriodic()
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
    private void generalInit(RobotMode robotMode)
    {
        try
        {
            this.currentMode = robotMode;
            this.driver.startMode(robotMode);

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

            // log our current mode
            this.logger.logString(LoggingKey.RobotState, robotMode.toString());
        }
        catch (RuntimeException ex)
        {
            if (TuningConstants.LOG_EXCEPTIONS)
            {
                this.logger.logString(LoggingKey.RobotCrash, ExceptionHelpers.exceptionString(ex));
            }

            throw ex;
        }
    }

    /**
     * General periodic code for teleop/autonomous mode should go here.
     */
    private void generalPeriodic()
    {
        try
        {
            this.mechanisms.readSensors();

            this.driver.update();

            // run each mechanism
            this.mechanisms.update(this.currentMode);

            this.logger.logNumber(LoggingKey.RobotTime, this.timer.get());
            this.logger.update();

            if (this.loggerUpdates++ > TuningConstants.LOG_FLUSH_THRESHOLD)
            {
                // lazily flush the log, in case of power-off.
                this.logger.flush();
                this.loggerUpdates = 0;
            }
        }
        catch (RuntimeException ex)
        {
            if (TuningConstants.LOG_EXCEPTIONS)
            {
                this.logger.logString(LoggingKey.RobotCrash, ExceptionHelpers.exceptionString(ex));
            }

            throw ex;
        }
    }

    private String generateMatchString(IDriverStation driverStation)
    {
        String eventName = driverStation.getEventName();
        MatchType matchType = driverStation.getMatchType();
        int matchNumber = driverStation.getMatchNumber();
        int replayNumber = driverStation.getReplayNumber();
        Optional<Alliance> alliance = driverStation.getAlliance();
        OptionalInt location = driverStation.getLocation();
        RobotMode mode = driverStation.getMode();

        if (eventName != null && matchType != MatchType.None && matchNumber > 0 && alliance.isPresent() && location.isPresent())
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
                    alliance.get().value,
                    location.getAsInt(),
                    mode.toString().toLowerCase());
        }

        return String.format("%1$d.csv", Calendar.getInstance().getTime().getTime());
    }
}
