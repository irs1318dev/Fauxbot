package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class FauxbotGameLiteScreen implements Screen
{
    private final FauxbotGame game;

    private OrthographicCamera camera;
    private Texture img;
    private Rectangle rect;

    public FauxbotGameLiteScreen(final FauxbotGame game, Simulation simulation)
    {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.img = new Texture("images/badlogic.jpg");

        this.rect = new Rectangle();
        this.rect.x = 800 / 2 - 64 / 2;
        this.rect.y = 20;
        this.rect.width = 64;
        this.rect.height = 64;
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0, 0, 0, 1);
        this.camera.update();

        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.batch.begin();
        this.game.batch.draw(this.img, this.rect.x, this.rect.y);
        this.game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            this.rect.x -= 200 * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            this.rect.x += 200 * delta;
        }

        if (this.rect.x < 0)
        {
            this.rect.x = 0;
        }

        if (this.rect.x > 800 - 64)
        {
            this.rect.x = 800 - 64;
        }
    }

    public void dispose()
    {
        this.img.dispose();
    }

    @Override
    public void show()
    {
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
}
