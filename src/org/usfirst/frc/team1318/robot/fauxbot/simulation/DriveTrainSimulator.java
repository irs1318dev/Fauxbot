package org.usfirst.frc.team1318.robot.fauxbot.simulation;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.spi.Provider;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.fauxbot.Fauxbot;
import org.usfirst.frc.team1318.robot.fauxbot.IRealWorldSimulator;
import org.usfirst.frc.team1318.robot.fauxbot.simulation.GarageDoorSimulator.GarageState;
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
public class DriveTrainSimulator implements IRealWorldSimulator
{
    private static final int LeftMotorChannel = ElectronicsConstants.DRIVETRAIN_LEFT_MOTOR_CAN_ID;
    private static final int RightMotorChannel = ElectronicsConstants.DRIVETRAIN_RIGHT_MOTOR_PWM_CHANNEL;
    
    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(LeftMotorChannel, "Left Motor");
            this.put(RightMotorChannel, "Right Motor");
        }
    };
    
    private double powerLeft; 
    private double powerRight;
   
    @Inject 
    public DriveTrainSimulator() {
        this.powerLeft = 0.0;
        this.powerRight = 0.0;
    }

   
    public String getSensorName(int channel)
    {
        return motorNameMap.get(channel);
    }

    public double getEncoderMin(int channel)
    {
        return -1.0;
    }

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

        return "Motor " + channel;    }

    @Override
    public void update()
    {
        ActuatorBase leftMotor = ActuatorManager.get((DriveTrainSimulator.LeftMotorChannel));
        ActuatorBase rightMotor = ActuatorManager.get((DriveTrainSimulator.RightMotorChannel));
        
        if((leftMotor != null && leftMotor instanceof MotorBase) && (rightMotor != null && rightMotor instanceof MotorBase)) {
            
            MotorBase left = (MotorBase)leftMotor;
            MotorBase right = (MotorBase)rightMotor;
            powerLeft = left.get();
            powerRight = right.get();
        }
        
    }
    
    @Override
    public void draw(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 200, 200);
        
        gc.setFill(Color.BLUE); 
        gc.fillRect(0, 0, 20, (0 + powerLeft * 50));
      
        
        gc.setFill(Color.RED); 
        gc.fillRect(50, 0, 20, (0 + powerRight * 50));
    } 
}
