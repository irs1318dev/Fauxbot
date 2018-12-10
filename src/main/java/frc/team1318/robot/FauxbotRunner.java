package frc.team1318.robot;

import frc.team1318.robot.common.MechanismManager;
import frc.team1318.robot.driver.common.Driver;

public class FauxbotRunner implements Runnable
{
    private final MechanismManager mechanisms;
    private final Driver driver;
    private final IRealWorldSimulator simulator;
    private final FauxbotApplication fauxbot;
    private boolean stop;

    public FauxbotRunner(MechanismManager mechanisms, Driver driver, IRealWorldSimulator simulator, FauxbotApplication fauxbot)
    {
        this.mechanisms = mechanisms;
        this.driver = driver;
        this.simulator = simulator;
        this.fauxbot = fauxbot;
        this.stop = false;
    }

    @Override
    public void run()
    {
        while (!this.stop)
        {
            this.mechanisms.readSensors();
            this.driver.update();
            this.mechanisms.update();
            this.simulator.update();
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

    public void stop()
    {
        this.stop = true;
    }
}
