package org.usfirst.frc.team1318.robot.fauxbot.simulation;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.fauxbot.IRealWorldSimulator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import edu.wpi.first.wpilibj.SensorManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@Singleton
public class GarageDoorSimulator implements IRealWorldSimulator
{
    private static final int ThroughBeamSensorChannel = 0;
    private static final int OpenSensorChannel = 1;
    private static final int ClosedSensorChannel = 2;
    private static final int MotorChannel = 0;

    @SuppressWarnings("serial")
    private final Map<Integer, String> sensorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(GarageDoorSimulator.ThroughBeamSensorChannel, "Through-Beam sensor");
            this.put(GarageDoorSimulator.OpenSensorChannel, "Open sensor");
            this.put(GarageDoorSimulator.ClosedSensorChannel, "Closed sensor");
        }
    };

    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(GarageDoorSimulator.MotorChannel, "Door motor");
        }
    };

    private static final int GarageFullyOpened = 250;

    private GarageState garageState;
    private double amountOpened;

    @Inject
    public GarageDoorSimulator()
    {
        this.garageState = GarageState.Stopped;
        this.amountOpened = 0.0;
    }

    public String getSensorName(int channel)
    {
        if (this.sensorNameMap.containsKey(channel))
        {
            return this.sensorNameMap.get(channel);
        }

        return "Sensor " + channel;
    }

    public double getEncoderMin(int channel)
    {
        return 0.0;
    }

    public double getEncoderMax(int channel)
    {
        return 1.0;
    }

    public String getActuatorName(int channel)
    {
        if (this.motorNameMap.containsKey(channel))
        {
            return this.motorNameMap.get(channel);
        }

        return "Motor " + channel;
    }

    public void update()
    {
        ActuatorBase actuator = ActuatorManager.get(GarageDoorSimulator.MotorChannel);
        if (actuator != null && actuator instanceof MotorBase)
        {
            MotorBase motor = (MotorBase)actuator;
            double motorPower = motor.get();
            if (motorPower > 0 && this.garageState != GarageState.Opening)
            {
                this.garageState = GarageState.Opening;
            }
            else if (motorPower < 0 && this.garageState != GarageState.Closing)
            {
                this.garageState = GarageState.Closing;
            }
            else if (motorPower == 0 && this.garageState != GarageState.Stopped)
            {
                this.garageState = GarageState.Stopped;
            }

            this.amountOpened += motorPower;
        }

        DigitalInput openSensor = (DigitalInput)SensorManager.get(GarageDoorSimulator.OpenSensorChannel);
        DigitalInput closedSensor = (DigitalInput)SensorManager.get(GarageDoorSimulator.ClosedSensorChannel);

        if (openSensor != null && closedSensor != null)
        {
            openSensor.set(false);
            closedSensor.set(false);
            if (this.amountOpened >= GarageDoorSimulator.GarageFullyOpened)
            {
                openSensor.set(true);
            }
            else if (this.amountOpened <= 0)
            {
                closedSensor.set(true);
            }
        }
    }

    public enum GarageState
    {
        Stopped,
        Opening,
        Closing;
    }

    @Override
    public void draw(Canvas canvas)
    {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight);

        double openRatio = this.amountOpened / GarageDoorSimulator.GarageFullyOpened;
        if (openRatio == 1.0)
        {
            gc.setStroke(Color.GREEN);
        }
        else
        {
            gc.setStroke(Color.RED);
        }

        gc.setLineWidth(4.0);

        // bars
        if (openRatio < 0.5)
        {
            gc.strokeLine(0.0, canvasHeight / 2.0, canvasWidth, canvasHeight / 2.0);
        }

        gc.strokeRect(0.0, 0.0, canvasWidth, (1 - openRatio) * canvasHeight);
    }
}
