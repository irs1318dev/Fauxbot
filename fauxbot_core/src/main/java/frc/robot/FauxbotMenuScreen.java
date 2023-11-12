package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FauxbotMenuScreen implements Screen
{
    private final FauxbotGame game;
    private final Stage stage;
    private final Table table;

    private final LabelStyle titleStyle;
    private final LabelStyle labelStyle;
    private final TextButtonStyle buttonStyle;
    private final SelectBoxStyle selectBoxStyle;

    private final Label title;
    private final Label select;
    private final SelectBox<Simulation> simulationSelector;
    private final TextButton simulateButton;
    private final TextButton simulateLiteButton;

    private Simulation selectedSimulation;

    public FauxbotMenuScreen(final FauxbotGame game)
    {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        this.table = new Table();
        this.table.setFillParent(true);
        ////this.table.setDebug(true);
        this.stage.addActor(this.table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));        
        Skin skin = new Skin(atlas);

        this.titleStyle = new LabelStyle(this.game.largeFont, Color.GOLD);
        this.title = new Label("Welcome to FauxbotGame!", this.titleStyle);
        this.table.add(title).pad(20, 20, 20, 20).colspan(2);
        this.table.row();

        this.labelStyle = new LabelStyle(this.game.smallFont, Color.GOLD);

        this.select = new Label("Select a Simulation:", this.labelStyle);
        this.table.add(select).colspan(2);
        this.table.row();

        Array<Controller> controllers = Controllers.getControllers();

        Simulation[] simulations = Simulation.values();
        this.selectBoxStyle = new SelectBoxStyle(
            this.game.smallFont,
            Color.GOLD,
            skin.getDrawable("default-select"),
            new ScrollPaneStyle(
                skin.getDrawable("default-rect"),
                skin.getDrawable("default-scroll"),
                skin.getDrawable("default-round-large"),
                skin.getDrawable("default-scroll"),
                skin.getDrawable("default-round-large")),
            new ListStyle(
                this.game.smallFont, 
                Color.GOLD,
                Color.YELLOW, 
                skin.getDrawable("default-select-selection")));

        this.simulationSelector = new SelectBox<Simulation>(this.selectBoxStyle);
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

        this.buttonStyle = new TextButtonStyle(
            skin.getDrawable("default-round"),
            skin.getDrawable("default-round-down"),            
            skin.getDrawable("default-round"), 
            this.game.smallFont);

        this.simulateButton = new TextButton("Full Simulation", this.buttonStyle);
        if (controllers.isEmpty())
        {
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

        this.simulateLiteButton = new TextButton("Lite Simulation", this.buttonStyle);
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
        // regenerate fonts based on viewport height
        this.game.regenerateFonts(height);

        // re-apply fonts to the styles
        this.titleStyle.font = this.game.largeFont;
        this.labelStyle.font = this.game.smallFont;
        this.selectBoxStyle.font = this.game.smallFont;
        this.selectBoxStyle.listStyle.font = this.game.smallFont;
        this.buttonStyle.font = this.game.smallFont;

        // re-apply styles to the ui elements
        this.title.setStyle(this.titleStyle);
        this.select.setStyle(this.labelStyle);
        this.simulationSelector.setStyle(this.selectBoxStyle);
        this.simulateButton.setStyle(this.buttonStyle);
        this.simulateLiteButton.setStyle(this.buttonStyle);

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
