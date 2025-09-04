package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import frc.lib.CoreRobot;
import frc.lib.driver.IDriver;
import frc.lib.robotprovider.FauxbotDriverStation;
import frc.lib.robotprovider.RobotMode;
import frc.robot.simulation.SimulatorBase;

abstract class FauxbotGameScreenBase implements Screen
{
    protected final FauxbotGame game;
    protected final Simulation selectedSimulation;

    protected final CoreRobot<FauxbotCommonModule> robot;
    protected final IDriver robotDriver;
    protected final SimulatorBase simulator;
    protected final FauxbotRunner runner;
    protected final Thread runnerThread;

    protected final Stage stage;
    protected final Skin skin;

    protected RobotMode currentMode;

    FauxbotGameScreenBase(final FauxbotGame game, Simulation selectedSimulation)
    {
        this.game = game;
        this.selectedSimulation = selectedSimulation;

        FauxbotCommonModule desiredModule = null;;
        switch (this.selectedSimulation)
        {
            case Forklift:
                desiredModule = new ForkliftFauxbotModule();
                break;

            case GarageDoor:
                desiredModule = new GarageDoorFauxbotModule();
                break;

            case Elevator:
                desiredModule = new ElevatorFauxbotModule();
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
        this.robotDriver = this.robot.getInjector().getInstance(IDriver.class);

        this.runnerThread.start();

        this.stage = new Stage(new ExtendViewport(900, 750));
        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin(Gdx.files.internal("skin/irs1318skin.json"));

        Table primaryTable = new Table(this.skin);
        primaryTable.setFillParent(true);
        ////primaryTable.setDebug(true);

        Label title = new Label(this.selectedSimulation.toString() + " Simulation", this.skin, "title");
        primaryTable.add(title).pad(20).top().colspan(4);
        primaryTable.row();

        primaryTable.add().expandX().padLeft(5);
        Label currentModeLabel = new Label("Mode", this.skin, "subtitle");
        primaryTable.add(currentModeLabel);

        RobotMode[] robotModes = RobotMode.values();
        SelectBox<RobotMode> modeSelector = new SelectBox<RobotMode>(this.skin);
        modeSelector.getSelection().setRequired(true);
        modeSelector.setItems(robotModes);
        modeSelector.setSelected(RobotMode.Disabled);
        this.currentMode = RobotMode.Disabled;
        modeSelector.addListener(
            new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    currentMode = modeSelector.getSelected();
                }
            });

        primaryTable.add(modeSelector);
        primaryTable.add().expandX().padRight(5);
        primaryTable.row();

        Table innerInfoTable = new Table(this.skin);
        ////innerInfoTable.setDebug(true);

        this.populateInnerInfoTable(innerInfoTable);

        ScrollPane scrollPane = new ScrollPane(innerInfoTable, this.skin);
        Table simulatorTable = new Table(this.skin);
        ////simulatorTable.setDebug(true);
        simulatorTable.add(this.simulator).center().expand();

        SplitPane pane = new SplitPane(scrollPane, simulatorTable, false, this.skin);
        primaryTable.add(pane).colspan(4).expand().pad(5).fill();
        primaryTable.row();

        this.stage.addActor(primaryTable);
    }

    protected abstract void populateInnerInfoTable(Table innerInfoTable);

    protected abstract void updateInnerTable();

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update mode
        this.runner.setMode(this.currentMode);
        FauxbotDriverStation.Instance.setMode(this.currentMode);
        this.updateInnerTable();

        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        // update the viewport
        this.stage.getViewport().update(width, height, true);
    }

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

        this.stage.dispose();
        this.simulator.dispose();
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void show()
    {
    }

    @Override
    public void hide()
    {
    }
}
