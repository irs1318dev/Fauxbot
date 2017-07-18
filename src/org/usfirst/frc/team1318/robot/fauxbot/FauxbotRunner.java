package org.usfirst.frc.team1318.robot.fauxbot;

import org.usfirst.frc.team1318.robot.ControllerManager;
import org.usfirst.frc.team1318.robot.driver.Driver;

public class FauxbotRunner implements Runnable
{
    private final ControllerManager controllers;
    private final Driver driver;
    private final IRealWorldSimulator simulator;
    private final Fauxbot fauxbot;
    private boolean stop;

    public FauxbotRunner(ControllerManager controllers, Driver driver, IRealWorldSimulator simulator, Fauxbot fauxbot)
    {
        this.controllers = controllers;
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
            this.driver.update();
            this.controllers.update();
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
