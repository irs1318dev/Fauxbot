package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import frc.lib.driver.IButtonMap;
import frc.lib.robotprovider.RobotMode;

public class FauxbotGameLiteScreen extends FauxbotGameScreenBase implements Screen
{
    private final Stage stage;
    private final Table primaryTable;

    private final Label title;
    private SelectBox<RobotMode> modeSelector;

    private RobotMode currentMode;
    private Table infoTable;

    public FauxbotGameLiteScreen(final FauxbotGame game, Simulation selectedSimulation)
    {
        super(game, selectedSimulation);

        this.stage = new Stage(new ExtendViewport(800, 600));
        Gdx.input.setInputProcessor(this.stage);

        Skin skin = new Skin(Gdx.files.internal("skin/irs1318skin.json"));

        this.primaryTable = new Table(skin);
        this.primaryTable.setFillParent(true);
        ////this.table.setDebug(true);
        this.stage.addActor(this.primaryTable);

        this.title = new Label(this.selectedSimulation.toString() + " Simulation", skin, "title");
        this.primaryTable.add(title).pad(20, 20, 20, 20);
        this.primaryTable.row();

        this.infoTable = new Table(skin);
        this.infoTable.setDebug(true);

        SplitPane pane = new SplitPane(this.infoTable, this.simulator, true, skin);
        this.primaryTable.add(pane).expand().left().top().pad(5, 5, 5, 5);
        this.primaryTable.row();

        Label currentModeLabel = new Label("Mode", skin);
        this.infoTable.add(currentModeLabel);

        RobotMode[] robotModes = RobotMode.values();
        this.modeSelector = new SelectBox<RobotMode>(skin);
        this.modeSelector.getSelection().setRequired(true);
        this.modeSelector.setItems(robotModes);
        this.modeSelector.setSelected(RobotMode.Disabled);
        this.currentMode = RobotMode.Disabled;
        this.modeSelector.addListener(
            new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    currentMode = modeSelector.getSelected();
                }
            });

        this.infoTable.add(this.modeSelector);
        this.infoTable.row();

        IButtonMap buttonMap = this.robot.getInjector().getInstance(IButtonMap.class);


    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.runner.setMode(this.currentMode);
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
        super.dispose();
        this.stage.dispose();
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
