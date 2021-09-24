package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Robot wraps CoreRobot to allow for the basic autonomous/teleop and switching logic to be shared between
 * Robot and Fauxbot (and whatever may come after).
 * 
 * The FRC Robot VM is designed to call the functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package, you must also update the Main class that
 * references it.
 */
public class Robot extends TimedRobot
{
    private final CoreRobot<RobotModule> robot;

    public Robot()
    {
        this.robot = new CoreRobot<RobotModule>(new RobotModule());
    }

    /**
     * Robot-wide initialization code should go here.
     * This default Robot-wide initialization code will be called when 
     * the robot is first powered on.  It will be called exactly 1 time.
     */
    @Override
    public void robotInit()
    {
        this.robot.robotInit();
    }

    /**
     * Initialization code for disabled mode should go here.
     * This code will be called each time the robot enters disabled mode.
     */
    @Override
    public void disabledInit()
    {
        this.robot.disabledInit();
    }

    /**
     * Initialization code for autonomous mode should go here.
     * This code will be called each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        this.robot.autonomousInit();
    }

    /**
     * Initialization code for teleop mode should go here.
     * This code will be called each time the robot enters teleop mode.
     */
    @Override
    public void teleopInit()
    {
        this.robot.teleopInit();
    }

    /**
     * Initialization code for test mode should go here.
     * This code will be called each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        this.robot.testInit();
    }

    /**
     * Periodic code for disabled mode should go here.
     * This code will be called periodically at a regular rate while the robot is in disabled mode.
     */
    @Override
    public void disabledPeriodic()
    {
        this.robot.disabledPeriodic();
    }

    /**
     * Periodic code for autonomous mode should go here.
     * This code will be called periodically at a regular rate while the robot is in autonomous mode.
     */
    @Override
    public void autonomousPeriodic()
    {
        this.robot.autonomousPeriodic();
    }

    /**
     * Periodic code for teleop mode should go here.
     * This code will be called periodically at a regular rate while the robot is in teleop mode.
     */
    @Override
    public void teleopPeriodic()
    {
        this.robot.teleopPeriodic();
    }

    /**
     * Periodic code for test mode should go here.
     * This code will be called periodically at a regular rate while the robot is in test mode.
     */
    @Override
    public void testPeriodic()
    {
        this.robot.testPeriodic();
    }
}
