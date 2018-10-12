package frc.team1318.robot.fauxbot.simulation;
import java.util.HashMap;
import java.util.Map;

import frc.team1318.robot.ElectronicsConstants;
import frc.team1318.robot.fauxbot.IRealWorldSimulator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.ActuatorBase;
import edu.wpi.first.wpilibj.ActuatorManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@Singleton
public class DriveTrainSimulator implements IRealWorldSimulator
{
    private static final int LeftMotorChannel = ElectronicsConstants.DRIVETRAIN_LEFT_MOTOR_CAN_ID;
    private static final int RightMotorChannel = ElectronicsConstants.DRIVETRAIN_RIGHT_MOTOR_CAN_ID;

    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(LeftMotorChannel, "DriveTrain Left Motor");
            this.put(RightMotorChannel, "DriveTrain Right Motor");
        }
    };

    private double powerLeft; 
    private double powerRight;

    @Inject
    public DriveTrainSimulator()
    {
        this.powerLeft = 0.0;
        this.powerRight = 0.0;
    }

    @Override
    public String getSensorName(int channel)
    {
        return motorNameMap.get(channel);
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
        if (this.motorNameMap.containsKey(channel))
        {
            return this.motorNameMap.get(channel);
        }

        return "Motor " + channel;
    }

    @Override
    public void update()
    {
        ActuatorBase leftMotor = ActuatorManager.get(DriveTrainSimulator.LeftMotorChannel);
        ActuatorBase rightMotor = ActuatorManager.get(DriveTrainSimulator.RightMotorChannel);

        if (leftMotor != null && leftMotor instanceof MotorBase && rightMotor != null && rightMotor instanceof MotorBase)
        {
            MotorBase left = (MotorBase)leftMotor;
            MotorBase right = (MotorBase)rightMotor;
            this.powerLeft = left.get();
            this.powerRight = right.get();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 200, 200);

        double leftHeight = Math.abs(powerLeft * 100);
        double leftTop;
        if (powerLeft > 0.0)
        {
            leftTop = 100 - leftHeight;
        }
        else
        {
            leftTop = 100;
        }

        gc.setFill(Color.BLUE); 
        gc.fillRect(0, leftTop, 20, leftHeight);

        double rightHeight = Math.abs(powerRight * 100);
        double rightTop;
        if (powerRight > 0.0)
        {
            rightTop = 100 - rightHeight;
        }
        else
        {
            rightTop = 100;
        }

        gc.setFill(Color.RED); 
        gc.fillRect(50, rightTop, 20, rightHeight);
    } 
}
