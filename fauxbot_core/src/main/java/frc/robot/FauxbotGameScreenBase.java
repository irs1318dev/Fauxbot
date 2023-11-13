package frc.robot;

import com.badlogic.gdx.Screen;

import frc.lib.CoreRobot;
import frc.robot.simulation.SimulatorBase;

abstract class FauxbotGameScreenBase implements Screen
{
    protected final FauxbotGame game;
    protected final Simulation selectedSimulation;

    protected final CoreRobot<FauxbotCommonModule> robot;
    protected final SimulatorBase simulator;
    protected final FauxbotRunner runner;
    protected final Thread runnerThread;

    FauxbotGameScreenBase(final FauxbotGame game, Simulation selectedSimulation)
    {
        this.game = game;
        this.selectedSimulation = selectedSimulation;

        FauxbotCommonModule desiredModule = null;;
        switch (this.selectedSimulation)
        {
            case Elevator:
                desiredModule = new ElevatorFauxbotModule();
                break;

            case Forklift:
                desiredModule = new ForkliftFauxbotModule();
                break;

            case GarageDoor:
                desiredModule = new GarageDoorFauxbotModule();
                break;

            case Shooter:
                desiredModule = new ShooterFauxbotModule();
                break;

            case Printer:
                desiredModule = new PrinterFauxbotModule();
                break;
        }

        this.robot = new CoreRobot<FauxbotCommonModule>(desiredModule);

        this.simulator = this.robot.getInjector().getInstance(SimulatorBase.class);
        this.runner = new FauxbotRunner(this.robot);
        this.runnerThread = new Thread(this.runner);
        this.robot.robotInit();

        this.runnerThread.start();
    }

    @Override
    public abstract void render(float delta);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public void dispose()
    {
        if (this.runner != null)
        {
            this.runner.stop();
            if (this.runnerThread != null)
            {
                try
                {
                    this.runnerThread.join(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void show();

    @Override
    public abstract void hide();
}
