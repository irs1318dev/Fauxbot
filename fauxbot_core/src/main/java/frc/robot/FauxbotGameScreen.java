package frc.robot;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;

import frc.lib.robotprovider.FauxbotJoystick;
import frc.lib.robotprovider.FauxbotJoystickManager;
import frc.lib.robotprovider.FauxbotSmartDashboardLogger;
import frc.lib.robotprovider.IJoystick;
import frc.lib.robotprovider.ISmartDashboardLogger;

public class FauxbotGameScreen extends FauxbotGameScreenBase implements Screen
{
    public FauxbotGameScreen(final FauxbotGame game, Simulation selectedSimulation, Array<Controller> controllers)
    {
        super(game, selectedSimulation);

        for (int i = 0; i < controllers.size; i++)
        {
            IJoystick joystick = FauxbotJoystickManager.get(i);
            if (joystick != null && joystick instanceof FauxbotJoystick)
            {
                FauxbotJoystick fauxbotJoystick = (FauxbotJoystick)joystick;
                fauxbotJoystick.setController(controllers.get(i));
            }
        }
    }

    @Override
    protected void populateInnerInfoTable(Table innerInfoTable)
    {
        // Add Logging data
        Label buttonsLabel = new Label("Logging:", this.skin, "subtitle");
        innerInfoTable.add(buttonsLabel).colspan(2).top().left().expandX().padTop(10);
        innerInfoTable.row();

        FauxbotSmartDashboardLogger logger =
            (FauxbotSmartDashboardLogger)this.robot.getInjector().getInstance(ISmartDashboardLogger.class);

        LoggingKey[] keys = LoggingKey.values();
        for (LoggingKey key : keys)
        {
            Label keyLabel = new Label(key.value, this.skin);
            innerInfoTable.add(keyLabel).left();

            LoggingKeyUI loggedValue = new LoggingKeyUI(key, logger, this.skin);
            innerInfoTable.add(loggedValue).left();

            innerInfoTable.row();
        }
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
    }

    public void dispose()
    {
        super.dispose();
    }

    private class LoggingKeyUI extends TextField
    {
        private final LoggingKey key;
        private final FauxbotSmartDashboardLogger logger;

        public LoggingKeyUI(LoggingKey key, FauxbotSmartDashboardLogger logger, Skin skin)
        {
            super("", skin);

            this.key = key;
            this.logger = logger;

            this.setProgrammaticChangeEvents(false);
            this.setDisabled(true);
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setText(this.logger.getString(this.key));
        }
    }
}
