package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class FauxbotMenuScreen implements Screen
{
    private final Stage stage;
    private final Table table;

    private final Label title;
    private final Label select;
    private final SelectBox<Simulation> simulationSelector;
    private final TextButton simulateButton;
    private final TextButton simulateLiteButton;

    private Simulation selectedSimulation;

    public FauxbotMenuScreen(final FauxbotGame game)
    {
        this.stage = new Stage(new ExtendViewport(800, 600));
        Gdx.input.setInputProcessor(this.stage);

        this.table = new Table();
        this.table.setFillParent(true);
        ////this.table.setDebug(true);
        this.stage.addActor(this.table);

        Skin skin = new Skin(Gdx.files.internal("skin/irs1318skin.json"));

        this.title = new Label("Welcome to FauxbotGame!", skin, "title");
        this.table.add(title).pad(20, 20, 20, 20).colspan(2);
        this.table.row();

        this.select = new Label("Select a Simulation:", skin);
        this.table.add(select).colspan(2);
        this.table.row();

        Array<Controller> controllers = Controllers.getControllers();

        Simulation[] simulations = Simulation.values();
        this.simulationSelector = new SelectBox<Simulation>(skin);
        this.simulationSelector.getSelection().setRequired(true);
        this.simulationSelector.setItems(simulations);
        this.selectedSimulation = this.simulationSelector.getSelected();
        this.simulationSelector.addListener(
            new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    selectedSimulation = simulationSelector.getSelected();
                }
            });

        this.table.add(this.simulationSelector).colspan(2);
        this.table.row();

        this.simulateButton = new TextButton("Full Simulation", skin);
        if (controllers.isEmpty())
        {
            this.simulateButton.addListener(new TextTooltip("No joysticks", skin));
            this.simulateButton.setDisabled(true);
        }
        else
        {
            this.simulateButton.addListener(
                new ClickListener()
                {
                    @Override 
                    public void clicked(InputEvent event, float x, float y)
                    {
                        game.setScreen(new FauxbotGameScreen(game, selectedSimulation, controllers));
                        dispose();
                    }
                });
        }

        this.table.add(this.simulateButton);

        this.simulateLiteButton = new TextButton("Lite Simulation", skin);
        this.simulateLiteButton.addListener(
            new ClickListener()
            {
                @Override 
                public void clicked(InputEvent event, float x, float y)
                {
                    game.setScreen(new FauxbotGameLiteScreen(game, selectedSimulation));
                    dispose();
                }
            });

        this.table.add(this.simulateLiteButton);
        this.table.row();
    }

    @Override
    public void show()
    {
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

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        this.stage.dispose();
    }
}
