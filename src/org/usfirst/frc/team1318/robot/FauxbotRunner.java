package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.driver.Driver;

public class FauxbotRunner implements Runnable
{
    private final ControllerManager controllers;
    private final Driver driver;
    private final RealWorldSimulator simulator;
    private boolean stop;

    public FauxbotRunner(ControllerManager controllers, Driver driver, RealWorldSimulator simulator)
    {
        this.controllers = controllers;
        this.driver = driver;
        this.simulator = simulator;
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
