package frc.robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IButtonMap;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.descriptions.MacroOperationDescription;
import frc.lib.driver.descriptions.OperationDescription;
import frc.lib.robotprovider.FauxbotAnalogInput;
import frc.lib.robotprovider.FauxbotDigitalInput;
import frc.lib.robotprovider.FauxbotDriverStation;
import frc.lib.robotprovider.FauxbotEncoder;
import frc.lib.robotprovider.FauxbotIMU;
import frc.lib.robotprovider.FauxbotJoystick;
import frc.lib.robotprovider.FauxbotJoystickManager;
import frc.lib.robotprovider.FauxbotNavx;
import frc.lib.robotprovider.FauxbotSensorBase;
import frc.lib.robotprovider.FauxbotSensorConnection;
import frc.lib.robotprovider.FauxbotSensorManager;
import frc.lib.robotprovider.RobotMode;

public class FauxbotGameLiteScreen extends FauxbotGameScreenBase implements Screen
{
    private final Stage stage;
    private final Table primaryTable;
    private final Skin skin;

    private RobotMode currentMode;

    public FauxbotGameLiteScreen(final FauxbotGame game, Simulation selectedSimulation)
    {
        super(game, selectedSimulation);

        this.stage = new Stage(new ExtendViewport(800, 600));
        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin(Gdx.files.internal("skin/irs1318skin.json"));

        this.primaryTable = new Table(this.skin);
        this.primaryTable.setFillParent(true);
        // this.primaryTable.setDebug(true);

        Label title = new Label(this.selectedSimulation.toString() + " Simulation", this.skin, "title");
        this.primaryTable.add(title).pad(20).top().colspan(4);
        this.primaryTable.row();

        this.primaryTable.add().expandX().padLeft(5);
        Label currentModeLabel = new Label("Mode", this.skin, "subtitle");
        this.primaryTable.add(currentModeLabel);

        RobotMode[] robotModes = RobotMode.values();
        SelectBox<RobotMode> modeSelector = new SelectBox<RobotMode>(this.skin);
        modeSelector.getSelection().setRequired(true);
        modeSelector.setItems(robotModes);
        modeSelector.setSelected(RobotMode.Disabled);
        this.currentMode = RobotMode.Disabled;
        modeSelector.addListener(
            new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    currentMode = modeSelector.getSelected();
                }
            });

        this.primaryTable.add(modeSelector);
        this.primaryTable.add().expandX().padRight(5);
        this.primaryTable.row();

        Table innerInfoTable = new Table(this.skin);
        innerInfoTable.setDebug(true);

        // Add Operations
        Label buttonsLabel = new Label("Operations:", this.skin, "subtitle");
        innerInfoTable.add(buttonsLabel).colspan(2).top().left().expandX().padTop(10);
        innerInfoTable.row();

        IButtonMap buttonMap = this.robot.getInjector().getInstance(IButtonMap.class);
        for (DigitalOperationDescription description : buttonMap.getDigitalOperationSchema())
        {
            this.addOperationDescription(description, innerInfoTable);
        }

        for (AnalogOperationDescription description : buttonMap.getAnalogOperationSchema())
        {
            this.addOperationDescription(description, innerInfoTable);
        }

        // Add Macros
        Label macrosLabel = new Label("Macros:", this.skin, "subtitle");
        innerInfoTable.add(macrosLabel).colspan(2).left().expandX().padTop(10);
        innerInfoTable.row();

        for (MacroOperationDescription description : buttonMap.getMacroOperationSchema())
        {
            this.addMacroDescription(description, innerInfoTable);
        }

        // Add Sensors
        Label sensorsLabel = new Label("Sensors:", this.skin, "subtitle");
        innerInfoTable.add(sensorsLabel).colspan(2).left().expandX().padTop(10);
        innerInfoTable.row();

        FauxbotSensorConnection[] sensors = this.simulator.getSensors();
        if (sensors == null)
        {
            sensors = (FauxbotSensorConnection[])FauxbotSensorManager.sensorMap.keySet().toArray();
        }

        for (FauxbotSensorConnection connection : sensors)
        {
            FauxbotSensorBase sensor = FauxbotSensorManager.get(connection);
            if (sensor != null)
            {
                boolean useTextBox = this.simulator.getSensorTextBox(connection);
                String sensorName = this.simulator.getSensorName(connection) + ":";

                Label sensorLabel = new Label(sensorName, this.skin);
                innerInfoTable.add(sensorLabel).left();

                if (sensor instanceof FauxbotDigitalInput)
                {
                }
                else if (sensor instanceof FauxbotAnalogInput)
                {
                }
                else if (sensor instanceof FauxbotEncoder)
                {
                }
                else if (sensor instanceof FauxbotIMU)
                {
                }
            }
        }

        // Add Actuators

        ScrollPane scrollPane = new ScrollPane(innerInfoTable, this.skin);

        SplitPane pane = new SplitPane(scrollPane, this.simulator, false, this.skin);
        this.primaryTable.add(pane).colspan(4).expand().pad(5).fill();
        this.primaryTable.row();

        this.stage.addActor(this.primaryTable);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(Color.PURPLE.r, Color.PURPLE.g, Color.PURPLE.b, Color.PURPLE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update mode
        this.runner.setMode(this.currentMode);
        FauxbotDriverStation.Instance.setMode(this.currentMode);

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

    private void addOperationDescription(OperationDescription description, Table infoTable)
    {
        if (description == null)
        {
            return;
        }

        int joystickPort = description.getUserInputDevice().getId();
        if (joystickPort != -1)
        {
            final FauxbotJoystick joystick = (FauxbotJoystick)FauxbotJoystickManager.get(joystickPort);
            if (joystick != null)
            {
                switch (description.getType())
                {
                    case Digital:
                        DigitalOperationDescription digitalDescription = (DigitalOperationDescription)description;
                        UserInputDeviceButton button = digitalDescription.getUserInputDeviceButton();
                        switch (digitalDescription.getButtonType())
                        {
                            case Click:
                                ClickButton clickButton = new ClickButton(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(clickButton).colspan(2).left().fillX();
                                break;

                            case Toggle:
                                ToggleButton toggleButton = new ToggleButton(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(toggleButton).colspan(2).left();
                                break;

                            case Simple:
                                ToggleSimpleButton simpleButton = new ToggleSimpleButton(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(simpleButton).colspan(2).left();
                                break;
                        }

                        break;

                    case Analog:
                        AnalogOperationDescription analogDescription = (AnalogOperationDescription)description;

                        Label label = new Label(description.getOperation().toString(), this.skin);
                        infoTable.add(label).left();

                        AnalogAxis axis = analogDescription.getUserInputDeviceAxis();
                        Slider slider = new Slider(-1.0f, 1.0f, 0.1f, false, this.skin);
                        slider.setValue(0.0f);
                        slider.addListener(
                            new ChangeListener()
                            {
                                @Override
                                public void changed(ChangeEvent event, Actor actor)
                                {
                                    joystick.setAxis(axis.Value, slider.getValue());
                                }
                            });
                        infoTable.add(slider).fillX().padLeft(5).padRight(5);
                        break;

                    case None:
                    default:
                        return;
                }

                infoTable.row();
            }
        }
    }

    private void addMacroDescription(MacroOperationDescription description, Table infoTable)
    {
        if (description == null)
        {
            return;
        }

        int joystickPort = description.getUserInputDevice().getId();
        if (joystickPort != -1)
        {
            final FauxbotJoystick joystick = (FauxbotJoystick)FauxbotJoystickManager.get(joystickPort);
            if (joystick != null)
            {
                UserInputDeviceButton button = description.getUserInputDeviceButton();
                switch (description.getButtonType())
                {
                    case Click:
                        ClickButton clickButton = new ClickButton(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(clickButton).colspan(2).left().fillX();
                        break;

                    case Toggle:
                        ClickButton toggleButton = new ClickButton(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(toggleButton).colspan(2).left();
                        break;

                    case Simple:
                        ToggleSimpleButton simpleButton = new ToggleSimpleButton(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(simpleButton).colspan(2).left();
                        break;
                }
            }
        }
    }

    private class ClickButton extends TextButton
    {
        private static final double STAY_PRESSED_FOR = 0.04;

        private final FauxbotJoystick joystick;
        private final UserInputDeviceButton button;

        private double clearClickAfter;

        public ClickButton(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
        {
            super(text, skin);

            this.joystick = joystick;
            this.button = button;

            this.clearClickAfter = -0.0;
            if (button == UserInputDeviceButton.POV)
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            joystick.setPOV(pov);
                            clearClickAfter = ClickButton.STAY_PRESSED_FOR;
                        }
                    });
            }
            else
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            joystick.setButton(button.Value, true);
                            clearClickAfter = ClickButton.STAY_PRESSED_FOR;
                        }
                    });
            }
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            if (this.clearClickAfter > 0.0)
            {
                this.clearClickAfter -= delta;
                if (this.clearClickAfter <= 0.0)
                {
                    if (this.button == UserInputDeviceButton.POV)
                    {
                        this.joystick.clearPOV();
                    }
                    else
                    {
                        this.joystick.setButton(this.button.Value, false);
                    }

                    this.setDisabled(false);
                }
            }
        }
    }

    private class ToggleSimpleButton extends CheckBox
    {
        public ToggleSimpleButton(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
        {
            super(text, skin);

            if (button == UserInputDeviceButton.POV)
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            if (isChecked())
                            {
                                joystick.setPOV(pov);
                            }
                            else
                            {
                                joystick.clearPOV();
                            }
                        }
                    });
            }
            else
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            joystick.setButton(button.Value, isChecked());
                        }
                    });
            }
        }
    }

    private class ToggleButton extends CheckBox
    {
        private static final double STAY_PRESSED_FOR = 0.04;

        private final FauxbotJoystick joystick;
        private final UserInputDeviceButton button;

        private double clearClickAfter;

        public ToggleButton(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
        {
            super(text, skin);

            this.joystick = joystick;
            this.button = button;

            this.clearClickAfter = -0.0;
            if (button == UserInputDeviceButton.POV)
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            joystick.setPOV(pov);
                            clearClickAfter = ToggleButton.STAY_PRESSED_FOR;
                            setDisabled(true);
                        }
                    });
            }
            else
            {
                this.addListener(
                    new ChangeListener()
                    {
                        @Override
                        public void changed(ChangeEvent event, Actor actor)
                        {
                            joystick.setButton(button.Value, true);
                            clearClickAfter = ToggleButton.STAY_PRESSED_FOR;
                            setDisabled(true);
                        }
                    });
            }
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            if (this.clearClickAfter > 0.0)
            {
                this.clearClickAfter -= delta;
                if (this.clearClickAfter <= 0.0)
                {
                    if (this.button == UserInputDeviceButton.POV)
                    {
                        this.joystick.clearPOV();
                    }
                    else
                    {
                        this.joystick.setButton(this.button.Value, false);
                    }

                    this.setDisabled(false);
                }
            }
        }
    }
}
