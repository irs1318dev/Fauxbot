package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class FauxbotMenuScreen implements Screen
{
    private final FauxbotGame game;
    private OrthographicCamera camera;
    private Rectangle[] simRectangles;
    private Simulation[] simulations;

    public FauxbotMenuScreen(final FauxbotGame game)
    {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.simulations = Simulation.values();
        this.simRectangles = new Rectangle[simulations.length];
        for (int i = 0; i < simulations.length; i++)
        {
            this.simRectangles[i] = new Rectangle(100, 400 - i * 40, 200, 40);
        }
    }

    @Override
    public void show()
    {
    }

    @Override
    public void render(float delta)
    {
        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        this.game.font.draw(this.game.batch, "Welcome to Fauxbot 2.0!", 300, 450);
        for (int i = 0; i < this.simRectangles.length; i++)
        {
            this.game.font.draw(this.game.batch, this.simulations[i].toString(), this.simRectangles[i].x, this.simRectangles[i].y);
        }

        this.game.batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            this.game.setScreen(new FauxbotGameScreen(this.game));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height)
    {
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
    }
}
