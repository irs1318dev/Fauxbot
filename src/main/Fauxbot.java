package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Common.Motor;
import main.Common.MotorManager;
import main.Common.Sensor;
import main.Common.AnalogInput;
import main.Common.DigitalInput;
import main.Common.SensorManager;
import main.Driver.Driver;
import main.Driver.Operation;

public class Fauxbot extends Application
{
    @SuppressWarnings("serial")
    private Map<Integer, String> sensorNameMap = new HashMap<Integer, String>()
    {
        {
            put(0, "Through-Beam sensor:");
            put(1, "Open sensor:");
            put(2, "Closed sensor:");
        }
    };

    @SuppressWarnings("serial")
    private Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            put(0, "Door motor:");
        }
    };

    private Driver driver;
    private ComponentManager components;
    private ControllerManager controllers;
    private FauxbotRunner runner;
    private Thread runnerThread;

    public Fauxbot()
    {
        super();

        this.driver = new Driver();
        this.components = new ComponentManager();
        this.controllers = new ControllerManager(this.components);

        this.controllers.setDriver(this.driver);
        this.runner = new FauxbotRunner(controllers);
        this.runnerThread = new Thread(runner);
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

        int rowIndex = 0;
        Text sensorsTitle = new Text("Sensors");
        sensorsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sensorsTitle, 0, rowIndex++, 2, 1);

        for (int i = 0; i <= SensorManager.getHightestPort(); i++)
        {
            Sensor sensor = SensorManager.get(i);
            if (sensor != null)
            {
                String sensorName = "Sensor " + i + ":";
                if (this.sensorNameMap.containsKey(i))
                {
                    sensorName = this.sensorNameMap.get(i);
                }

                int thisRowIndex = rowIndex;
                rowIndex++;

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
            }
        }

        // add a spacer:
        rowIndex++;

        Text buttonsTitle = new Text("Buttons");
        buttonsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(buttonsTitle, 0, rowIndex++, 2, 1);
        for (Operation op : Operation.values())
        {
            Button operationButton = new Button(op.toString());
            operationButton.setOnMouseClicked(
                (MouseEvent event) ->
                {
                    this.driver.pressButton(op);
                });

            grid.add(operationButton, 0, rowIndex++);
        }

        // add a spacer:
        rowIndex++;

        Text motorsTitle = new Text("Motors");
        motorsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(motorsTitle, 0, rowIndex++, 2, 1);
        for (int i = 0; i <= MotorManager.getHightestPort(); i++)
        {
            Motor motor = MotorManager.get(i);
            if (motor != null)
            {
                String motorName = "Motor " + i + ":";
                if (this.motorNameMap.containsKey(i))
                {
                    motorName = this.motorNameMap.get(i);
                }

                int thisRowIndex = rowIndex;
                rowIndex++;

                Label motorNameLabel = new Label(motorName);
                grid.add(motorNameLabel, 0, thisRowIndex);

                Slider sensorSlider = new Slider();
                sensorSlider.setMin(-1.0);
                sensorSlider.setMax(1.0);
                sensorSlider.setBlockIncrement(0.25);
                sensorSlider.setShowTickLabels(true);
                sensorSlider.setShowTickMarks(true);

                grid.add(sensorSlider, 1, thisRowIndex);
                Bindings.bindBidirectional(motor.getProperty(), sensorSlider.valueProperty());
            }
        }

        Scene scene = new Scene(grid, 375, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // start the runner:
        this.runnerThread.start();
    }

    @Override
    public void stop() throws Exception
    {
    	this.runner.stop();
        this.runnerThread.join(500);
    }

    public static void main(String[] args) throws InterruptedException, IOException
    {
        launch(args);
    }
}
