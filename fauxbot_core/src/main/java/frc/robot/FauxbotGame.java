package frc.robot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FauxbotGame extends Game
{
    SpriteBatch batch;
    BitmapFont font;

    private FreeTypeFontGenerator fontGenerator;

    @Override
    public void create()
    {
        this.batch = new SpriteBatch();
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Light.ttf"));
        this.generateFont();
        this.setScreen(new FauxbotMenuScreen(this));
    }

    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void dispose()
    {
        this.batch.dispose();
        this.font.dispose();
        this.fontGenerator.dispose();
    }

    public void generateFont()
    {
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 25;
        this.font = this.fontGenerator.generateFont(params);
    }
}
