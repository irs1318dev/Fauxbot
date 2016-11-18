package main;

public class FauxbotRunner implements Runnable
{
    private final ControllerManager controllers;
    private final RealWorldSimulator simulator;
    private boolean stop;

    public FauxbotRunner(ControllerManager controllers, RealWorldSimulator simulator)
    {
        this.controllers = controllers;
        this.simulator = simulator;
        this.stop = false;
    }

    @Override
    public void run()
    {
        while (!this.stop)
        {
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
