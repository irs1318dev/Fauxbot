package frc.robot.simulation;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;
import frc.robot.HardwareConstants;
import frc.robot.IRealWorldSimulator;
import frc.robot.TuningConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

@Singleton
public class ForkliftSimulator implements IRealWorldSimulator
{
    private static final double WHEEL_SEPARATION_DISTANCE = 10.0; // in inches

    private static final FauxbotActuatorConnection LeftMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PWM, 0);
    private static final FauxbotActuatorConnection RightMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PWM, 1);
    private static final FauxbotActuatorConnection LifterForwardConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0A, 7);
    private static final FauxbotActuatorConnection LifterReverseConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0B, 8);

    private final FauxbotSensorConnection[] sensors =
        new FauxbotSensorConnection[]
        {
        };

    private final FauxbotActuatorConnection[] actuators =
        new FauxbotActuatorConnection[]
        {
            ForkliftSimulator.LeftMotorConnection,
            ForkliftSimulator.RightMotorConnection,
            ForkliftSimulator.LifterForwardConnection,
            ForkliftSimulator.LifterReverseConnection,
        };

    @SuppressWarnings("serial")
    private final Map<FauxbotActuatorConnection, String> actuatorNameMap = new HashMap<FauxbotActuatorConnection, String>()
    {
        {
            this.put(ForkliftSimulator.LeftMotorConnection, "Left Drive Motor");
            this.put(ForkliftSimulator.RightMotorConnection, "Right Drive Motor");
            this.put(ForkliftSimulator.LifterForwardConnection, "Lifter solenoid");
            this.put(ForkliftSimulator.LifterReverseConnection, "Lifter solenoid");
        }
    };

    private double leftPower;
    private double rightPower;

    private boolean forkliftUp;

    private Image forkliftDownImage;
    private Image forkliftUpImage;

    // odometry coordinates (x forward, y left, angle counter-clockwise)
    private double x;
    private double y;
    private double angle;
    private double prevLeftDistance;
    private double prevRightDistance;
    private double prevTime;

    @Inject
    public ForkliftSimulator()
    {
        this.leftPower = 0.0;
        this.rightPower = 0.0;

        // start with it either up or down
        this.forkliftUp = Math.random() >= 0.5;

        this.x = 25.0;
        this.y = 25.0;
        this.angle = 0.0;
        this.prevLeftDistance = 0.0;
        this.prevRightDistance = 0.0;
        this.prevTime = Calendar.getInstance().getTime().getTime() / 1000.0;

        try
        {
            FileInputStream forkliftDownInputStream = new FileInputStream(this.getClass().getResource("/images/forklift_down.png").getPath());
            this.forkliftDownImage = new Image(forkliftDownInputStream);

            FileInputStream forkliftUpInputStream = new FileInputStream(this.getClass().getResource("/images/forklift_up.png").getPath());
            this.forkliftUpImage = new Image(forkliftUpInputStream);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: INVALID IMAGE");             
        }
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
        return "Sensor " + connection;
    }

    @Override
    public double getSensorMin(FauxbotSensorConnection connection)
    {
        return -1.0;
    }

    @Override
    public double getSensorMax(FauxbotSensorConnection connection)
    {
        return 1.0;
    }

    @Override
    public String getActuatorName(FauxbotActuatorConnection connection)
    {
        if (this.actuatorNameMap.containsKey(connection))
        {
            return this.actuatorNameMap.get(connection);
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
        FauxbotActuatorBase leftDriveActuator = FauxbotActuatorManager.get(ForkliftSimulator.LeftMotorConnection);
        FauxbotActuatorBase rightDriveActuator = FauxbotActuatorManager.get(ForkliftSimulator.RightMotorConnection);
        FauxbotActuatorBase lifterActuator = FauxbotActuatorManager.get(ForkliftSimulator.LifterForwardConnection);

        if (leftDriveActuator != null && leftDriveActuator instanceof FauxbotMotorBase && rightDriveActuator != null && rightDriveActuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase leftDriveMotor = (FauxbotMotorBase)leftDriveActuator;
            FauxbotMotorBase rightDriveMotor = (FauxbotMotorBase)rightDriveActuator;
            this.leftPower = leftDriveMotor.get();
            this.rightPower = rightDriveMotor.get();
        }

        if (lifterActuator != null && lifterActuator instanceof FauxbotDoubleSolenoid)
        {
            FauxbotDoubleSolenoid lifterSolenoid = (FauxbotDoubleSolenoid)lifterActuator;
            this.forkliftUp = lifterSolenoid.get() == DoubleSolenoidValue.Forward;
        }

        this.updateOdometry();
    }

    private void updateOdometry()
    {
        double currTime = Calendar.getInstance().getTime().getTime() / 1000.0;
        double deltaT = this.prevTime - currTime;

        // check the current distance recorded by the encoders
        double leftDistance = this.prevLeftDistance + this.leftPower * 1.0 * deltaT;
        double rightDistance = this.prevRightDistance + this.rightPower * 1.0 * deltaT;

        // calculate the angle (in radians) based on the total distance traveled
        double angleR = (rightDistance - leftDistance) / ForkliftSimulator.WHEEL_SEPARATION_DISTANCE;

        // calculate the average distance traveled
        double averagePositionChange = ((leftDistance - this.prevLeftDistance) + (rightDistance - this.prevRightDistance)) / 2.0;

        // calculate the change since last time, and update our relative position
        double newX = this.x + averagePositionChange * Math.cos(angleR);
        double newY = this.y + averagePositionChange * Math.sin(angleR);

        double newAngle = (angleR * 360.0 / (2.0 * Math.PI)) % 360;

        // TODO: check for collision with walls!!
        this.x = newX;
        this.y = newY;
        this.angle = newAngle;

        // record distance for next time
        this.prevLeftDistance = leftDistance;
        this.prevRightDistance = rightDistance;

        this.prevTime = currTime;
    }

    @Override
    public void draw(Canvas canvas)
    {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        double halfHeight = canvasHeight / 2.0;
        double powerIndicatorWidth = canvasWidth / 10.0;

        double leftHeight = Math.abs(leftPower * halfHeight);
        double leftTop;
        if (leftPower > 0.0)
        {
            leftTop = halfHeight - leftHeight;
        }
        else
        {
            leftTop = halfHeight;
        }

        gc.setFill(Color.BLUE); 
        gc.fillRect(0, leftTop, powerIndicatorWidth, leftHeight);

        double rightHeight = Math.abs(rightPower * halfHeight);
        double rightTop;
        if (rightPower > 0.0)
        {
            rightTop = halfHeight - rightHeight;
        }
        else
        {
            rightTop = halfHeight;
        }

        gc.setFill(Color.RED); 
        gc.fillRect(powerIndicatorWidth * 2, rightTop, powerIndicatorWidth, rightHeight);

        // draw the forklift in its current state
        Image forkliftToDraw = null;
        if (this.forkliftUp)
        {
            forkliftToDraw = this.forkliftUpImage;
        }
        else
        {
            forkliftToDraw = this.forkliftDownImage;
        }

        gc.drawImage(
            forkliftToDraw,
            canvasWidth / 2.0,
            canvasHeight / 4.0, 
            canvasWidth / 2.0,
            canvasHeight / 2.0);
    } 
}
