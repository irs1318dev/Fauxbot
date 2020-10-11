package frc.robot.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import frc.robot.IRealWorldSimulator;
import frc.robot.common.robotprovider.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

@Singleton
public class ShooterSimulator implements IRealWorldSimulator
{
    private static final FauxbotActuatorConnection AngleMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 0);
    private static final FauxbotActuatorConnection FlyWheelMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 1);
    private static final FauxbotSensorConnection AngleEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, 0);
    private static final FauxbotSensorConnection FlyWheelEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, 1);
    private static final FauxbotActuatorConnection KickerForwardConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0A, 7);
    private static final FauxbotActuatorConnection KickerReverseConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0B, 8);

    private final FauxbotSensorConnection[] sensors =
        new FauxbotSensorConnection[]
        {
            ShooterSimulator.AngleEncoderConnection,
            ShooterSimulator.FlyWheelEncoderConnection,
        };

    private final FauxbotActuatorConnection[] actuators =
        new FauxbotActuatorConnection[]
        {
            ShooterSimulator.AngleMotorConnection,
            ShooterSimulator.FlyWheelMotorConnection,
            ShooterSimulator.KickerForwardConnection,
            ShooterSimulator.KickerReverseConnection,
        };

    @SuppressWarnings("serial")
    private final Map<FauxbotSensorConnection, String> sensorNameMap = new HashMap<FauxbotSensorConnection, String>()
    {
        {
            this.put(ShooterSimulator.AngleEncoderConnection, "Angle encoder");
            this.put(ShooterSimulator.FlyWheelEncoderConnection, "Fly Wheel encoder");
        }
    };

    @SuppressWarnings("serial")
    private final Map<FauxbotActuatorConnection, String> motorNameMap = new HashMap<FauxbotActuatorConnection, String>()
    {
        {
            this.put(ShooterSimulator.AngleMotorConnection, "Angle Motor");
            this.put(ShooterSimulator.FlyWheelMotorConnection, "Fly Wheel Motor");
            this.put(ShooterSimulator.KickerForwardConnection, "Kicker solenoid");
            this.put(ShooterSimulator.KickerReverseConnection, "Kicker solenoid");
        }
    };

    private static final double AngleMinPosition = 0.0;
    private static final double AngleMaxPosition = 90.0;

    private static final double AngleMotorPower = 40.0;
    private static final double AngleSlowRatio = 1.0; // friction?
    private static final double AngleMinAbsoluteVelocity = 0.1;
    private static final double FlyWheelMotorPower = 300.0;
    private static final double FlyWheelSlowRatio = 1.0; // friction?
    private static final double FlyWheelMinAbsoluteVelocity = 0.1;

    private static final double AngleMinVelocity = -22.5;
    private static final double AngleMaxVelocity = 22.5;
    private static final double FlyWheelMinVelocity = 0.0;
    private static final double FlyWheelMaxVelocity = 400.0;

    private static final double GravityAcceleration = 384.0;

    private double prevTime;
    private boolean prevKick;
    private double prevAngle;
    private double prevAngleVelocity;
    private double prevWheelVelocity;

    private double ballHeight;
    private double ballDistance;
    private double ballHorizontalVelocity;
    private double ballVerticalVelocity;

    @Inject
    public ShooterSimulator()
    {
        this.prevTime = 0.0;
        this.prevKick = false;
        this.prevAngle = 0.0;
        this.prevAngleVelocity = 0.0;
        this.prevWheelVelocity = 0.0;
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
    public boolean getSensorTextBox(FauxbotSensorConnection connection)
    {
        return false;
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
        if (connection == ShooterSimulator.AngleEncoderConnection)
        {
            return ShooterSimulator.AngleMinPosition;
        }

        return ShooterSimulator.FlyWheelMinVelocity;
    }

    @Override
    public double getSensorMax(FauxbotSensorConnection connection)
    {
        if (connection == ShooterSimulator.AngleEncoderConnection)
        {
            return ShooterSimulator.AngleMaxPosition;
        }

        return ShooterSimulator.FlyWheelMaxVelocity;
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
        return false;
    }

    @Override
    public void update()
    {
        double currTime = Calendar.getInstance().getTime().getTime() / 1000.0;
        double currAngle = this.prevAngle;
        double currAngleVelocity = this.prevAngleVelocity;
        double currWheelVelocity = this.prevWheelVelocity;

        double angleMotorPower = 0.0;
        FauxbotActuatorBase angleActuator = FauxbotActuatorManager.get(ShooterSimulator.AngleMotorConnection);
        if (angleActuator != null && angleActuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase angleMotor = (FauxbotMotorBase)angleActuator;
            angleMotorPower = angleMotor.get();
        }

        double flyWheelMotorPower = 0.0;
        FauxbotActuatorBase flyWheelActuator = FauxbotActuatorManager.get(ShooterSimulator.FlyWheelMotorConnection);
        if (flyWheelActuator != null && flyWheelActuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase flyWheelMotor = (FauxbotMotorBase)flyWheelActuator;
            flyWheelMotorPower = flyWheelMotor.get();
        }

        boolean currKick = false;
        FauxbotActuatorBase kickerActuator = FauxbotActuatorManager.get(ShooterSimulator.KickerForwardConnection);
        if (kickerActuator != null && kickerActuator instanceof FauxbotDoubleSolenoid)
        {
            FauxbotDoubleSolenoid kickerSolenoid = (FauxbotDoubleSolenoid)kickerActuator;
            currKick = kickerSolenoid.get() == DoubleSolenoidValue.Forward;
        }

        double dt = currTime - this.prevTime;

        // accelerate based on percentage of Shooter power
        currAngleVelocity += angleMotorPower * ShooterSimulator.AngleMotorPower * dt;
        currWheelVelocity += flyWheelMotorPower * ShooterSimulator.FlyWheelMotorPower * dt;

        // decelerate based on slowing ratio (friction)
        currAngleVelocity -= ShooterSimulator.AngleSlowRatio * currAngleVelocity * dt;
        currWheelVelocity -= ShooterSimulator.FlyWheelSlowRatio * currWheelVelocity * dt;

        if (currAngleVelocity > ShooterSimulator.AngleMaxVelocity)
        {
            currAngleVelocity = ShooterSimulator.AngleMaxVelocity;
        }
        else if (currAngleVelocity < ShooterSimulator.AngleMinVelocity)
        {
            currAngleVelocity = ShooterSimulator.AngleMinVelocity;
        }
        else if (Math.abs(currAngleVelocity) < ShooterSimulator.AngleMinAbsoluteVelocity)
        {
            currAngleVelocity = 0.0;
        }

        if (currWheelVelocity > ShooterSimulator.FlyWheelMaxVelocity)
        {
            currWheelVelocity = ShooterSimulator.FlyWheelMaxVelocity;
        }
        else if (currWheelVelocity < ShooterSimulator.FlyWheelMinVelocity)
        {
            currWheelVelocity = ShooterSimulator.FlyWheelMinVelocity;
        }
        else if (Math.abs(currWheelVelocity) < ShooterSimulator.FlyWheelMinAbsoluteVelocity)
        {
            currWheelVelocity = 0.0;
        }

        currAngle += (currAngleVelocity * dt);

        if (currAngle > ShooterSimulator.AngleMaxPosition)
        {
            currAngle = ShooterSimulator.AngleMaxPosition;
            currAngleVelocity = 0.0;
        }
        else if (currAngle < ShooterSimulator.AngleMinPosition)
        {
            currAngle = ShooterSimulator.AngleMinPosition;
            currAngleVelocity = 0.0;
        }

        if (!this.prevKick && currKick)
        {
            double angle = currAngle * Math.PI / 180.0;
            this.ballHorizontalVelocity = Math.cos(angle) * currWheelVelocity;
            this.ballVerticalVelocity = Math.sin(angle) * currWheelVelocity;

            // have the starting location be where the front edge of the hood meets the fly-wheel 
            double angleRad = (this.prevAngle + 90.0) * Math.PI / 180.0;
            this.ballDistance = 11 + 7.5 * Math.cos(angleRad) - 2.5;
            this.ballHeight = 11 + 7.5 * Math.sin(angleRad) + 2.5;
        }
        else if (this.ballHorizontalVelocity < 0.0 ||
            this.ballDistance > 200.0)
        {
            this.ballHorizontalVelocity = -1.0;
            this.ballVerticalVelocity = 0.0;
            this.ballDistance = 0.0;
            this.ballHeight = 0.0;
        }
        else
        {
            this.ballVerticalVelocity -= ShooterSimulator.GravityAcceleration * dt;
            this.ballHeight += this.ballVerticalVelocity * dt;
            this.ballDistance += this.ballHorizontalVelocity * dt;
        }

        this.prevTime = currTime;
        this.prevKick = currKick;
        this.prevAngle = currAngle;
        this.prevAngleVelocity = currAngleVelocity;
        this.prevWheelVelocity = currWheelVelocity;

        FauxbotSensorBase angleSensor = FauxbotSensorManager.get(ShooterSimulator.AngleEncoderConnection);
        if (angleSensor != null && angleSensor instanceof FauxbotEncoder)
        {
            FauxbotEncoder angleEncoder = (FauxbotEncoder)angleSensor;
            angleEncoder.set((int)this.prevAngle);
        }

        FauxbotSensorBase wheelSensor = FauxbotSensorManager.get(ShooterSimulator.FlyWheelEncoderConnection);
        if (wheelSensor != null && wheelSensor instanceof FauxbotEncoder)
        {
            FauxbotEncoder wheelEncoder = (FauxbotEncoder)wheelSensor;
            wheelEncoder.setRate(this.prevWheelVelocity);
        }
    }

    /**
     * Draw a frame of animation based on the current state of the simulation.
     * Remember that (0, 0) is at the top left!
     */
    @Override
    public void draw(Canvas canvas)
    {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight);

        // draw the shooter apparatus:
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1.0);

        // fly-wheel:
        gc.fillOval(6, canvasHeight - 16, 10, 10);

        // hood:
        gc.strokeArc(1, canvasHeight - 21, 20, 20, this.prevAngle + 90.0, 90.0, ArcType.OPEN);

        // draw the ball
        gc.setStroke(Color.RED);
        gc.setFill(Color.RED);
        if (this.ballDistance != 0.0 && this.ballHeight != 0.0)
        {
            gc.fillOval(this.ballDistance, canvasHeight - this.ballHeight, 5, 5);
        }
    }
}
