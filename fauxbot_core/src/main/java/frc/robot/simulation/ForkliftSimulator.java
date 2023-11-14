package frc.robot.simulation;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;
import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// import javafx.scene.canvas.Canvas;
// import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.image.Image;
// import javafx.scene.paint.Color;
// import javafx.scene.transform.Affine;
// import javafx.scene.transform.Rotate;

@Singleton
public class ForkliftSimulator extends SimulatorBase
{
    private static final double WHEEL_SEPARATION_DISTANCE = 10.0; // in inches/pixels
    private static final double FORKLIFT_SPEED = 10.0; // in inches/pixels / sec

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

    private static final double FORKLIFT_LENGTH = 20.0; // front to back, in pixels
    private static final double FORKLIFT_WIDTH = 10.0; // left to right, in pixels
    private static final double FORKLIFT_HALF_LENGTH = ForkliftSimulator.FORKLIFT_LENGTH / 2.0;
    private static final double FORKLIFT_HALF_WIDTH = ForkliftSimulator.FORKLIFT_WIDTH / 2.0;

    private static final double MAX_X = 125.0;
    private static final double MAX_Y = 200.0;
    private static final double STARTING_ANGLE_R = Math.PI / 2.0; // 90deg to the left (image-up)
    private static final double STARTING_X = ForkliftSimulator.MAX_X / 2.0;
    private static final double STARTING_Y = ForkliftSimulator.MAX_Y / 2.0;
    private static final double MAX_WALL_DISTANCE =
        Math.sqrt(FORKLIFT_HALF_LENGTH * FORKLIFT_HALF_LENGTH + FORKLIFT_HALF_WIDTH * FORKLIFT_HALF_WIDTH);

    private double leftPower;
    private double rightPower;

    private boolean forkliftUp;

    // private Image forkliftDownImage;
    // private Image forkliftUpImage;

    // odometry coordinates (x forward, y left, angle counter-clockwise)
    private double x;
    private double y;
    private double angle;
    private double prevLeftDistance;
    private double prevRightDistance;

    @Inject
    public ForkliftSimulator()
    {
        this.leftPower = 0.0;
        this.rightPower = 0.0;

        // start with it either up or down
        this.forkliftUp = Math.random() >= 0.5;

        this.x = ForkliftSimulator.STARTING_X;
        this.y = ForkliftSimulator.STARTING_Y;
        this.angle = ForkliftSimulator.STARTING_ANGLE_R * (180.0 / Math.PI);
        this.prevLeftDistance = 0.0;
        this.prevRightDistance = 0.0;

        try
        {
            FileInputStream forkliftDownInputStream = new FileInputStream(this.getClass().getResource("/images/forklift_down.png").getPath());
            // this.forkliftDownImage = new Image(forkliftDownInputStream);

            FileInputStream forkliftUpInputStream = new FileInputStream(this.getClass().getResource("/images/forklift_up.png").getPath());
            // this.forkliftUpImage = new Image(forkliftUpInputStream);
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
    public void act(float delta)
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

        this.updateOdometry(delta);
    }

    private void updateOdometry(float delta)
    {
        // check the current distance recorded by the encoders
        double leftDistance = this.prevLeftDistance + this.leftPower * ForkliftSimulator.FORKLIFT_SPEED * delta;
        double rightDistance = this.prevRightDistance + this.rightPower * ForkliftSimulator.FORKLIFT_SPEED * delta;

        // calculate the angle (in radians) based on the total distance traveled
        double angleR = ForkliftSimulator.STARTING_ANGLE_R + (rightDistance - leftDistance) / ForkliftSimulator.WHEEL_SEPARATION_DISTANCE;

        // calculate the average distance traveled
        double averagePositionChange = ((leftDistance - this.prevLeftDistance) + (rightDistance - this.prevRightDistance)) / 2.0;

        // calculate the change since last time, and update our relative position
        double newX = this.x + averagePositionChange * Math.cos(angleR);
        double newY = this.y + averagePositionChange * Math.sin(angleR);

        double newAngle = (angleR * 360.0 / (2.0 * Math.PI)) % 360.0;

        // quick check for collision with walls
        this.x = ForkliftSimulator.clamp(newX, ForkliftSimulator.MAX_WALL_DISTANCE, ForkliftSimulator.MAX_X - ForkliftSimulator.MAX_WALL_DISTANCE);
        this.y = ForkliftSimulator.clamp(newY, ForkliftSimulator.MAX_WALL_DISTANCE, ForkliftSimulator.MAX_Y - ForkliftSimulator.MAX_WALL_DISTANCE);
        this.angle = newAngle;

        // record distance for next time
        this.prevLeftDistance = leftDistance;
        this.prevRightDistance = rightDistance;
    }

    // @Override
    // public void draw(Canvas canvas)
    // {
    //     double canvasHeight = canvas.getHeight();
    //     double canvasWidth = canvas.getWidth();

    //     GraphicsContext gc = canvas.getGraphicsContext2D();
    //     gc.clearRect(0, 0, canvasWidth, canvasHeight);

    //     gc.setStroke(Color.BLACK);
    //     gc.strokeRect(0, 0, MAX_X, MAX_Y);
    //     gc.save();

    //     gc.transform(new Affine(new Rotate(-this.angle, this.x, ForkliftSimulator.MAX_Y - this.y)));
    //     gc.setFill(Color.RED);
    //     gc.fillRect(
    //         this.x - ForkliftSimulator.FORKLIFT_HALF_LENGTH,
    //         (ForkliftSimulator.MAX_Y - this.y) - ForkliftSimulator.FORKLIFT_HALF_WIDTH,
    //         ForkliftSimulator.FORKLIFT_LENGTH,
    //         ForkliftSimulator.FORKLIFT_WIDTH);
    //     gc.restore();

    //     gc.setFill(Color.BLUE);
    //     gc.fillOval(this.x - 1, (ForkliftSimulator.MAX_Y - this.y) - 1, 2, 2);

    //     /*
    //     double halfHeight = canvasHeight / 2.0;
    //     double powerIndicatorWidth = canvasWidth / 10.0;

    //     double leftHeight = Math.abs(leftPower * halfHeight);
    //     double leftTop;
    //     if (leftPower > 0.0)
    //     {
    //         leftTop = halfHeight - leftHeight;
    //     }
    //     else
    //     {
    //         leftTop = halfHeight;
    //     }

    //     gc.setFill(Color.BLUE); 
    //     gc.fillRect(0, leftTop, powerIndicatorWidth, leftHeight);

    //     double rightHeight = Math.abs(rightPower * halfHeight);
    //     double rightTop;
    //     if (rightPower > 0.0)
    //     {
    //         rightTop = halfHeight - rightHeight;
    //     }
    //     else
    //     {
    //         rightTop = halfHeight;
    //     }

    //     gc.setFill(Color.RED); 
    //     gc.fillRect(powerIndicatorWidth * 2, rightTop, powerIndicatorWidth, rightHeight);
    //     */

    //     // draw the forklift in its current state
    //     Image forkliftToDraw = null;
    //     if (this.forkliftUp)
    //     {
    //         forkliftToDraw = this.forkliftUpImage;
    //     }
    //     else
    //     {
    //         forkliftToDraw = this.forkliftDownImage;
    //     }

    //     gc.drawImage(
    //         forkliftToDraw,
    //         ForkliftSimulator.MAX_X + 5,
    //         canvasHeight / 4.0, 
    //         canvasWidth - (ForkliftSimulator.MAX_X + 5),
    //         canvasHeight / 2.0);
    // }

    private static double clamp(double value, double min, double max)
    {
        if (value < min)
        {
            return min;
        }

        if (value > max)
        {
            return max;
        }

        return value;
    }
}
