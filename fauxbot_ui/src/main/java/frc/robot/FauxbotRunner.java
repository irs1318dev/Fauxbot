package frc.robot;

import frc.robot.common.robotprovider.RobotMode;

public class FauxbotRunner implements Runnable
{
    private final Object locker;
    private final CoreRobot<FauxbotModule> robot;
    private final FauxbotApplication fauxbot;

    private boolean stop;
    private RobotMode mode;

    public FauxbotRunner(CoreRobot<FauxbotModule> robot, FauxbotApplication fauxbot)
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
        while (!this.stop)
        {
            RobotMode newMode;
            synchronized (this.locker)
            {
                newMode = this.mode;
            }

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

                case Teleop:
                    this.robot.teleopPeriodic();
                    break;

                case Test:
                    this.robot.testPeriodic();
                    break;
            }

            this.fauxbot.refresh();

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
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
        this.stop = true;
    }
}
