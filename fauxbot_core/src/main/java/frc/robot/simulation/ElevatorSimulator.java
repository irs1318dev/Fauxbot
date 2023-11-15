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

    private static final float[] FloorHeightPercentages = new float[] { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f };
    private static final float PercentageAllowance = 0.01f;
    private static final float ElevatorCarWidth = 30.0f;
    private static final float ElevatorCarHeight = 70.0f;

    private static final float FrameDimension = 400.0f;
    private static final float ElevatorMinHeight = 0.0f;
    private static final float ElevatorMaxHeight = 250.0f;

    private static final float MotorStrength = 700.0f;
    private static final float Gravity = -386.0f;

    private static final float ElevatorMinVelocity = -20.0f;
    private static final float ElevatorMaxVelocity = 20.0f;

    private Texture elevatorPassenger;
    private Texture drawerTexture;

    private float prevHeight;
    private float prevVelocity;

    @Inject
    public ElevatorSimulator()
    {
        this.elevatorPassenger = new Texture(Gdx.files.internal("images/stickFigure.png"));

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        this.drawerTexture = new Texture(pixmap);
        pixmap.dispose();

        this.prevHeight = 0.0f;
        this.prevVelocity = 0.0f;

        this.setSize(ElevatorSimulator.FrameDimension, ElevatorSimulator.FrameDimension);
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
    public void act(float delta)
    {
        float currHeight = this.prevHeight;
        float currVelocity = this.prevVelocity;

        float motorPower = 0.0f;
        FauxbotActuatorBase actuator = FauxbotActuatorManager.get(ElevatorSimulator.MotorChannel);
        if (actuator != null && actuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase motor = (FauxbotMotorBase)actuator;
            motorPower = (float)motor.get();
        }

        currVelocity += motorPower * ElevatorSimulator.MotorStrength * delta + ElevatorSimulator.Gravity * delta;
        currHeight += (currVelocity * delta);

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
            currVelocity = 0.0f;
        }
        else if (this.prevHeight < ElevatorSimulator.ElevatorMinHeight)
        {
            currHeight = ElevatorSimulator.ElevatorMinHeight;
            currVelocity = 0.0f;
        }

        this.prevHeight = currHeight;
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
     * Remember that (0, 0) is at the bottom left!
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        float elevatorHeightRatio = this.prevHeight / (ElevatorSimulator.ElevatorMaxHeight - ElevatorSimulator.ElevatorMinHeight);

        float frameX = this.getX();
        float frameY = this.getY();
        float frameHeight = this.getHeight();
        float frameWidth = this.getWidth();

        ShapeDrawer drawer = new ShapeDrawer(batch, new TextureRegion(this.drawerTexture, 0, 0, 1, 1));

        // cycle through the floors:
        Color elevatorCarColor = Color.RED;
        for (float ratio : ElevatorSimulator.FloorHeightPercentages)
        {
            // if we are within a small allowance from the floor, change elevator color to Green
            if (Math.abs(elevatorHeightRatio - ratio) < ElevatorSimulator.PercentageAllowance)
            {
                elevatorCarColor = Color.GREEN;
            }

            // draw the floor:
            drawer.setColor(Color.BLACK);
            drawer.setDefaultLineWidth(1.0f);
            drawer.line(
                frameX + ElevatorSimulator.ElevatorCarWidth,
                frameY + ratio * frameHeight,
                frameX + frameWidth,
                frameY + ratio * frameHeight); 
        }

        // draw the elevator car:
        drawer.setColor(elevatorCarColor);
        drawer.setDefaultLineWidth(1.0f);
        drawer.filledRectangle(
            frameX,
            frameY + elevatorHeightRatio * frameHeight,
            ElevatorSimulator.ElevatorCarWidth,
            ElevatorSimulator.ElevatorCarHeight);

        // draw the elevator rider:
        batch.draw(
            this.elevatorPassenger,
            frameX,
            frameY + elevatorHeightRatio * frameHeight,
            ElevatorSimulator.ElevatorCarWidth,
            ElevatorSimulator.ElevatorCarHeight);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        this.drawerTexture.dispose();
        this.elevatorPassenger.dispose();
    }
}
