package frc.robot.simulation;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import frc.lib.robotprovider.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// import javafx.scene.canvas.Canvas;
// import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.image.Image;
// import javafx.scene.paint.Color;

@Singleton
public class ElevatorSimulator extends SimulatorBase
{
    private static final FauxbotSensorConnection EncoderAChannel = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, FauxbotEncoder.class, 0);
    private static final FauxbotSensorConnection EncoderBChannel = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, FauxbotEncoder.class, 1);
    private static final FauxbotActuatorConnection MotorChannel = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PWM, 0);

    private final FauxbotSensorConnection[] sensors =
        new FauxbotSensorConnection[]
        {
            ElevatorSimulator.EncoderAChannel,
            ElevatorSimulator.EncoderBChannel,
        };

    private final FauxbotActuatorConnection[] actuators =
        new FauxbotActuatorConnection[]
        {
            ElevatorSimulator.MotorChannel,
        };

    @SuppressWarnings("serial")
    private final Map<FauxbotSensorConnection, String> sensorNameMap = new HashMap<FauxbotSensorConnection, String>()
    {
        {
            this.put(ElevatorSimulator.EncoderAChannel, "Elevator encoder");
            this.put(ElevatorSimulator.EncoderBChannel, "Elevator encoder");
        }
    };

    @SuppressWarnings("serial")
    private final Map<FauxbotActuatorConnection, String> motorNameMap = new HashMap<FauxbotActuatorConnection, String>()
    {
        {
            this.put(ElevatorSimulator.MotorChannel, "Elevator motor");
        }
    };

    private static final double ElevatorMinHeight = 0.0;
    private static final double ElevatorMaxHeight = 250.0;

    private static final double MotorStrength = 700.0;
    private static final double Gravity = -386.0;

    private static final double ElevatorMinVelocity = -20.0;
    private static final double ElevatorMaxVelocity = 20.0;

    private static final double[] FloorHeightPercentages = new double[] { 0.0, 0.2, 0.4, 0.6, 0.8 };
    private static final double PercentageAllowance = 0.01;
    private static final double ElevatorCarWidth = 15.0;
    private static final double ElevatorCarHeight = 35.0;

    private FileInputStream elevatorPersonInputStream;
    // private Image elevatorPerson;

    private double prevHeight;
    private double prevTime;
    private double prevVelocity;

    @Inject
    public ElevatorSimulator()
    {
        try
        {
            this.elevatorPersonInputStream = new FileInputStream(this.getClass().getResource("/images/stickFigure.png").getPath());
        }
        catch (Exception e)
        {
            System.out.println("ERROR: INVALID IMAGE");
        }

        // this.elevatorPerson = new Image(this.elevatorPersonInputStream);

        this.prevHeight = 0.0;
        this.prevTime = 0.0;
        this.prevVelocity = 0.0;
    }

    @Override
    public FauxbotSensorConnection[] getSensors()
    {
        return this.sensors;
    }

    @Override
    public FauxbotActuatorConnection[] getActuators()
    {
        return this.actuators;
    }

    @Override
    public String getSensorName(FauxbotSensorConnection connection)
    {
        if (this.sensorNameMap.containsKey(connection))
        {
            return this.sensorNameMap.get(connection);
        }

        return "Sensor " + connection;
    }

    @Override
    public double getSensorMin(FauxbotSensorConnection connection)
    {
        return ElevatorSimulator.ElevatorMinHeight;
    }

    @Override
    public double getSensorMax(FauxbotSensorConnection connection)
    {
        return ElevatorSimulator.ElevatorMaxHeight;
    }

    @Override
    public String getActuatorName(FauxbotActuatorConnection connection)
    {
        if (this.motorNameMap.containsKey(connection))
        {
            return this.motorNameMap.get(connection);
        }

        return "Motor " + connection;
    }

    @Override
    public double getMotorMin(FauxbotActuatorConnection connection)
    {
        return -1.0;
    }

    @Override
    public double getMotorMax(FauxbotActuatorConnection connection)
    {
        return 1.0;
    }

    @Override
    public boolean shouldSimulatePID()
    {
        return true;
    }

    @Override
    public void update()
    {
        double currTime = Calendar.getInstance().getTime().getTime() / 1000.0;
        double currHeight = this.prevHeight;
        double currVelocity = this.prevVelocity;

        double motorPower = 0.0;
        FauxbotActuatorBase actuator = FauxbotActuatorManager.get(ElevatorSimulator.MotorChannel);
        if (actuator != null && actuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase motor = (FauxbotMotorBase)actuator;
            motorPower = motor.get();
        }

        double dt = currTime - this.prevTime;

        currVelocity += motorPower * ElevatorSimulator.MotorStrength * dt + ElevatorSimulator.Gravity * dt;
        currHeight += (currVelocity * dt);

        if (currVelocity > ElevatorSimulator.ElevatorMaxVelocity)
        {
            currVelocity = ElevatorSimulator.ElevatorMaxVelocity;
        }
        else if (currVelocity < ElevatorSimulator.ElevatorMinVelocity)
        {
            currVelocity = ElevatorSimulator.ElevatorMinVelocity;
        }

        if (this.prevHeight > ElevatorSimulator.ElevatorMaxHeight)
        {
            currHeight = ElevatorSimulator.ElevatorMaxHeight;
            currVelocity = 0.0;
        }
        else if (this.prevHeight < ElevatorSimulator.ElevatorMinHeight)
        {
            currHeight = ElevatorSimulator.ElevatorMinHeight;
            currVelocity = 0.0;
        }

        this.prevHeight = currHeight;
        this.prevTime = currTime;
        this.prevVelocity = currVelocity;

        FauxbotSensorBase sensor = FauxbotSensorManager.get(ElevatorSimulator.EncoderAChannel);
        if (sensor != null && sensor instanceof FauxbotEncoder)
        {
            FauxbotEncoder encoder = (FauxbotEncoder)sensor;
            encoder.set((int)this.prevHeight);
        }
    }

    /**
     * Draw a frame of animation based on the current state of the simulation.
     * Remember that (0, 0) is at the top left!
     */
    // @Override
    // public void draw(Canvas canvas)
    // {
    //     double elevatorHeightRatio = this.prevHeight / (ElevatorSimulator.ElevatorMaxHeight - ElevatorSimulator.ElevatorMinHeight);

    //     double canvasHeight = canvas.getHeight();
    //     double canvasWidth = canvas.getWidth();
    //     GraphicsContext gc = canvas.getGraphicsContext2D();
    //     gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight);

    //     // cycle through the floors:
    //     Color elevatorCarColor = Color.RED;
    //     for (double ratio : ElevatorSimulator.FloorHeightPercentages)
    //     {
    //         // if we are within a small allowance from the floor, change elevator color to Green
    //         if (Math.abs(elevatorHeightRatio - ratio) < ElevatorSimulator.PercentageAllowance)
    //         {
    //             elevatorCarColor = Color.GREEN;
    //         }

    //         // draw the floor:
    //         gc.setStroke(Color.BLACK);
    //         gc.setLineWidth(1.0);
    //         gc.strokeLine(ElevatorSimulator.ElevatorCarWidth, (1 - ratio) * canvasHeight, canvasWidth, (1 - ratio) * canvasHeight); 
    //     }

    //     // draw the elevator car:
    //     gc.setStroke(elevatorCarColor);
    //     gc.setLineWidth(1.0);
    //     gc.strokeRect(
    //         0.0,
    //         (1.0 - elevatorHeightRatio) * canvasHeight - ElevatorSimulator.ElevatorCarHeight,
    //         ElevatorSimulator.ElevatorCarWidth,
    //         ElevatorSimulator.ElevatorCarHeight);

    //     // draw the elevator rider:
    //     gc.drawImage(this.elevatorPerson, 0.0, (1.0 - elevatorHeightRatio) * canvasHeight - ElevatorSimulator.ElevatorCarHeight, 
    //                     ElevatorSimulator.ElevatorCarWidth,  ElevatorSimulator.ElevatorCarHeight);
    // }
}
