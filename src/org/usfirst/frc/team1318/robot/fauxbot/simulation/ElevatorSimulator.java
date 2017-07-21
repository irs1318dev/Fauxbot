package org.usfirst.frc.team1318.robot.fauxbot.simulation;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.fauxbot.IRealWorldSimulator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import edu.wpi.first.wpilibj.SensorManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

@Singleton
public class ElevatorSimulator implements IRealWorldSimulator
{
    private static final int EncoderAChannel = 0;
    private static final int EncoderBChannel = 1;
    private static final int MotorChannel = 0;

    @SuppressWarnings("serial")
    private final Map<Integer, String> sensorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(ElevatorSimulator.EncoderAChannel, "Elevator encoder");
            this.put(ElevatorSimulator.EncoderBChannel, "Elevator encoder");
        }
    };

    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(ElevatorSimulator.MotorChannel, "Elevator motor");
        }
    };

    private static final double ElevatorMinHeight = 0.0;
    private static final double ElevatorMaxHeight = 250.0;

    private double currentElevatorHeight;

    @Inject
    public ElevatorSimulator()
    {
        this.currentElevatorHeight = 0.0;
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
        return ElevatorSimulator.ElevatorMinHeight;
    }

    public double getEncoderMax(int channel)
    {
        return ElevatorSimulator.ElevatorMaxHeight;
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
        ActuatorBase actuator = ActuatorManager.get(ElevatorSimulator.MotorChannel);
        if (actuator != null && actuator instanceof MotorBase)
        {
            MotorBase motor = (MotorBase)actuator;
            double motorPower = motor.get();
            this.currentElevatorHeight += motorPower;
        }

        Encoder encoder = (Encoder)SensorManager.get(ElevatorSimulator.EncoderAChannel);
        if (encoder != null)
        {
            encoder.set(this.currentElevatorHeight);
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.setFill(Color.RED);
        //gc.setLineWidth(5.0);
        //gc.strokeLine(0.0, 0.0, this.numUpdatesOpened, this.numUpdatesOpened);
    }
}
