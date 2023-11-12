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
    BitmapFont largeFont;
    BitmapFont smallFont;

    private FreeTypeFontGenerator fontGenerator;

    @Override
    public void create()
    {
        this.batch = new SpriteBatch();
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Light.ttf"));
        this.regenerateFonts(600);
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
        this.largeFont.dispose();
        this.smallFont.dispose();
        this.fontGenerator.dispose();
    }

    public void regenerateFonts(int height)
    {
        if (height < 300)
        {
            if (this.largeFont != null && this.smallFont != null)
            {
                return;
            }

            height = 300;
        }

        if (this.largeFont != null)
        {
            this.largeFont.dispose();
        }

        if (this.smallFont != null)
        {
            this.smallFont.dispose();
        }

        FreeTypeFontParameter largeFontParams = new FreeTypeFontParameter();
        largeFontParams.size = height / 12;
        this.largeFont = this.fontGenerator.generateFont(largeFontParams);
        
        FreeTypeFontParameter smallFontParams = new FreeTypeFontParameter();
        smallFontParams.size = height / 18;
        this.smallFont = this.fontGenerator.generateFont(smallFontParams);
    }
}
