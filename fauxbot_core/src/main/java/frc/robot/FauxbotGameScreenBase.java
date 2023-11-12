package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import frc.lib.CoreRobot;
import frc.lib.driver.IButtonMap;
import frc.lib.robotprovider.RobotMode;

abstract class FauxbotGameScreenBase implements Screen
{
    protected final FauxbotGame game;
    protected final Simulation selectedSimulation;

    protected final CoreRobot<FauxbotCommonModule> robot;
    protected final IRealWorldSimulator simulator;
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

        this.simulator = this.robot.getInjector().getInstance(IRealWorldSimulator.class);
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
    public abstract void dispose();

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void show();

    @Override
    public abstract void hide();
}
