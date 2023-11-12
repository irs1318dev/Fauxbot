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

public class FauxbotGameLiteScreen extends FauxbotGameScreenBase implements Screen
{
    private final Stage stage;
    private final Table table;

    private final Label title;
    private SelectBox<RobotMode> modeSelector;

    private RobotMode currentMode;

    public FauxbotGameLiteScreen(final FauxbotGame game, Simulation selectedSimulation)
    {
        super(game, selectedSimulation);

        this.stage = new Stage(new ExtendViewport(800, 600));
        Gdx.input.setInputProcessor(this.stage);

        this.table = new Table();
        this.table.setFillParent(true);
        ////this.table.setDebug(true);
        this.stage.addActor(this.table);

        Skin skin = new Skin(Gdx.files.internal("skin/irs1318skin.json"));


        this.title = new Label(this.selectedSimulation.toString() + " Simulation", skin, "title");
        this.table.add(title).pad(20, 20, 20, 20).colspan(2);
        this.table.row();

        Label currentModeLabel = new Label("Mode", skin);
        this.table.add(currentModeLabel);

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

        this.table.add(this.modeSelector);
        this.table.row();

        IButtonMap buttonMap = this.robot.getInjector().getInstance(IButtonMap.class);


    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        // update the viewport
        this.stage.getViewport().update(width, height, true);
    }

    public void dispose()
    {
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
