package frc.team1318.robot.fauxbot.simulation;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import frc.team1318.robot.ElectronicsConstants;
import frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import frc.team1318.robot.fauxbot.IRealWorldSimulator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

@Singleton
public class ForkliftSimulator implements IRealWorldSimulator
{
    @SuppressWarnings("serial")
    private final Map<Integer, String> actuatorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(ElectronicsConstants.FORKLIFT_DRIVE_LEFT_MOTOR_CAN_ID, "F Left Drive Motor");
            this.put(ElectronicsConstants.FORKLIFT_DRIVE_RIGHT_MOTOR_CAN_ID, "Right Drive Motor");
            this.put(ElectronicsConstants.FORKLIFT_LIFTER_FORWARD_PCM_CHANNEL, "Lifter solenoid");
        }
    };

    private double leftPower;
    private double rightPower;

    private boolean forkliftUp;

    private Image forkliftDownImage;
    private Image forkliftUpImage;

    @Inject
    public ForkliftSimulator()
    {
        this.leftPower = 0.0;
        this.rightPower = 0.0;

        // start with it either up or down
        this.forkliftUp = Math.random() >= 0.5;

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
    public String getSensorName(int channel)
    {
        return "Sensor " + channel;
    }

    @Override
    public double getEncoderMin(int channel)
    {
        return -1.0;
    }

    @Override
    public double getEncoderMax(int channel)
    {
        return 1.0;
    }

    @Override
    public String getActuatorName(int channel)
    {
        if (this.actuatorNameMap.containsKey(channel))
        {
            return this.actuatorNameMap.get(channel);
        }

        return "Motor " + channel;
    }

    @Override
    public void update()
    {
        ActuatorBase leftDriveActuator = ActuatorManager.get(ElectronicsConstants.FORKLIFT_DRIVE_LEFT_MOTOR_CAN_ID);
        ActuatorBase rightDriveActuator = ActuatorManager.get(ElectronicsConstants.FORKLIFT_DRIVE_RIGHT_MOTOR_CAN_ID);
        ActuatorBase lifterActuator = ActuatorManager.get(ElectronicsConstants.FORKLIFT_LIFTER_FORWARD_PCM_CHANNEL);

        if (leftDriveActuator != null && leftDriveActuator instanceof MotorBase && rightDriveActuator != null && rightDriveActuator instanceof MotorBase)
        {
            MotorBase leftDriveMotor = (MotorBase)leftDriveActuator;
            MotorBase rightDriveMotor = (MotorBase)rightDriveActuator;
            this.leftPower = leftDriveMotor.get();
            this.rightPower = rightDriveMotor.get();
        }

        if (lifterActuator != null && lifterActuator instanceof DoubleSolenoid)
        {
            DoubleSolenoid lifterSolenoid = (DoubleSolenoid)lifterActuator;
            this.forkliftUp = lifterSolenoid.get() == DoubleSolenoid.Value.kForward;
        }
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
