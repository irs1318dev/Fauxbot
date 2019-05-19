package frc.robot;

import java.io.IOException;

import frc.robot.ElectronicsConstants;
import frc.robot.FauxbotModule;
import frc.robot.RobotMode;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.MacroOperation;
import frc.robot.driver.Operation;
import frc.robot.driver.common.IButtonMap;
import frc.robot.driver.common.UserInputDeviceButton;
import frc.robot.driver.common.buttons.ButtonType;
import frc.robot.driver.common.descriptions.*;
import frc.robot.simulation.*;

import javafx.application.*;
import javafx.beans.binding.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class FauxbotApplication extends Application
{
    private Stage primaryStage;

    private FauxbotRunner runner;
    private Thread runnerThread;

    private IRealWorldSimulator simulator;

    private CoreRobot<FauxbotModule> robot;
    private Simulation desiredSimulation;

    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fauxbot");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        String fontDefault = "Arial";

        Label simulationTitle = new Label("Select Simulation");
        simulationTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 30));
        simulationTitle.setMaxWidth(Double.MAX_VALUE);
        simulationTitle.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        ToggleGroup simulationGroup = new ToggleGroup();
        Simulation[] allSimulationValues = Simulation.values();
        RadioButton[] simulationButtons = new RadioButton[allSimulationValues.length];
        for (int i = 0; i < allSimulationValues.length; i++)
        {
            Simulation value = allSimulationValues[i];

            RadioButton button = new RadioButton(value.toString());
            button.setUserData(value);
            button.setToggleGroup(simulationGroup);
            simulationButtons[i] = button;

            if (i == 0)
            {
                this.desiredSimulation = value;
                button.setSelected(true);
            }
        }

        hBox.getChildren().addAll(simulationButtons);

        simulationGroup.selectedToggleProperty().addListener(
            (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) ->
            {
                this.desiredSimulation = (Simulation)newValue.getUserData(); 
            });

        Button startSimulationButton = new Button("Start Simulation");
        startSimulationButton.setOnMouseClicked(
            (MouseEvent event) ->
            {
                Scene simulationScene = this.prepareScene();
                this.primaryStage.setScene(simulationScene);
                this.primaryStage.show();
            });

        vBox.getChildren().addAll(simulationTitle, hBox, startSimulationButton);

        Scene scene = new Scene(vBox, 605, 200);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public Scene prepareScene()
    {
        String simulationName = null;
        FauxbotModule desiredModule = null;;
        switch (this.desiredSimulation)
        {
            case Elevator:
                simulationName = "Elevator Simulation";
                desiredModule = new ElevatorFauxbotModule();
                break;

            case Forklift:
                simulationName = "Forklift Simulation";
                desiredModule = new ForkliftFauxbotModule();
                break;
            
            case GarageDoor:
                simulationName = "GarageDoor Simulation";
                desiredModule = new GarageDoorFauxbotModule();
                break;
        }

        this.robot = new CoreRobot<FauxbotModule>(desiredModule);

        this.simulator = this.robot.getInjector().getInstance(IRealWorldSimulator.class);
        this.runner = new FauxbotRunner(this.robot, this);
        this.runnerThread = new Thread(this.runner);
        this.robot.robotInit();

        IButtonMap buttonMap = this.robot.getInjector().getInstance(IButtonMap.class);

        String fontDefault = "Arial";

        Label simulationTitle = new Label(simulationName);
        simulationTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 30));
        simulationTitle.setMaxWidth(Double.MAX_VALUE);
        simulationTitle.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int rowCount = 0;

        Text modeTitle = new Text("Mode");
        modeTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
        grid.add(modeTitle, 0, rowCount, 1, 1);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        ToggleGroup modeGroup = new ToggleGroup();
        RobotMode[] allModeValues = RobotMode.values();
        RadioButton[] modeButtons = new RadioButton[allModeValues.length];
        for (int i = 0; i < allModeValues.length; i++)
        {
            RobotMode value = allModeValues[i];

            RadioButton button = new RadioButton(value.toString());
            button.setUserData(value);
            button.setToggleGroup(modeGroup);
            modeButtons[i] = button;

            if (value == RobotMode.Disabled)
            {
                button.setSelected(true);
            }
        }

        hBox.getChildren().addAll(modeButtons);
        grid.add(hBox, 1, rowCount, 3, 1);

        modeGroup.selectedToggleProperty().addListener(
            new ChangeListener<Toggle>()
            {
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
                {
                    runner.setMode((RobotMode)newValue.getUserData());
                } 
            });

        rowCount++;

        boolean firstOperation = true;
        for (Operation op : Operation.values())
        {
            OperationDescription description = buttonMap.getOperationSchema().getOrDefault(op, null);
            if (description != null)
            {
                if (firstOperation)
                {
                    Text buttonsTitle = new Text("Buttons");
                    buttonsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
                    grid.add(buttonsTitle, 0, rowCount++, 2, 1);
                    firstOperation = false;
                }

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

        if (!firstOperation)
        {
            // if there were any buttons in the list, add a spacer:
            rowCount++;
        }

        boolean firstMacro = true;
        if (MacroOperation.values().length > 0)
        {
            for (MacroOperation op : MacroOperation.values())
            {
                MacroOperationDescription description = buttonMap.getMacroOperationSchema().getOrDefault(op, null);
                if (description != null)
                {
                    if (firstMacro)
                    {
                        Text macrosTitle = new Text("Macros");
                        macrosTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
                        grid.add(macrosTitle, 0, rowCount++, 2, 1);
                        firstMacro = false;
                    }

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

            if (!firstMacro)
            {
                // if there were any macros in the list, add a spacer:
                rowCount++;
            }
        }

        boolean firstSensor = true;
        for (FauxbotSensorConnection connection : FauxbotSensorManager.sensorMap.keySet())
        {
            FauxbotSensorBase sensor = FauxbotSensorManager.get(connection);
            if (sensor != null)
            {
                if (firstSensor)
                {
                    Text sensorsTitle = new Text("Sensors");
                    sensorsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
                    grid.add(sensorsTitle, 0, rowCount++, 2, 1);
                    firstSensor = false;
                }

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

        if (!firstSensor)
        {
            // if there were any sensors in the list, add a spacer:
            rowCount++;
        }

        boolean firstActuator = true;
        for (FauxbotActuatorConnection connection : FauxbotActuatorManager.actuatorMap.keySet())
        {
            FauxbotActuatorBase actuator = FauxbotActuatorManager.get(connection);
            if (actuator != null)
            {
                if (firstActuator)
                {
                    Text motorsTitle = new Text("Actuators");
                    motorsTitle.setFont(Font.font(fontDefault, FontWeight.NORMAL, 20));
                    grid.add(motorsTitle, 0, rowCount++, 2, 1);
                    firstActuator = false;
                }

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

        Parent root = new VBox(10, simulationTitle, grid);
        int width = 605;
        int height;
        if (rowCount < 5)
        {
            height = 250;
        }
        else if (rowCount < 8)
        {
            height = 250 + (rowCount - 5) * 30;
        }
        else
        {
            root = new ScrollPane(root);
            height = 500;
        }

        Scene scene = new Scene(root, width, height);

        // start the runner:
        this.runnerThread.start();

        return scene;
    }

    public void refresh()
    {
        this.simulator.update();
        Platform.runLater(
            () ->
            {
                this.simulator.draw(this.canvas);
            });
    }

    @Override
    public void stop() throws Exception
    {
        if (this.runner != null)
        {
            this.runner.stop();
            if (this.runnerThread != null)
            {
                this.runnerThread.join(500);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException
    {
        Application.launch(args);
    }
}
