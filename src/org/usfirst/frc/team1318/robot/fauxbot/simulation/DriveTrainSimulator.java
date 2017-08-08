package org.usfirst.frc.team1318.robot.fauxbot.simulation;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.spi.Provider;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
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
    private static final int LeftMotorChannel = 0;
    private static final int RightMotorChannel = 1;
    
    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(DriveTrainSimulator.LeftMotorChannel, "Left Motor");
            this.put(DriveTrainSimulator.RightMotorChannel, "Right Motor");
        }
    };
    
    
    private DriveState driveState;
    private double amountTurned;
    private double amountMoved;
    private double powerLeft; 
    private double powerRight;
    private double leftMoved;
    private double rightMoved;
   
    @Inject 
    public DriveTrainSimulator() {
        
    }

   
    public String getSensorName(int channel)
    {
        
        return null;
    }

    public double getEncoderMin(int channel)
    {
        return 0.0;
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
            double leftPower = left.get();
            double rightPower = right.get();
            
            if ((rightPower == leftPower && rightPower > 0) && this.driveState != DriveState.Forward) {
                this.driveState = DriveState.Forward;
            } else if ((rightPower > leftPower && rightPower > 0) && this.driveState != DriveState.LeftForward) {
                this.driveState = DriveState.LeftForward;
            } else if ((rightPower < leftPower && rightPower > 0) && this.driveState != DriveState.RightForward) {
                this.driveState = DriveState.RightForward;
            } else if ((rightPower > leftPower && rightPower < 0) && this.driveState != DriveState.RightReverse) {
                this.driveState = DriveState.RightReverse;
            } else if ((rightPower < leftPower && rightPower < 0) && this.driveState != DriveState.LeftReverse) {
                this.driveState = DriveState.LeftReverse;
            } else if ((rightPower == leftPower && rightPower < 0) && this.driveState != DriveState.Reverse) {
                this.driveState = DriveState.Reverse;
            } else if ((rightPower == leftPower && rightPower == 0) && this.driveState != DriveState.Stopped) {
                this.driveState = DriveState.Stopped;
            }
         
            amountMoved = 0.0;
            powerLeft = leftPower;
            powerRight = rightPower;
            leftMoved = ++leftPower;
            rightMoved = ++rightPower;
        }
        
    }
    
    public enum DriveState
    {
        Forward,
        LeftForward,
        RightForward,
        Reverse,
        LeftReverse,
        RightReverse,
        Stopped;
    }

    @Override
    public void draw(Canvas canvas)
    {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        
        //gc.fillRoundRect(leftMoved, rightMoved, 10, 10, 50, 50);
        
       
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, 20, powerRight);
        
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 30, 20, powerRight);
        
      
        
    }
    
    
    
}
