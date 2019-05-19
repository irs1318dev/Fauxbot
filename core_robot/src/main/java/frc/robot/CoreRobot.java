package frc.robot;

import frc.robot.common.MechanismManager;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.ITimer;
import frc.robot.driver.common.Driver;

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
    // smartdash logging constants
    private static final String LogName = "r";

    private final T module;

    // Driver - used both for autonomous and teleop mode.
    private Driver driver;

    // Mechanisms and injector
    private MechanismManager mechanisms;
    private IDashboardLogger logger;
    private Injector injector;

    private ITimer timer;
    private boolean timerStarted;

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
        this.mechanisms = this.getInjector().getInstance(MechanismManager.class);
        this.logger = this.getInjector().getInstance(IDashboardLogger.class);
        this.logger.logString(CoreRobot.LogName, "state", "Init");

        this.timer = this.getInjector().getInstance(ITimer.class);
        this.logger.logNumber(CoreRobot.LogName, "time", this.timer.get());
        this.timerStarted = false;

        // create driver
        this.driver = this.getInjector().getInstance(Driver.class);
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

        this.logger.logString(CoreRobot.LogName, "state", "Disabled");
    }

    /**
     * Initialization code for autonomous mode should go here.
     * This code will be called each time the robot enters autonomous mode.
     */
    public void autonomousInit()
    {
        this.driver.startAutonomous();

        this.generalInit();

        // log that we are in autonomous mode
        this.logger.logString(CoreRobot.LogName, "state", "Autonomous");
    }

    /**
     * Initialization code for teleop mode should go here.
     * This code will be called each time the robot enters teleop mode.
     */
    public void teleopInit()
    {
        this.generalInit();

        // log that we are in teleop mode
        this.logger.logString(CoreRobot.LogName, "state", "Teleop");
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
        // apply the driver to the mechanisms
        this.mechanisms.setDriver(this.driver);

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

        this.logger.logNumber(CoreRobot.LogName, "time", this.timer.get());
        this.logger.flush();
    }
}
