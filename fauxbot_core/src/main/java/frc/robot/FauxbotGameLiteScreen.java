package frc.robot;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IButtonMap;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.descriptions.MacroOperationDescription;
import frc.lib.driver.descriptions.OperationDescription;
import frc.lib.robotprovider.FauxbotActuatorBase;
import frc.lib.robotprovider.FauxbotActuatorConnection;
import frc.lib.robotprovider.FauxbotActuatorManager;
import frc.lib.robotprovider.FauxbotAnalogInput;
import frc.lib.robotprovider.FauxbotDigitalInput;
import frc.lib.robotprovider.FauxbotDoubleSolenoid;
import frc.lib.robotprovider.FauxbotEncoder;
import frc.lib.robotprovider.FauxbotIMU;
import frc.lib.robotprovider.FauxbotJoystick;
import frc.lib.robotprovider.FauxbotJoystickManager;
import frc.lib.robotprovider.FauxbotMotorBase;
import frc.lib.robotprovider.FauxbotSensorBase;
import frc.lib.robotprovider.FauxbotSensorConnection;
import frc.lib.robotprovider.FauxbotSensorManager;
import frc.lib.robotprovider.FauxbotSolenoid;

public class FauxbotGameLiteScreen extends FauxbotGameScreenBase implements Screen
{
    public FauxbotGameLiteScreen(final FauxbotGame game, Simulation selectedSimulation)
    {
        super(game, selectedSimulation);
    }

    @Override
    public void populateInnerInfoTable(Table innerInfoTable)
    {
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
                String sensorName = this.simulator.getSensorName(connection) + ":";

                if (sensor instanceof FauxbotDigitalInput)
                {
                    DigitalInputUI digitalInput = new DigitalInputUI((FauxbotDigitalInput)sensor, sensorName, this.skin);
                    innerInfoTable.add(digitalInput).colspan(2).left();
                }
                else if (sensor instanceof FauxbotAnalogInput)
                {
                    Label sensorLabel = new Label(sensorName, this.skin);
                    innerInfoTable.add(sensorLabel).left();

                    float min = (float)this.simulator.getSensorMin(connection);
                    float max = (float)this.simulator.getSensorMax(connection);

                    AnalogInputUI analogInput = new AnalogInputUI((FauxbotAnalogInput)sensor, min, max, 0.1f, this.skin);
                    innerInfoTable.add(analogInput).fillX();
                }
                else if (sensor instanceof FauxbotEncoder)
                {
                    Label sensorLabel = new Label(sensorName, this.skin);
                    innerInfoTable.add(sensorLabel).left();

                    float min = (float)this.simulator.getSensorMin(connection);
                    float max = (float)this.simulator.getSensorMax(connection);

                    EncoderUI encoder = new EncoderUI((FauxbotEncoder)sensor, min, max, this.skin);
                    innerInfoTable.add(encoder).fillX();
                }
                else if (sensor instanceof FauxbotIMU)
                {
                    Label sensorLabel = new Label(sensorName, this.skin);
                    innerInfoTable.add(sensorLabel).left();

                    float min = (float)this.simulator.getSensorMin(connection);
                    float max = (float)this.simulator.getSensorMax(connection);

                    ImuUI imu = new ImuUI((FauxbotIMU)sensor, min, max, 0.1f, this.skin);
                    innerInfoTable.add(imu).fillX();
                }
                else
                {
                    continue;
                }

                innerInfoTable.row();
            }
        }

        // Add Actuators
        Label actuatorsLabel = new Label("Actuators:", this.skin, "subtitle");
        innerInfoTable.add(actuatorsLabel).colspan(2).left().expandX().padTop(10);
        innerInfoTable.row();

        FauxbotActuatorConnection[] actuators = this.simulator.getActuators();
        if (actuators == null)
        {
            actuators = (FauxbotActuatorConnection[])FauxbotActuatorManager.actuatorMap.keySet().toArray();
        }

        for (FauxbotActuatorConnection connection : actuators)
        {
            FauxbotActuatorBase actuator = FauxbotActuatorManager.get(connection);
            String actuatorName = this.simulator.getActuatorName(connection);

            Label actuatorLabel = new Label(actuatorName, this.skin);
            if (actuator instanceof FauxbotMotorBase)
            {
                innerInfoTable.add(actuatorLabel).left();

                float min = (float)this.simulator.getMotorMin(connection);
                float max = (float)this.simulator.getMotorMax(connection);

                MotorUI motor = new MotorUI((FauxbotMotorBase)actuator, min, max, 0.1f, this.skin);
                innerInfoTable.add(motor).fillX();
            }
            else if (actuator instanceof FauxbotSolenoid)
            {
                innerInfoTable.add(actuatorLabel).left();

                SolenoidUI solenoid = new SolenoidUI((FauxbotSolenoid)actuator, this.skin);
                innerInfoTable.add(solenoid).fillX();
            }
            else if (actuator instanceof FauxbotDoubleSolenoid)
            {
                innerInfoTable.add(actuatorLabel).left();

                DoubleSolenoidUI doubleSolenoid = new DoubleSolenoidUI((FauxbotDoubleSolenoid)actuator, this.skin);
                innerInfoTable.add(doubleSolenoid).fillX();
            }
            else
            {
                continue;
            }

            innerInfoTable.row();
        }
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
                                ClickButtonUI clickButton = new ClickButtonUI(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(clickButton).colspan(2).left().fillX();
                                break;

                            case Toggle:
                                ToggleButtonUI toggleButton = new ToggleButtonUI(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(toggleButton).colspan(2).left();
                                break;

                            case Simple:
                                ToggleSimpleButtonUI simpleButton = new ToggleSimpleButtonUI(description.getOperation().toString(), joystick, button, digitalDescription.getUserInputDevicePovValue(), this.skin);
                                infoTable.add(simpleButton).colspan(2).left();
                                break;

                            default:
                                return;
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
                        ClickButtonUI clickButton = new ClickButtonUI(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(clickButton).colspan(2).left().fillX();
                        break;

                    case Toggle:
                        ClickButtonUI toggleButton = new ClickButtonUI(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(toggleButton).colspan(2).left();
                        break;

                    case Simple:
                        ToggleSimpleButtonUI simpleButton = new ToggleSimpleButtonUI(description.getOperation().toString(), joystick, button, description.getUserInputDevicePovValue(), this.skin);
                        infoTable.add(simpleButton).colspan(2).left();
                        break;

                    default:
                        return;
                }

                infoTable.row();
            }
        }
    }

    private class ClickButtonUI extends TextButton
    {
        private static final double STAY_PRESSED_FOR = 0.04;

        private final FauxbotJoystick joystick;
        private final UserInputDeviceButton button;

        private double clearClickAfter;

        public ClickButtonUI(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
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
                            clearClickAfter = ClickButtonUI.STAY_PRESSED_FOR;
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
                            clearClickAfter = ClickButtonUI.STAY_PRESSED_FOR;
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

    private class ToggleSimpleButtonUI extends CheckBox
    {
        public ToggleSimpleButtonUI(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
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

    private class ToggleButtonUI extends CheckBox
    {
        private static final double STAY_PRESSED_FOR = 0.25;

        private final FauxbotJoystick joystick;
        private final UserInputDeviceButton button;

        private double clearClickAfter;

        public ToggleButtonUI(String text, FauxbotJoystick joystick, UserInputDeviceButton button, int pov, Skin skin)
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
                            clearClickAfter = ToggleButtonUI.STAY_PRESSED_FOR;
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
                            clearClickAfter = ToggleButtonUI.STAY_PRESSED_FOR;
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

    private class DigitalInputUI extends CheckBox
    {
        private final FauxbotDigitalInput digitalInput;

        public DigitalInputUI(FauxbotDigitalInput digitalInput, String text, Skin skin)
        {
            super(text, skin);

            this.digitalInput = digitalInput;

            this.setProgrammaticChangeEvents(false);
            this.addListener(
                new ChangeListener()
                {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        digitalInput.set(isChecked());
                    }
                });
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setChecked(this.digitalInput.get());
        }
    }

    private class AnalogInputUI extends Slider
    {
        private final FauxbotAnalogInput analogInput;

        public AnalogInputUI(FauxbotAnalogInput analogInput, float min, float max, float step, Skin skin)
        {
            super(min, max, step, false, skin);

            this.analogInput = analogInput;

            this.setProgrammaticChangeEvents(false);
            this.setValue(Math.min(Math.max(min, 0.0f), max));
            this.addListener(
                new ChangeListener()
                {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        analogInput.set(getValue());
                    }
                });
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setValue((float)this.analogInput.get());
        }
    }

    private class EncoderUI extends Slider
    {
        private final FauxbotEncoder encoder;

        public EncoderUI(FauxbotEncoder encoder, float min, float max, Skin skin)
        {
            super(min, max, 1.0f, false, skin);

            this.encoder = encoder;

            this.setProgrammaticChangeEvents(false);
            this.setValue(Math.min(Math.max(min, 0.0f), max));
            this.addListener(
                new ChangeListener()
                {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        encoder.set(getValue());
                    }
                });
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setValue((float)this.encoder.get());
        }
    }

    private class ImuUI extends Slider
    {
        private final FauxbotIMU imu;

        public ImuUI(FauxbotIMU imu, float min, float max, float step, Skin skin)
        {
            super(min, max, step, false, skin);

            this.imu = imu;

            this.setProgrammaticChangeEvents(false);
            this.setValue(Math.min(Math.max(min, 0.0f), max));
            this.addListener(
                new ChangeListener()
                {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        imu.set(getValue());
                    }
                });
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setValue((float)this.imu.get());
        }
    }

    private class MotorUI extends Slider
    {
        private final FauxbotMotorBase motor;

        public MotorUI(FauxbotMotorBase motor, float min, float max, float step, Skin skin)
        {
            super(min, max, step, false, skin);

            this.motor = motor;

            this.setValue(Math.min(Math.max(min, 0.0f), max));
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setValue((float)this.motor.get());
        }
    }

    private class SolenoidUI extends Slider
    {
        private final FauxbotSolenoid solenoid;

        public SolenoidUI(FauxbotSolenoid solenoid, Skin skin)
        {
            super(0.0f, 1.0f, 1.0f, false, skin);

            this.solenoid = solenoid;

            this.setValue(0.0f);
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            this.setValue(this.solenoid.get() ? 0.0f : -1.0f);
        }
    }

    private class DoubleSolenoidUI extends Slider
    {
        private final FauxbotDoubleSolenoid doubleSolenoid;

        public DoubleSolenoidUI(FauxbotDoubleSolenoid doubleSolenoid, Skin skin)
        {
            super(-1.0f, 1.0f, 1.0f, false, skin);

            this.doubleSolenoid = doubleSolenoid;

            this.setValue(0.0f);
        }

        @Override
        public void act(float delta)
        {
            super.act(delta);

            float value;
            switch (this.doubleSolenoid.get())
            {
                case Forward:
                    value = 1.0f;
                    break;

                case Reverse:
                    value = -1.0f;
                    break;

                case Off:
                default:
                    value = 0.0f;
            }

            this.setValue(value);
        }
    }
}
