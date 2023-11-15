package frc.robot.simulation;

import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ForkliftSimulator extends SimulatorBase
{
    private static final double WHEEL_SEPARATION_DISTANCE = 10.0f; // in inches/pixels
    private static final double FORKLIFT_SPEED = 10.0f; // in inches/pixels / sec

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

    private static final float FrameDimension = 400.0f;

    private static final float FORKLIFT_LENGTH = 20.0f; // front to back, in pixels
    private static final float FORKLIFT_WIDTH = 10.0f; // left to right, in pixels
    private static final float FORKLIFT_HALF_LENGTH = ForkliftSimulator.FORKLIFT_LENGTH / 2.0f;
    private static final float FORKLIFT_HALF_WIDTH = ForkliftSimulator.FORKLIFT_WIDTH / 2.0f;

    private static final double MAX_X = 250.0;
    private static final double MAX_Y = 400.0;
    private static final double STARTING_ANGLE_R = (float)Math.PI / 2.0f; // 90deg to the left (image-up)
    private static final double STARTING_X = ForkliftSimulator.MAX_X / 2.0f;
    private static final double STARTING_Y = ForkliftSimulator.MAX_Y / 2.0f;
    private static final double MAX_WALL_DISTANCE =
        (float)Math.sqrt(FORKLIFT_HALF_LENGTH * FORKLIFT_HALF_LENGTH + FORKLIFT_HALF_WIDTH * FORKLIFT_HALF_WIDTH);

    private float leftPower;
    private float rightPower;

    private boolean forkliftUp;

    private Texture forkliftDownImage;
    private Texture forkliftUpImage;
    private Texture drawerTexture;

    // odometry coordinates (x forward, y left, angle counter-clockwise)
    private double x;
    private double y;
    private double angle;
    private double prevLeftDistance;
    private double prevRightDistance;

    @Inject
    public ForkliftSimulator()
    {
        this.leftPower = 0.0f;
        this.rightPower = 0.0f;

        // start with it either up or down
        this.forkliftUp = Math.random() >= 0.5;

        this.x = ForkliftSimulator.STARTING_X;
        this.y = ForkliftSimulator.STARTING_Y;
        this.angle = ForkliftSimulator.STARTING_ANGLE_R * (180.0f / (float)Math.PI);
        this.prevLeftDistance = 0.0f;
        this.prevRightDistance = 0.0f;

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        this.drawerTexture = new Texture(pixmap); // remember to dispose of later
        pixmap.dispose();

        this.forkliftDownImage = new Texture(Gdx.files.internal("images/forklift_down.png"));
        this.forkliftUpImage = new Texture(Gdx.files.internal("images/forklift_up.png"));

        this.setSize(ForkliftSimulator.FrameDimension, ForkliftSimulator.FrameDimension);
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
            this.leftPower = (float)leftDriveMotor.get();
            this.rightPower = (float)rightDriveMotor.get();
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
        double averagePositionChange = ((leftDistance - this.prevLeftDistance) + (rightDistance - this.prevRightDistance)) / 2.0f;

        // calculate the change since last time, and update our relative position
        double newX = this.x + averagePositionChange * (float)Math.cos(angleR);
        double newY = this.y + averagePositionChange * (float)Math.sin(angleR);

        double newAngle = (angleR * 360.0 / (2.0 * (float)Math.PI)) % 360.0;

        // quick check for collision with walls
        this.x = ForkliftSimulator.clamp(newX, ForkliftSimulator.MAX_WALL_DISTANCE, ForkliftSimulator.MAX_X - ForkliftSimulator.MAX_WALL_DISTANCE);
        this.y = ForkliftSimulator.clamp(newY, ForkliftSimulator.MAX_WALL_DISTANCE, ForkliftSimulator.MAX_Y - ForkliftSimulator.MAX_WALL_DISTANCE);
        this.angle = newAngle;

        // record distance for next time
        this.prevLeftDistance = leftDistance;
        this.prevRightDistance = rightDistance;
    }

    /**
     * Draw a frame of animation based on the current state of the simulation.
     * Remember that (0, 0) is at the bottom left!
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        float frameX = this.getX();
        float frameY = this.getY();
        float frameHeight = this.getHeight();
        float frameWidth = this.getWidth();

        ShapeDrawer drawer = new ShapeDrawer(batch, new TextureRegion(this.drawerTexture, 0, 0, 1, 1));

        /*
        drawer.setColor(Color.BLACK);
        drawer.filledRectangle(frameX, frameY, MAX_X, MAX_Y);
        gc.strokeRect(0, 0, MAX_X, MAX_Y);
        gc.save();

        gc.transform(new Affine(new Rotate(-this.angle, this.x, ForkliftSimulator.MAX_Y - this.y)));
        gc.setFill(Color.RED);
        gc.fillRect(
            this.x - ForkliftSimulator.FORKLIFT_HALF_LENGTH,
            (ForkliftSimulator.MAX_Y - this.y) - ForkliftSimulator.FORKLIFT_HALF_WIDTH,
            ForkliftSimulator.FORKLIFT_LENGTH,
            ForkliftSimulator.FORKLIFT_WIDTH);
        gc.restore();

        gc.setFill(Color.BLUE);
        gc.fillOval(this.x - 1, (ForkliftSimulator.MAX_Y - this.y) - 1, 2, 2);
        */

        float halfHeight = frameHeight / 2.0f;
        float powerIndicatorWidth = frameWidth / 10.0f;

        float leftHeight = Math.abs(leftPower * halfHeight);
        float leftBottom;
        if (leftPower > 0.0)
        {
            leftBottom = halfHeight;
        }
        else
        {
            leftBottom = halfHeight - leftHeight;
        }

        drawer.setColor(Color.BLUE); 
        drawer.filledRectangle(frameX, frameY + leftBottom, powerIndicatorWidth, leftHeight);

        float rightHeight = Math.abs(rightPower * halfHeight);
        float rightBottom;
        if (rightPower > 0.0)
        {
            rightBottom = halfHeight;
        }
        else
        {
            rightBottom = halfHeight - rightHeight;
        }

        drawer.setColor(Color.RED);
        drawer.filledRectangle(frameX + powerIndicatorWidth * 2.0f, frameY + rightBottom, powerIndicatorWidth, rightHeight);

        // draw the forklift in its current state
        Texture forkliftToDraw = null;
        if (this.forkliftUp)
        {
            forkliftToDraw = this.forkliftUpImage;
        }
        else
        {
            forkliftToDraw = this.forkliftDownImage;
        }
        
        batch.draw(
            forkliftToDraw,
            frameX + (float)ForkliftSimulator.MAX_X + 5.0f,
            frameY + frameHeight / 4.0f,
            frameWidth - ((float)ForkliftSimulator.MAX_X + 5.0f),
            frameHeight / 2.0f);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        this.drawerTexture.dispose();
        this.forkliftDownImage.dispose();
        this.forkliftUpImage.dispose();
    }

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
