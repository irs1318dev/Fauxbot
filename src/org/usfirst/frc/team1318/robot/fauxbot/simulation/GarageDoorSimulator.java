package org.usfirst.frc.team1318.robot.fauxbot.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.spi.Provider;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.fauxbot.IRealWorldSimulator;
import org.usfirst.frc.team1318.robot.garagedoor.GarageDoorMechanism;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import edu.wpi.first.wpilibj.SensorManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

    private static final String CerberusPath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/cerberus.jpg";
    private static final String GolfCartPath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/golfCart.jpg";
    private static final String lamborghiniPath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/lamborghini.jpg";
    private static final String PorschePath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/porsche.jpg";
    private static final String CessnaCitationPath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/cesssnaCitX.jpg";
    private static final String BenzPath = "src/org/usfirst/frc/team1318/robot/fauxbot/images/benz.jpeg";

    private static final int GarageFullyOpened = 250;

    private GarageState garageState;
    private double amountOpened;
    private boolean isThroughBeamBroken;

    private Image image;
    private Color doorColor;

    @Inject
    public GarageDoorSimulator()
    {
        this.garageState = GarageState.Stopped;
        this.amountOpened = 0.0;

        this.loadRandomImage();
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

        if (this.amountOpened > GarageDoorSimulator.GarageFullyOpened)
        {
            this.amountOpened = GarageDoorSimulator.GarageFullyOpened;
        }
        else if (this.amountOpened < 0.0)
        {
            this.amountOpened = 0.0;
        }

        SensorBase openSensor = SensorManager.get(GarageDoorSimulator.OpenSensorChannel);
        if (openSensor != null && openSensor instanceof DigitalInput)
        {
            DigitalInput openSwitch = (DigitalInput)openSensor;
            if (this.amountOpened >= GarageDoorSimulator.GarageFullyOpened)
            {
                openSwitch.set(true);
            }
            else
            {
                openSwitch.set(false);
            }
        }

        SensorBase closedSensor = SensorManager.get(GarageDoorSimulator.ClosedSensorChannel);
        if (closedSensor != null && closedSensor instanceof DigitalInput)
        {
            DigitalInput closedSwitch = (DigitalInput)closedSensor;
            if (this.amountOpened <= 0)
            {
                closedSwitch.set(true);
            }
            else
            {
                closedSwitch.set(false);
            }
        }

        SensorBase throughBeamSensor = SensorManager.get(GarageDoorSimulator.ThroughBeamSensorChannel);
        if (throughBeamSensor != null && throughBeamSensor instanceof DigitalInput)
        {
            DigitalInput throughBeam = (DigitalInput)throughBeamSensor;
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

        if (this.garageState == GarageState.Stopped && this.amountOpened <= 0.0)
        {
            this.loadRandomImage();
        }
    }

    private void loadRandomImage()
    {
        String usedImg = null;
        int randCar = (int)(Math.random() * 6);

        switch (randCar)
        {
            case 0:
                usedImg = GarageDoorSimulator.lamborghiniPath;
                break;

            case 1:
                usedImg = GarageDoorSimulator.PorschePath;
                break;

            case 2:
                usedImg = GarageDoorSimulator.GolfCartPath;
                break;

            case 3:
                usedImg = GarageDoorSimulator.BenzPath;
                break;

            case 4:
                usedImg = GarageDoorSimulator.CessnaCitationPath;
                break;

            case 5:
                usedImg = GarageDoorSimulator.CerberusPath;
                break;
        }

        try 
        {
            FileInputStream imageInput = new FileInputStream(usedImg); 
            this.image = new Image(imageInput);
        }
        catch (Exception e)
        {
            System.out.println(e);
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
     * Remember that (0, 0) is at the top left!
     */
    @Override
    public void draw(Canvas canvas)
    {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight);

        double height = this.image.getHeight();
        double width = this.image.getWidth();

        //Auto Resize for images
        //Based on a 3 to 2 width/height ratio
        int scale;
        if (height < 400 || width < 400)
        {
            scale = 4;
        }
        else if ((height > 600 || width > 400) && (height < 1200 || width < 800))
        {
            scale = 5;
        }
        else if ((height > 1200 || width > 800) && (height < 2400 || width < 1600))
        {
            scale = 6;
        }
        else if (height > 5400 || width > 3600)
        {
            scale = 20;
        }
        else
        {
            scale = 30;
        }

        //int tempScale = 19;
        double imageHeight = this.image.getHeight() / scale; 
        double imageWidth = this.image.getWidth() / scale;

        gc.drawImage(this.image, 0, (canvasHeight - imageHeight), imageWidth, imageHeight);

        // determine the garage door color based on whether it is fully opened or not:
        double openRatio = this.amountOpened / GarageDoorSimulator.GarageFullyOpened;
        gc.setFill(doorColor);
        if (openRatio >= 0.98)
        {
            gc.setFill(Color.GREEN);
        }
        else
        {
            gc.setFill(doorColor);
        }

        gc.setLineWidth(4.0);

        // draw the midway-bar:
        if (openRatio < 0.5)
        {
            gc.strokeLine(0.0, canvasHeight / 2.0, canvasWidth, canvasHeight / 2.0);
        }

        // draw the garage door:
        gc.fillRect(0.0, 0.0, canvasWidth, (1 - openRatio) * canvasHeight);
    }
}
