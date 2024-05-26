package frc.robot.simulation;

import java.util.HashMap;
import java.util.Map;

import frc.lib.robotprovider.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PrinterSimulator extends SimulatorBase
{
    private static final FauxbotActuatorConnection XMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 0);
    private static final FauxbotActuatorConnection YMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 1);
    private static final FauxbotSensorConnection XEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonSRX.class, 0);
    private static final FauxbotSensorConnection YEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonSRX.class, 1);
    private static final FauxbotActuatorConnection PenForwardConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0A, 7);
    private static final FauxbotActuatorConnection PenReverseConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PCM0B, 8);

    private final FauxbotSensorConnection[] sensors =
        new FauxbotSensorConnection[]
        {
            PrinterSimulator.XEncoderConnection,
            PrinterSimulator.YEncoderConnection,
        };

    private final FauxbotActuatorConnection[] actuators =
        new FauxbotActuatorConnection[]
        {
            PrinterSimulator.XMotorConnection,
            PrinterSimulator.YMotorConnection,
            PrinterSimulator.PenForwardConnection,
            PrinterSimulator.PenReverseConnection,
        };

    @SuppressWarnings("serial")
    private final Map<FauxbotSensorConnection, String> sensorNameMap = new HashMap<FauxbotSensorConnection, String>()
    {
        {
            this.put(PrinterSimulator.XEncoderConnection, "X encoder");
            this.put(PrinterSimulator.YEncoderConnection, "Y encoder");
        }
    };

    @SuppressWarnings("serial")
    private final Map<FauxbotActuatorConnection, String> motorNameMap = new HashMap<FauxbotActuatorConnection, String>()
    {
        {
            this.put(PrinterSimulator.XMotorConnection, "X Motor");
            this.put(PrinterSimulator.YMotorConnection, "Y Motor");
            this.put(PrinterSimulator.PenForwardConnection, "Pen solenoid");
            this.put(PrinterSimulator.PenReverseConnection, "Pen solenoid");
        }
    };

    private static final double PrinterMinPosition = 0.0;
    private static final int PrinterMin = (int)PrinterSimulator.PrinterMinPosition;
    private static final double PrinterMaxPosition = 200.0;
    private static final int PrinterMax = (int)PrinterSimulator.PrinterMaxPosition;

    private static final double PrinterMotorPower = 40.0;
    private static final double SlowRatio = 1.0; // friction?
    private static final double PrinterMinAbsoluteVelocity = 0.1;

    private static final double PrinterMinVelocity = -20.0;
    private static final double PrinterMaxVelocity = 20.0;

    private Pixmap pixMap;
    private Texture currentPixMapTexture;
    private boolean prevPenDown;
    private double prevX;
    private double prevXVelocity;
    private double prevY;
    private double prevYVelocity;
    private Texture crosshairOpen;
    private Texture crosshairClosed;

    @Inject
    public PrinterSimulator()
    {
        int difference = PrinterSimulator.PrinterMax - PrinterSimulator.PrinterMin;

        this.pixMap = new Pixmap(difference, difference, Format.RGB888);
        this.pixMap.setColor(Color.WHITE);
        this.pixMap.fill();
        this.currentPixMapTexture = new Texture(this.pixMap);

        this.crosshairOpen = new Texture(Gdx.files.internal("images/printer_crosshairs.png"));
        this.crosshairClosed = new Texture(Gdx.files.internal("images/printer_crosshairs_filled.png"));
        this.pixMap.setColor(Color.GREEN);

        this.prevPenDown = false;
        this.prevX = 0.0;
        this.prevY = 0.0;
        this.prevXVelocity = 0.0;
        this.prevYVelocity = 0.0;

        this.setSize(2.0f * difference, 2.0f * difference);
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
        return PrinterSimulator.PrinterMinPosition;
    }

    @Override
    public double getSensorMax(FauxbotSensorConnection connection)
    {
        return PrinterSimulator.PrinterMaxPosition;
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
        double currX = this.prevX;
        double currY = this.prevY;
        double currXVelocity = this.prevXVelocity;
        double currYVelocity = this.prevYVelocity;

        double xMotorPower = 0.0;
        FauxbotActuatorBase xActuator = FauxbotActuatorManager.get(PrinterSimulator.XMotorConnection);
        if (xActuator != null && xActuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase xMotor = (FauxbotMotorBase)xActuator;
            xMotorPower = xMotor.get();
        }

        double yMotorPower = 0.0;
        FauxbotActuatorBase yActuator = FauxbotActuatorManager.get(PrinterSimulator.YMotorConnection);
        if (yActuator != null && yActuator instanceof FauxbotMotorBase)
        {
            FauxbotMotorBase yMotor = (FauxbotMotorBase)yActuator;
            yMotorPower = yMotor.get();
        }

        boolean currPenDown = false;
        FauxbotActuatorBase penActuator = FauxbotActuatorManager.get(PrinterSimulator.PenForwardConnection);
        if (penActuator != null && penActuator instanceof FauxbotDoubleSolenoid)
        {
            FauxbotDoubleSolenoid penSolenoid = (FauxbotDoubleSolenoid)penActuator;
            currPenDown = penSolenoid.get() == DoubleSolenoidValue.Forward;
        }

        // accelerate based on percentage of printer power
        currXVelocity += xMotorPower * PrinterSimulator.PrinterMotorPower * delta;
        currYVelocity += yMotorPower * PrinterSimulator.PrinterMotorPower * delta;

        // decelerate based on slowing ratio (friction)
        currXVelocity -= PrinterSimulator.SlowRatio * currXVelocity * delta;
        currYVelocity -= PrinterSimulator.SlowRatio * currYVelocity * delta;

        if (currXVelocity > PrinterSimulator.PrinterMaxVelocity)
        {
            currXVelocity = PrinterSimulator.PrinterMaxVelocity;
        }
        else if (currXVelocity < PrinterSimulator.PrinterMinVelocity)
        {
            currXVelocity = PrinterSimulator.PrinterMinVelocity;
        }
        else if (Math.abs(currXVelocity) < PrinterSimulator.PrinterMinAbsoluteVelocity)
        {
            currXVelocity = 0.0;
        }

        if (currYVelocity > PrinterSimulator.PrinterMaxVelocity)
        {
            currYVelocity = PrinterSimulator.PrinterMaxVelocity;
        }
        else if (currYVelocity < PrinterSimulator.PrinterMinVelocity)
        {
            currYVelocity = PrinterSimulator.PrinterMinVelocity;
        }
        else if (Math.abs(currYVelocity) < PrinterSimulator.PrinterMinAbsoluteVelocity)
        {
            currYVelocity = 0.0;
        }

        currX += (currXVelocity * delta);
        currY += (currYVelocity * delta);

        if (currX > PrinterSimulator.PrinterMaxPosition)
        {
            currX = PrinterSimulator.PrinterMaxPosition;
            currXVelocity = 0.0;
        }
        else if (currX < PrinterSimulator.PrinterMinPosition)
        {
            currX = PrinterSimulator.PrinterMinPosition;
            currXVelocity = 0.0;
        }

        if (currY > PrinterSimulator.PrinterMaxPosition)
        {
            currY = PrinterSimulator.PrinterMaxPosition;
            currYVelocity = 0.0;
        }
        else if (currY < PrinterSimulator.PrinterMinPosition)
        {
            currY = PrinterSimulator.PrinterMinPosition;
            currYVelocity = 0.0;
        }

        if (this.prevPenDown)
        {
            double distance = Math.sqrt(Math.pow(Math.abs(currX - this.prevX), 2) * Math.pow(Math.abs(currY - this.prevY), 2));
            if (distance > 1.0)
            {
                ////throw new RuntimeException("don't expect to be moving that fast");
            }

            int x = (int)Math.round(currX);
            int y = (int)Math.round(currY);
            this.pixMap.drawPixel(x, y);
            this.currentPixMapTexture.draw(this.pixMap, 0, 0);
        }

        this.prevPenDown = currPenDown;
        this.prevX = currX;
        this.prevY = currY;
        this.prevXVelocity = currXVelocity;
        this.prevYVelocity = currYVelocity;

        FauxbotSensorBase xSensor = FauxbotSensorManager.get(PrinterSimulator.XEncoderConnection);
        if (xSensor != null && xSensor instanceof FauxbotEncoder)
        {
            FauxbotEncoder xEncoder = (FauxbotEncoder)xSensor;
            xEncoder.set((int)this.prevX);
        }

        FauxbotSensorBase ySensor = FauxbotSensorManager.get(PrinterSimulator.YEncoderConnection);
        if (ySensor != null && ySensor instanceof FauxbotEncoder)
        {
            FauxbotEncoder yEncoder = (FauxbotEncoder)ySensor;
            yEncoder.set((int)this.prevY);
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

        batch.setColor(1f, 1f, 1f, parentAlpha);
        batch.draw(this.currentPixMapTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());

        Texture crosshair;
        if (this.prevPenDown)
        {
            crosshair = this.crosshairClosed;
        }
        else
        {
            crosshair = this.crosshairOpen;
        }

        batch.draw(
            crosshair,
            this.getX() + 2.0f * (float)this.prevX - crosshair.getWidth() / 4.0f,
            this.getY() + this.getHeight() - 2.0f * (float)this.prevY - crosshair.getHeight() / 4.0f,
            crosshair.getWidth() / 2.0f,
            crosshair.getHeight() / 2.0f);
    }

    @Override
    public void dispose()
    {
        this.currentPixMapTexture.dispose();
        this.pixMap.dispose();
        this.crosshairOpen.dispose();
        this.crosshairClosed.dispose();
    }
}
