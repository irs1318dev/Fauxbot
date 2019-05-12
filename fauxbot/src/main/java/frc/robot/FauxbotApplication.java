package frc.robot;

import java.io.IOException;

import frc.robot.ElectronicsConstants;
import frc.robot.FauxbotModule;
import frc.robot.FauxbotRunner.RobotMode;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.MacroOperation;
import frc.robot.driver.Operation;
import frc.robot.driver.common.IButtonMap;
import frc.robot.driver.common.UserInputDeviceButton;
import frc.robot.driver.common.buttons.ButtonType;
import frc.robot.driver.common.descriptions.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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

    private final CoreRobot<FauxbotModule> robot;

    private Canvas canvas;

    public FauxbotApplication()
    {
        super();

        this.robot = new CoreRobot<FauxbotModule>(new FauxbotModule());

        this.simulator = this.robot.getInjector().getInstance(IRealWorldSimulator.class);
        this.runner = new FauxbotRunner(this.robot, this);
        this.runnerThread = new Thread(this.runner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.robot.robotInit();

        IButtonMap buttonMap = this.robot.getInjector().getInstance(IButtonMap.class);

        primaryStage.setTitle("Fauxbot");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int rowCount = 0;
        String fontDefault = "Arial";

        Text modeTitle = new Text("Mode");
        modeTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(modeTitle, 0, rowCount, 1, 1);

        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton autonomousButton = new RadioButton("Autonomous");
        autonomousButton.setUserData(FauxbotRunner.RobotMode.Autonomous);
        autonomousButton.setToggleGroup(modeGroup);
        grid.add(autonomousButton, 1, rowCount, 1, 1);

        RadioButton disabledButton = new RadioButton("Disabled");
        disabledButton.setUserData(FauxbotRunner.RobotMode.Disabled);
        disabledButton.setToggleGroup(modeGroup);
        disabledButton.setSelected(true);
        grid.add(disabledButton, 2, rowCount, 1, 1);

        RadioButton teleopButton = new RadioButton("Teleop");
        teleopButton.setUserData(FauxbotRunner.RobotMode.Teleop);
        teleopButton.setToggleGroup(modeGroup);
        grid.add(teleopButton, 3, rowCount, 1, 1);

        modeGroup.selectedToggleProperty().addListener(
            new ChangeListener<Toggle>()
            {
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
                {
                    runner.setMode((RobotMode)newValue.getUserData());
                } 
            });

        rowCount++;

        Text buttonsTitle = new Text("Buttons");
        buttonsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(buttonsTitle, 0, rowCount++, 2, 1);
        for (Operation op : Operation.values())
        {
            OperationDescription description = buttonMap.getOperationSchema().getOrDefault(op, null);
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
                    final FauxbotJoystick joystick = FauxbotJoystickManager.get(joystickPort);
                    if (joystick != null)
                    {
                        int thisRowIndex = rowCount;
                        rowCount++;

                        Label operationNameLabel = new Label(op.toString());
                        grid.add(operationNameLabel, 0, thisRowIndex);

                        if (description.getType() == OperationType.Digital)
                        {
                            DigitalOperationDescription digitalDescription = (DigitalOperationDescription)description;
                            UserInputDeviceButton button = digitalDescription.getUserInputDeviceButton();
                            if (digitalDescription.getButtonType() == ButtonType.Click)
                            {
                                Button operationButton = new Button("Click");
                                operationButton.setOnMouseClicked(
                                    (MouseEvent event) ->
                                    {
                                        joystick.getButtonProperty(button.Value).set(true);
                                    });

                                grid.add(operationButton, 1, thisRowIndex);
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Toggle)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                if (button != UserInputDeviceButton.JOYSTICK_POV)
                                {
                                    Bindings.bindBidirectional(joystick.getButtonProperty(button.Value), operationCheckBox.selectedProperty());
                                }
                                else
                                {
                                    operationCheckBox.selectedProperty();
                                }
                            }
                            else if (digitalDescription.getButtonType() == ButtonType.Simple)
                            {
                                CheckBox operationCheckBox = new CheckBox();
                                grid.add(operationCheckBox, 1, thisRowIndex);
                                Bindings.bindBidirectional(joystick.getButtonProperty(button.Value), operationCheckBox.selectedProperty());
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
                            Bindings.bindBidirectional(joystick.getAxisProperty(analogDescription.getUserInputDeviceAxis()), analogSlider.valueProperty());
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
                MacroOperationDescription description = buttonMap.getMacroOperationSchema().getOrDefault(op, null);
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
                        final FauxbotJoystick joystick = FauxbotJoystickManager.get(joystickPort);
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

        for (FauxbotSensorConnection connection : FauxbotSensorManager.sensorMap.keySet())
        {
            FauxbotSensorBase sensor = FauxbotSensorManager.get(connection);
            if (sensor != null)
            {
                String sensorName = this.simulator.getSensorName(connection) + ":";

                int thisRowIndex = rowCount;
                rowCount++;

                Label sensorNameLabel = new Label(sensorName);
                grid.add(sensorNameLabel, 0, thisRowIndex);

                if (sensor instanceof FauxbotDigitalInput)
                {
                    CheckBox sensorCheckBox = new CheckBox();
                    grid.add(sensorCheckBox, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotDigitalInput)sensor).getProperty(), sensorCheckBox.selectedProperty());
                }
                else if (sensor instanceof FauxbotAnalogInput)
                {
                    double min = this.simulator.getSensorMin(connection);
                    double max = this.simulator.getSensorMax(connection);
                    Slider sensorSlider = new Slider();
                    sensorSlider.setMin(min);
                    sensorSlider.setMax(max);
                    sensorSlider.setBlockIncrement(0.1);
                    sensorSlider.setShowTickMarks(true);

                    grid.add(sensorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotAnalogInput)sensor).getProperty(), sensorSlider.valueProperty());
                }
                else if (sensor instanceof FauxbotEncoder)
                {
                    double min = this.simulator.getSensorMin(connection);
                    double max = this.simulator.getSensorMax(connection);
                    Slider sensorSlider = new Slider();
                    sensorSlider.setMin(min);
                    sensorSlider.setMax(max);
                    sensorSlider.setBlockIncrement(0.1);
                    sensorSlider.setShowTickMarks(true);

                    grid.add(sensorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotEncoder)sensor).getProperty(), sensorSlider.valueProperty());
                }
            }
        }

        // add a spacer:
        rowCount++;

        Text motorsTitle = new Text("Actuators");
        motorsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(motorsTitle, 0, rowCount++, 2, 1);
        for (FauxbotActuatorConnection connection : FauxbotActuatorManager.actuatorMap.keySet())
        {
            FauxbotActuatorBase actuator = FauxbotActuatorManager.get(connection);
            if (actuator != null)
            {
                String motorName = this.simulator.getActuatorName(connection) + ":";

                int thisRowIndex = rowCount;
                rowCount++;

                Label actuatorNameLabel = new Label(motorName);
                grid.add(actuatorNameLabel, 0, thisRowIndex);

                if (actuator instanceof FauxbotMotorBase)
                {
                    double min = this.simulator.getMotorMin(connection);
                    double max = this.simulator.getMotorMax(connection);
                    Slider motorSlider = new Slider();
                    motorSlider.setMin(min);
                    motorSlider.setMax(max);
                    motorSlider.setBlockIncrement(0.25);
                    motorSlider.setShowTickLabels(true);
                    motorSlider.setShowTickMarks(true);

                    grid.add(motorSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotMotorBase)actuator).getProperty(), motorSlider.valueProperty());
                }
                else if (actuator instanceof FauxbotSolenoid)
                {
                    Slider solenoidSlider = new Slider();
                    solenoidSlider.setMin(0.0);
                    solenoidSlider.setMax(1.0);
                    solenoidSlider.setBlockIncrement(0.25);
                    solenoidSlider.setShowTickLabels(true);
                    solenoidSlider.setShowTickMarks(true);

                    grid.add(solenoidSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotSolenoid)actuator).getProperty(), solenoidSlider.valueProperty());
                }
                else if (actuator instanceof FauxbotDoubleSolenoid)
                {
                    Slider solenoidSlider = new Slider();
                    solenoidSlider.setMin(-1.0);
                    solenoidSlider.setMax(1.0);
                    solenoidSlider.setBlockIncrement(0.25);
                    solenoidSlider.setShowTickLabels(true);
                    solenoidSlider.setShowTickMarks(true);

                    grid.add(solenoidSlider, 1, thisRowIndex);
                    Bindings.bindBidirectional(((FauxbotDoubleSolenoid)actuator).getProperty(), solenoidSlider.valueProperty());
                }
            }
        }

        // construct Canvas
        this.canvas = new Canvas(200, 200);
        grid.add(this.canvas, 2, 2, 2, rowCount);

        Parent root = grid;
        int width = 605;
        int height;
        if (rowCount < 5)
        {
            height = 200;
        }
        else if (rowCount < 15)
        {
            height = 200 + (rowCount - 5) * 30;
        }
        else
        {
            root = new ScrollPane(grid);
            height = 500;
        }

        Scene scene = new Scene(root, width, height);

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
}
