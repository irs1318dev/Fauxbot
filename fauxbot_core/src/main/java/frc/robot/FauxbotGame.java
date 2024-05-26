package frc.robot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FauxbotGame extends Game
{
    SpriteBatch batch;

    @Override
    public void create()
    {
        this.batch = new SpriteBatch();
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
        Screen currentScreen = this.getScreen();
        if (currentScreen != null)
        {
            currentScreen.dispose();
        }
    }
}
