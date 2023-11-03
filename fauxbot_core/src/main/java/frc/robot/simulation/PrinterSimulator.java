package frc.robot.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import frc.lib.robotprovider.*;
import frc.robot.IRealWorldSimulator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// import javafx.scene.canvas.Canvas;
// import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.image.PixelWriter;
// import javafx.scene.paint.Color;

@Singleton
public class PrinterSimulator implements IRealWorldSimulator
{
    private static final FauxbotActuatorConnection XMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 0);
    private static final FauxbotActuatorConnection YMotorConnection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, 1);
    private static final FauxbotSensorConnection XEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonXBase.class, 0);
    private static final FauxbotSensorConnection YEncoderConnection = new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonXBase.class, 1);
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

    private boolean[][] drawnPixels;
    private double prevTime;
    private boolean prevPenDown;
    private double prevX;
    private double prevXVelocity;
    private double prevY;
    private double prevYVelocity;

    @Inject
    public PrinterSimulator()
    {
        int difference = PrinterSimulator.PrinterMax - PrinterSimulator.PrinterMin;
        this.drawnPixels = new boolean[difference][difference];
        for (int i = 0; i < difference; i++)
        {
            for (int j = 0; j < difference; j++)
            {
                this.drawnPixels[PrinterSimulator.PrinterMin + i][PrinterSimulator.PrinterMin + j] = false;
            }
        }

        this.prevTime = 0.0;
        this.prevPenDown = false;
        this.prevX = 0.0;
        this.prevY = 0.0;
        this.prevXVelocity = 0.0;
        this.prevYVelocity = 0.0;
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
    public void update()
    {
        double currTime = Calendar.getInstance().getTime().getTime() / 1000.0;
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

        double dt = currTime - this.prevTime;

        // accelerate based on percentage of printer power
        currXVelocity += xMotorPower * PrinterSimulator.PrinterMotorPower * dt;
        currYVelocity += yMotorPower * PrinterSimulator.PrinterMotorPower * dt;

        // decelerate based on slowing ratio (friction)
        currXVelocity -= PrinterSimulator.SlowRatio * currXVelocity * dt;
        currYVelocity -= PrinterSimulator.SlowRatio * currYVelocity * dt;

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

        currX += (currXVelocity * dt);
        currY += (currYVelocity * dt);

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
                throw new RuntimeException("don't expect to be moving that fast");
            }

            int x = (int)Math.round(currX);
            int y = (int)Math.round(currY);
            this.drawnPixels[x][y] = true;
        }

        this.prevTime = currTime;
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
     * Remember that (0, 0) is at the top left!
     */
    // @Override
    // public void draw(Canvas canvas)
    // {
    //     double canvasHeight = canvas.getHeight();
    //     double canvasWidth = canvas.getWidth();
    //     GraphicsContext gc = canvas.getGraphicsContext2D();
    //     gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight);

    //     // draw the past path:
    //     PixelWriter writer = gc.getPixelWriter();
    //     int difference = PrinterSimulator.PrinterMax - PrinterSimulator.PrinterMin;
    //     for (int i = 0; i < difference; i++)
    //     {
    //         int xPos = PrinterSimulator.PrinterMin + i;
    //         for (int j = 0; j < difference; j++)
    //         {
    //             int yPos = PrinterSimulator.PrinterMin + j;
    //             if (this.drawnPixels[xPos][yPos])
    //             {
    //                 writer.setColor(xPos, yPos, Color.GREEN);
    //             }
    //         }
    //     }

    //     // draw the crosshair:
    //     double lineLength = 5;
    //     double lineSeparation = 2;
    //     gc.setStroke(Color.BLACK);
    //     gc.setLineWidth(1.0);

    //     int x = (int)Math.round(this.prevX);
    //     int y = (int)Math.round(this.prevY);

    //     // left
    //     if (x > PrinterSimulator.PrinterMinPosition + lineSeparation)
    //     {
    //         gc.strokeLine(
    //             Math.max(PrinterSimulator.PrinterMinPosition, x - lineSeparation - lineLength),
    //             y,
    //             Math.max(PrinterSimulator.PrinterMinPosition, x - lineSeparation),
    //             y); 
    //     }

    //     // right
    //     if (x < PrinterSimulator.PrinterMaxPosition + lineSeparation)
    //     {
    //         gc.strokeLine(
    //             Math.min(PrinterSimulator.PrinterMaxPosition, x + lineSeparation),
    //             y,
    //             Math.min(PrinterSimulator.PrinterMaxPosition, x + lineSeparation + lineLength),
    //             y); 
    //     }

    //     // top
    //     if (y > PrinterSimulator.PrinterMinPosition + lineSeparation)
    //     {
    //         gc.strokeLine(
    //             x,
    //             Math.max(PrinterSimulator.PrinterMinPosition, y - lineSeparation - lineLength),
    //             x,
    //             Math.max(PrinterSimulator.PrinterMinPosition, y - lineSeparation)); 
    //     }

    //     // bottom
    //     if (y < PrinterSimulator.PrinterMaxPosition + lineSeparation)
    //     {
    //         gc.strokeLine(
    //             x,
    //             Math.min(PrinterSimulator.PrinterMaxPosition, y + lineSeparation),
    //             x,
    //             Math.min(PrinterSimulator.PrinterMaxPosition, y + lineSeparation + lineLength)); 
    //     }

    //     // draw the exterior of the point:
    //     gc.setStroke(Color.RED);
    //     gc.setFill(Color.RED);
    //     gc.setLineWidth(1.0);
    //     if (this.prevPenDown)
    //     {
    //         gc.fillOval(
    //             x - lineSeparation,
    //             y - lineSeparation,
    //             lineSeparation * 2.0,
    //             lineSeparation * 2.0);
    //     }
    //     else
    //     {
    //         gc.strokeOval(
    //             x - lineSeparation,
    //             y - lineSeparation,
    //             lineSeparation * 2.0,
    //             lineSeparation * 2.0);
    //     }
    // }
}
