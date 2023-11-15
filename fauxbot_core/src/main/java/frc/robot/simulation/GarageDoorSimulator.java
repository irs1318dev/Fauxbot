package frc.robot.simulation;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GarageDoorSimulator extends SimulatorBase
{
    private static final FauxbotSensorConnection ThroughBeamSensorConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, FauxbotDigitalInput.class, 0);
    private static final FauxbotSensorConnection OpenSensorConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, FauxbotDigitalInput.class, 1);
    private static final FauxbotSensorConnection ClosedSensorConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.DigitalInput, FauxbotDigitalInput.class, 2);
    private static final FauxbotActuatorConnection MotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PWM, 0);

    private final FauxbotSensorConnection[] sensors =
        new FauxbotSensorConnection[]
        {
            GarageDoorSimulator.ThroughBeamSensorConnection,
            GarageDoorSimulator.OpenSensorConnection,
            GarageDoorSimulator.ClosedSensorConnection,
        };

    private final FauxbotActuatorConnection[] actuators =
        new FauxbotActuatorConnection[]
        {
            GarageDoorSimulator.MotorConnection,
        };

    @SuppressWarnings("serial")
    private final Map<FauxbotSensorConnection, String> sensorNameMap = new HashMap<FauxbotSensorConnection, String>()
    {
        {
            this.put(GarageDoorSimulator.ThroughBeamSensorConnection, "Through-Beam sensor");
            this.put(GarageDoorSimulator.OpenSensorConnection, "Open sensor");
            this.put(GarageDoorSimulator.ClosedSensorConnection, "Closed sensor");
        }
    };

    @SuppressWarnings("serial")
    private final Map<FauxbotActuatorConnection, String> motorNameMap = new HashMap<FauxbotActuatorConnection, String>()
    {
        {
            this.put(GarageDoorSimulator.MotorConnection, "Door motor");
        }
    };

    private static final String CerberusPath = "images/cerberus.jpg";
    private static final String GolfCartPath = "images/golfCart.jpg";
    private static final String LamborghiniPath = "images/lamborghini.jpg";
    private static final String PorschePath = "images/porsche.jpg";
    private static final String CessnaCitationPath = "images/cesssnaCitX.jpg";
    private static final String BenzPath = "images/benz.jpeg";

    private static final float SquareDimension = 400.0f;
    private static final float GarageFullyOpened = 400.0f;
    private static final float GarageSpeed = 80.0f;

    private GarageState garageState;
    private float amountOpened;
    private boolean isThroughBeamBroken;

    private Texture image;
    private Color doorColor;
    private Texture drawerTexture;

    @Inject
    public GarageDoorSimulator()
    {
        this.garageState = GarageState.Stopped;
        this.amountOpened = 0.0f;

        this.loadRandomImage();

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        this.drawerTexture = new Texture(pixmap); // remember to dispose of later
        pixmap.dispose();

        this.setSize(GarageDoorSimulator.SquareDimension, GarageDoorSimulator.SquareDimension);
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
        return 0.0;
    }

    @Override
    public double getSensorMax(FauxbotSensorConnection connection)
    {
        return 1.0;
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
    public void act(float delta)
    {
        boolean stateChanged = false;
        FauxbotActuatorBase actuator = FauxbotActuatorManager.get(GarageDoorSimulator.MotorConnection);
        if (actuator != null && actuator instanceof FauxbotMotorBase)
        {
            stateChanged = true;
            FauxbotMotorBase motor = (FauxbotMotorBase)actuator;
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
            else
            {
                stateChanged = false;
            }

            this.amountOpened += motorPower * delta * GarageDoorSimulator.GarageSpeed;
        }

        if (this.amountOpened > GarageDoorSimulator.GarageFullyOpened)
        {
            this.amountOpened = GarageDoorSimulator.GarageFullyOpened;
        }
        else if (this.amountOpened < 0.0f)
        {
            this.amountOpened = 0.0f;
        }

        FauxbotSensorBase openSensor = FauxbotSensorManager.get(GarageDoorSimulator.OpenSensorConnection);
        if (openSensor != null && openSensor instanceof FauxbotDigitalInput)
        {
            FauxbotDigitalInput openSwitch = (FauxbotDigitalInput)openSensor;
            if (this.amountOpened >= GarageDoorSimulator.GarageFullyOpened)
            {
                openSwitch.set(true);
            }
            else
            {
                openSwitch.set(false);
            }
        }

        FauxbotSensorBase closedSensor = FauxbotSensorManager.get(GarageDoorSimulator.ClosedSensorConnection);
        if (closedSensor != null && closedSensor instanceof FauxbotDigitalInput)
        {
            FauxbotDigitalInput closedSwitch = (FauxbotDigitalInput)closedSensor;
            if (this.amountOpened <= 0.0f)
            {
                closedSwitch.set(true);
            }
            else
            {
                closedSwitch.set(false);
            }
        }

        FauxbotSensorBase throughBeamSensor = FauxbotSensorManager.get(GarageDoorSimulator.ThroughBeamSensorConnection);
        if (throughBeamSensor != null && throughBeamSensor instanceof FauxbotDigitalInput)
        {
            FauxbotDigitalInput throughBeam = (FauxbotDigitalInput)throughBeamSensor;
            this.isThroughBeamBroken = throughBeam.get();
            
            if (this.isThroughBeamBroken)
            {
                this.doorColor = Color.YELLOW;
            }
            else
            {
                this.doorColor = Color.GRAY;
            }
        }

        if (this.garageState == GarageState.Stopped && this.amountOpened <= 0.0f && stateChanged)
        {
            this.loadRandomImage();
        }
    }

    private void loadRandomImage()
    {
        String filename = null;
        int randCar = (int)(Math.random() * 6);

        switch (randCar)
        {
            case 0:
                filename = GarageDoorSimulator.LamborghiniPath;
                break;

            case 1:
                filename = GarageDoorSimulator.PorschePath;
                break;

            case 2:
                filename = GarageDoorSimulator.GolfCartPath;
                break;

            case 3:
                filename = GarageDoorSimulator.BenzPath;
                break;

            case 4:
                filename = GarageDoorSimulator.CessnaCitationPath;
                break;

            case 5:
            default:
                filename = GarageDoorSimulator.CerberusPath;
                break;
        }

        try 
        {
            if (this.image != null)
            {
                this.image.dispose();
                this.image = null;
            }

            this.image = new Texture(Gdx.files.internal(filename));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public enum GarageState
    {
        Stopped,
        Opening,
        Closing;
    }

    /**
     * Draw a frame of animation based on the current state of the simulation.
     * Remember that (0, 0) is at the bottom left!
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        float frameHeight = this.getHeight();
        float frameWidth = this.getWidth();
        float frameX = this.getX();
        float frameY = this.getY();

        batch.setColor(1f, 1f, 1f, parentAlpha);

        float imageHeight = this.image.getHeight();
        float imageWidth = this.image.getWidth();

        // Resize images to maintain their aspect ratio
        float imageScale;
        if (imageHeight > imageWidth)
        {
            imageScale = frameHeight / imageHeight;
        }
        else // if (imageHeight <= imageWidth)
        {
            imageScale = frameWidth / imageWidth;
        }

        float scaledImageHeight = this.image.getHeight() * imageScale; 
        float scaledImageWidth = this.image.getWidth() * imageScale;

        float horizontalOffset = frameHeight - scaledImageWidth;
        float verticalOffset = frameHeight - scaledImageHeight;

        batch.draw(
            this.image,
            frameX + horizontalOffset / 2.0f,
            frameY + verticalOffset / 2.0f,
            scaledImageWidth,
            scaledImageHeight);

        ShapeDrawer drawer = new ShapeDrawer(batch, new TextureRegion(this.drawerTexture, 0, 0, 1, 1));

        // determine the garage door color based on whether it is fully opened or not:
        float openRatio = this.amountOpened / GarageDoorSimulator.GarageFullyOpened;
        if (openRatio >= 0.95f)
        {
            drawer.setColor(Color.GREEN);
        }
        else
        {
            drawer.setColor(this.doorColor);
        }

        drawer.setDefaultLineWidth(4.0f);

        // draw the garage door:
        float doorHeight = (1.0f - openRatio) * frameHeight;
        drawer.filledRectangle(
            frameX,
            frameY + openRatio * frameHeight,
            frameWidth,
            doorHeight);

        drawer.setColor(Color.DARK_GRAY);

        // draw the bottom of the door:
        drawer.line(
            frameX, 
            frameY + openRatio * frameHeight,
            frameX + frameWidth,
            frameY + openRatio * frameHeight);

        // draw the midway-bar:
        if (openRatio < 0.95f)
        {
            drawer.line(
                frameX, 
                frameY + openRatio * frameHeight + doorHeight / 2.0f,
                frameX + frameWidth,
                frameY + openRatio * frameHeight + doorHeight / 2.0f);
        }
    }
}
