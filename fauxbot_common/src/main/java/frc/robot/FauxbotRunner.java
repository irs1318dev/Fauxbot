package frc.robot;

import java.util.Calendar;

import frc.lib.CoreRobot;
import frc.lib.robotprovider.RobotMode;

public class FauxbotRunner implements Runnable
{
    private final Object locker;
    private final CoreRobot<FauxbotCommonModule> robot;

    private final IRefresh fauxbot;

    private boolean stop;
    private RobotMode mode;

    public FauxbotRunner(CoreRobot<FauxbotCommonModule> robot)
    {
        this(robot, null);
    }

    public FauxbotRunner(CoreRobot<FauxbotCommonModule> robot, IRefresh fauxbot)
    {
        this.locker = new Object();
        this.robot = robot;
        this.fauxbot = fauxbot;
        this.stop = false;
        this.mode = RobotMode.Disabled;
    }

    @Override
    public void run()
    {
        RobotMode currentMode = RobotMode.Disabled;

        boolean shouldStop;
        do
        {
            RobotMode newMode;
            synchronized (this.locker)
            {
                newMode = this.mode;
            }

            long startTime = Calendar.getInstance().getTime().getTime();
            if (currentMode != newMode)
            {
                switch (newMode)
                {
                    case Disabled:
                        this.robot.disabledInit();
                        break;

                    case Autonomous:
                        this.robot.autonomousInit();
                        break;

                    case Simulation:
                        this.robot.simulationInit();

                    case Teleop:
                        this.robot.teleopInit();
                        break;

                    case Test:
                        this.robot.testInit();
                        break;
                }

                currentMode = newMode;
            }

            switch (currentMode)
            {
                case Disabled:
                    this.robot.disabledPeriodic();
                    break;

                case Autonomous:
                    this.robot.autonomousPeriodic();
                    break;

                case Simulation:
                    this.robot.simulationPeriodic();

                case Teleop:
                    this.robot.teleopPeriodic();
                    break;

                case Test:
                    this.robot.testPeriodic();
                    break;
            }

            if (this.fauxbot != null)
            {
                this.fauxbot.refresh();
            }

            long endTime = Calendar.getInstance().getTime().getTime();
            long elapsed = endTime - startTime;
            if (elapsed <= 20)
            {
                try
                {
                    Thread.sleep(20 - elapsed);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("20ms loop time exceeded (" + elapsed + ")");
            }

            synchronized (this.locker)
            {
                shouldStop = this.stop;
            }
        }
        while (!shouldStop);
    }

    public void setMode(RobotMode newMode)
    {
        synchronized (this.locker)
        {
            this.mode = newMode;
        }
    }

    public void stop()
    {
        synchronized (this.locker)
        {
            this.stop = true;
        }
    }
}
