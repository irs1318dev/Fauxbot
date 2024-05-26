package frc.robot.simulation;

import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShooterSimulator extends SimulatorBase
{
    private static final float RADIANS_PER_DEGREE = (float)(Math.PI / 180.0);
    private static final FauxbotActuatorConnection AngleMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 0);
    private static final FauxbotActuatorConnection FlyWheelMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 1);
    private static final FauxbotSensorConnection AngleEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonSRX.class, 0);
    private static final FauxbotSensorConnection FlyWheelEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonSRX.class, 1);
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

    private static final float FrameDimension = 400.0f;

    private static final double AngleMinPosition = 0.0;
    private static final double AngleMaxPosition = 90.0;

    private static final double AngleMotorPower = 40.0;
    private static final double AngleSlowRatio = 1.0; // friction?
    private static final double AngleMinAbsoluteVelocity = 0.1;
    private static final double FlyWheelMotorPower = 600.0;
    private static final double FlyWheelSlowRatio = 1.0; // friction?
    private static final double FlyWheelMinAbsoluteVelocity = 0.1;

    private static final double AngleMinVelocity = -22.5;
    private static final double AngleMaxVelocity = 22.5;
    private static final double FlyWheelMinVelocity = 0.0;
    private static final double FlyWheelMaxVelocity = 800.0;

    private static final double GravityAcceleration = 384.0;

    private boolean prevKick;
    private double prevAngle;
    private double prevAngleVelocity;
    private double prevWheelVelocity;

    private double ballHeight;
    private double ballDistance;
    private double ballHorizontalVelocity;
    private double ballVerticalVelocity;

    private Texture drawerTexture;

    @Inject
    public ShooterSimulator()
    {
        this.prevKick = false;
        this.prevAngle = 0.0;
        this.prevAngleVelocity = 0.0;
        this.prevWheelVelocity = 0.0;

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        this.drawerTexture = new Texture(pixmap);
        pixmap.dispose();

        this.setSize(ShooterSimulator.FrameDimension, ShooterSimulator.FrameDimension);
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
        return true;
    }

    @Override
    public void act(float delta)
    {
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

        // accelerate based on percentage of Shooter power
        currAngleVelocity += angleMotorPower * ShooterSimulator.AngleMotorPower * delta;
        currWheelVelocity += flyWheelMotorPower * ShooterSimulator.FlyWheelMotorPower * delta;

        // decelerate based on slowing ratio (friction)
        currAngleVelocity -= ShooterSimulator.AngleSlowRatio * currAngleVelocity * delta;
        currWheelVelocity -= ShooterSimulator.FlyWheelSlowRatio * currWheelVelocity * delta;

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

        currAngle += (currAngleVelocity * delta);

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
            this.ballDistance = 21 + 7.5 * Math.cos(angleRad) - 2.5;
            this.ballHeight = 26 + 7.5 * Math.sin(angleRad) + 2.5;
        }
        else if (this.ballHorizontalVelocity < 0.0 ||
            this.ballDistance > 400.0)
        {
            this.ballHorizontalVelocity = -1.0;
            this.ballVerticalVelocity = 0.0;
            this.ballDistance = 0.0;
            this.ballHeight = 0.0;
        }
        else
        {
            this.ballVerticalVelocity -= ShooterSimulator.GravityAcceleration * delta;
            this.ballHeight += this.ballVerticalVelocity * delta;
            this.ballDistance += this.ballHorizontalVelocity * delta;
        }

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
     * Remember that (0, 0) is at the bottom left!
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        float frameX = this.getX();
        float frameY = this.getY();

        ShapeDrawer drawer = new ShapeDrawer(batch, new TextureRegion(this.drawerTexture, 0, 0, 1, 1));

        // draw the shooter apparatus:
        drawer.setColor(Color.BLACK);
        drawer.setDefaultLineWidth(1.0f);

        // fly-wheel:
        drawer.filledCircle(frameX + 21, frameY + 21, 10);

        // hood:
        drawer.arc(
            frameX + 21,
            frameY + 21,
            20,
            (float)(this.prevAngle + 90.0f) * RADIANS_PER_DEGREE,
            90.0f * RADIANS_PER_DEGREE);

        // draw the ball
        drawer.setColor(Color.BLACK);
        if (this.ballDistance > 0.0 && this.ballHeight > 0.0 && this.ballDistance < 400.0)
        {
            drawer.filledCircle(
                frameX + (float)this.ballDistance,
                frameY + (float)this.ballHeight,
                5);
        }
    }

    @Override
    public void dispose()
    {
        super.dispose();
        this.drawerTexture.dispose();
    }
}
