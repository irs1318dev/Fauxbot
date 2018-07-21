package frc.team1318.robot.fauxbot;

import java.io.IOException;

import frc.team1318.robot.ElectronicsConstants;
import frc.team1318.robot.RobotModule;
import frc.team1318.robot.common.MechanismManager;
import frc.team1318.robot.common.wpilib.ITimer;
import frc.team1318.robot.driver.MacroOperation;
import frc.team1318.robot.driver.Operation;
import frc.team1318.robot.driver.common.Driver;
import frc.team1318.robot.driver.common.IButtonMap;
import frc.team1318.robot.driver.common.buttons.ButtonType;
import frc.team1318.robot.driver.common.descriptions.AnalogOperationDescription;
import frc.team1318.robot.driver.common.descriptions.DigitalOperationDescription;
import frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import frc.team1318.robot.driver.common.descriptions.OperationDescription;
import frc.team1318.robot.driver.common.descriptions.OperationType;
import frc.team1318.robot.driver.common.descriptions.UserInputDevice;
import frc.team1318.robot.driver.common.states.AnalogOperationState;
import frc.team1318.robot.driver.common.user.UserDriver;
import frc.team1318.robot.fauxbot.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.JoystickManager;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SensorManager;
import edu.wpi.first.wpilibj.Solenoid;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FauxbotApplication extends Application
{
    private final FauxbotRunner runner;
    private final Thread runnerThread;

    private final IRealWorldSimulator simulator;

    private MechanismManager mechanisms;
    private ITimer timer;
    private Driver driver;
    private IButtonMap buttonMap;

    private Injector robotInjector;
    private Injector fauxbotInjector;

    private Canvas canvas;

    public FauxbotApplication()
    {
        super();

        this.mechanisms = this.getRobotInjector().getInstance(MechanismManager.class);
        this.timer = this.getRobotInjector().getInstance(ITimer.class);
        this.driver = this.getRobotInjector().getInstance(UserDriver.class);
        this.buttonMap = this.getRobotInjector().getInstance(IButtonMap.class);

        this.mechanisms.setDriver(this.driver);
        this.timer.start();

        this.simulator = this.getFauxbotInjector().getInstance(IRealWorldSimulator.class);
        this.runner = new FauxbotRunner(this.mechanisms, this.driver, this.simulator, this);
        this.runnerThread = new Thread(this.runner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Fauxbot");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int rowCount = 0;
        String fontDefault = "Arial";

        Text buttonsTitle = new Text("Buttons");
        buttonsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(buttonsTitle, 0, rowCount++, 2, 1);
        for (Operation op : Operation.values())
        {
            OperationDescription description = this.buttonMap.getOperationSchema().getOrDefault(op, null);
            if (description != null)
            {
                int joystickPort = -1;
                if (description.getUserInputDevice() == UserInputDevice.Driver)
                {
                    joystickPort = ElectronicsConstants.JOYSTICK_DRIVER_PORT;
                }
                else if (description.getUserInputDevice() == UserInputDevice.CoDriver)
                {
                    joystickPort = ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT;
                }

                if (joystickPort != -1)
                {
                    final Joystick joystick = JoystickManager.get(joystickPort);
                    if (joystick != null)
                    {
                        int thisRowIndex = rowCount;
                        rowCount++;

                        Label operationNameLabel = new Label(op.toString());
                        grid.add(operationNameLabel, 0, thisRowIndex);

                        if (description.getType() == OperationType.Digital)
                        {
                            DigitalOperationDescription digitalDescription = (DigitalOperationDescription)description;
                            int buttonNumber = digitalDescription.getUserInputDeviceButton().Value;
                            if (digitalDescription.getButtonType() == ButtonType.Click)
                            {
                                Button operationButton = new Button("Click");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Toggle)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                Bindings.bindBidirectional(joystick.getButtonProperty(buttonNumber), operationCheckBox.selectedProperty());
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Simple)
                            {
                                Button operationButton = new Button("Simple");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);
                                        ;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                        }
                        else if (description.getType() == OperationType.Analog)
                        {
                            AnalogOperationDescription analogDescription = (AnalogOperationDescription)description;

                            Slider analogSlider = new Slider();
                            analogSlider.setMin(-1.0);
                            analogSlider.setMax(1.0);
                            analogSlider.setBlockIncrement(0.1);
                            analogSlider.setShowTickMarks(true);

                            grid.add(analogSlider, 1, thisRowIndex);
                            Bindings.bindBidirectional(joystick.getAxisProperty(AnalogOperationState.fromAxis(analogDescription
                                .getUserInputDeviceAxis())), analogSlider.valueProperty());
                        }
                    }
                }
            }
        }

        // add a spacer:
        rowCount++;

        if (MacroOperation.values().length > 0)
        {
            Text macrosTitle = new Text("Macros");
            macrosTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
            grid.add(macrosTitle, 0, rowCount++, 2, 1);
            for (MacroOperation op : MacroOperation.values())
            {
                MacroOperationDescription description = this.buttonMap.getMacroOperationSchema().getOrDefault(op, null);
                if (description != null)
                {
                    int joystickPort = -1;
                    if (description.getUserInputDevice() == UserInputDevice.Driver)
                    {
                        joystickPort = ElectronicsConstants.JOYSTICK_DRIVER_PORT;
                    }
                    else if (description.getUserInputDevice() == UserInputDevice.CoDriver)
                    {
                        joystickPort = ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT;
                    }

                    if (joystickPort != -1)
                    {
                        final Joystick joystick = JoystickManager.get(joystickPort);
                        if (joystick != null)
                        {
                            int thisRowIndex = rowCount;
                            rowCount++;

                            Label operationNameLabel = new Label(op.toString());
                            grid.add(operationNameLabel, 0, thisRowIndex);

                            int buttonNumber = description.getUserInputDeviceButton().Value;
                            if (description.getButtonType() == ButtonType.Click)
                            {
                                Button operationButton = new Button("Click");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);
                                        ;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                            else if (description.getButtonType() == ButtonType.Toggle)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                Bindings.bindBidirectional(joystick.getButtonProperty(buttonNumber), operationCheckBox.selectedProperty());
                            }
                            else if (description.getButtonType() == ButtonType.Simple)
                            {
                                Button operationButton = new Button("Simple");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(buttonNumber).set(true);
                                        ;
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                        }
                    }
                }
            }

            // add a spacer:
            rowCount++;
        }

        Text sensorsTitle = new Text("Sensors");
        sensorsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(sensorsTitle, 0, rowCount++, 2, 1);

        for (int i = 0; i <= SensorManager.getHightestPort(); i++)
        {
            SensorBase sensor = SensorManager.get(i);
            if (sensor != null)
            {
                String sensorName = this.simulator.getSensorName(i) + ":";

                int thisRowIndex = rowCount;
                rowCount++;

                Label sensorNameLabel = new Label(sensorName);
                grid.add(sensorNameLabel, 0, thisRowIndex);

                if (sensor instanceof DigitalInput)
                {
                    CheckBox sensorCheckBox = new CheckBox();
                    grid.add(sensorCheckBox, 1, thisRowIndex);
                    Bindings.bindBidirectional(((DigitalInput)sensor).getProperty(), sensorCheckBox.selectedProperty());
                }
                else if (sensor instanceof AnalogInput)
                {
                    Slider sensorSlider = new Slider();
                    sensorSlider.setMin(-1.0);
                    sensorSlider.setMax(1.0);
                    sensorSlider.setBlockIncrement(0.1);
                    sensorSlider.setShowTickMarks(true);

                    grid.add(sensorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((AnalogInput)sensor).getProperty(), sensorSlider.valueProperty());
                }
                else if (sensor instanceof Encoder)
                {
                    double encoderMax = this.simulator.getEncoderMax(i);
                    Slider sensorSlider = new Slider();
                    sensorSlider.setMin(-encoderMax);
                    sensorSlider.setMax(encoderMax);
                    sensorSlider.setBlockIncrement(0.1);
                    sensorSlider.setShowTickMarks(true);

                    grid.add(sensorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((Encoder)sensor).getProperty(), sensorSlider.valueProperty());
                }
            }
        }

        // add a spacer:
        rowCount++;

        Text motorsTitle = new Text("Actuators");
        motorsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(motorsTitle, 0, rowCount++, 2, 1);
        for (int i = 0; i <= ActuatorManager.getHightestPort(); i++)
        {
            ActuatorBase actuator = ActuatorManager.get(i);
            if (actuator != null)
            {
                String motorName = this.simulator.getActuatorName(i) + ":";

                int thisRowIndex = rowCount;
                rowCount++;

                Label actuatorNameLabel = new Label(motorName);
                grid.add(actuatorNameLabel, 0, thisRowIndex);

                if (actuator instanceof MotorBase)
                {
                    Slider motorSlider = new Slider();
                    motorSlider.setMin(-1.0);
                    motorSlider.setMax(1.0);
                    motorSlider.setBlockIncrement(0.25);
                    motorSlider.setShowTickLabels(true);
                    motorSlider.setShowTickMarks(true);

                    grid.add(motorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((MotorBase)actuator).getProperty(), motorSlider.valueProperty());
                }
                else if (actuator instanceof Solenoid)
                {
                    Slider solenoidSlider = new Slider();
                    solenoidSlider.setMin(0.0);
                    solenoidSlider.setMax(1.0);
                    solenoidSlider.setBlockIncrement(0.25);
                    solenoidSlider.setShowTickLabels(true);
                    solenoidSlider.setShowTickMarks(true);

                    grid.add(solenoidSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((Solenoid)actuator).getProperty(), solenoidSlider.valueProperty());
                }
                else if (actuator instanceof DoubleSolenoid)
                {
                    Slider solenoidSlider = new Slider();
                    solenoidSlider.setMin(-1.0);
                    solenoidSlider.setMax(1.0);
                    solenoidSlider.setBlockIncrement(0.25);
                    solenoidSlider.setShowTickLabels(true);
                    solenoidSlider.setShowTickMarks(true);

                    grid.add(solenoidSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((DoubleSolenoid)actuator).getProperty(), solenoidSlider.valueProperty());
                }
            }
        }

        // construct Canvas
        this.canvas = new Canvas(200, 200);
        grid.add(this.canvas, 2, 0, 2, rowCount);

        Scene scene = new Scene(grid, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        // start the runner:
        this.runnerThread.start();
    }

    public void refresh()
    {
        Platform.runLater(
            () ->
            {
                this.simulator.draw(this.canvas);
            });
    }

    @Override
    public void stop() throws Exception
    {
        this.runner.stop();
        this.runnerThread.join(500);
    }

    public static void main(String[] args) throws InterruptedException, IOException
    {
        Application.launch(args);
    }

    /**
     * Lazily initializes and retrieves the injector.
     * @return the injector to use for this robot
     */
    Injector getRobotInjector()
    {
        if (this.robotInjector == null)
        {
            this.robotInjector = Guice.createInjector(new RobotModule());
        }

        return this.robotInjector;
    }

    /**
     * Lazily initializes and retrieves the Fauxbot injector.
     * @return the injector to use for this fauxbot simulation
     */
    Injector getFauxbotInjector()
    {
        if (this.fauxbotInjector == null)
        {
            this.fauxbotInjector = Guice.createInjector(new FauxbotModule());
        }

        return this.fauxbotInjector;
    }
}
