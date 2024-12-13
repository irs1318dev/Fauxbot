package frc.robot.mechanisms;

import frc.lib.robotprovider.*;
import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.robot.driver.DigitalOperation;
import frc.robot.ElectronicsConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class ElevatorMechanism implements IMechanism {

    // Motor and Encoder
    private final IMotor elevatorMotor;
    private final IEncoder elevatorEncoder;
    // PID Controller
    private PIDHandler pidController;
    // Driver (for button input)
    private final IDriver driver;
    // Target height based on button presses
    private double targetHeight = 0.0;

    @Inject
    public ElevatorMechanism(IRobotProvider provider, IDriver driver) 
    {
        this.driver = driver;
        
        // Initialize the motor and encoder from provider
        this.elevatorMotor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        this.elevatorEncoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);

        // Initialize the PID controller (assuming PID constants are defined in ElectronicsConstants)
        this.pidController = new PIDHandler(ElectronicsConstants.PID_P, ElectronicsConstants.PID_I, ElectronicsConstants.PID_D, targetHeight, targetHeight, targetHeight, targetHeight, targetHeight, targetHeight, targetHeight, targetHeight, null);
    }

    @Override
    public void readSensors() 
    {
        // Read the encoder to get the current height of the elevator
        double currentHeight = elevatorEncoder.getPosition();
        pidController.setCurrentPosition(currentHeight);
    }

    @Override
    public void update(RobotMode mode) 
    {
        // Check for button presses to update the target height
        if (driver.getDigital(DigitalOperation.FirstFloor)) 
        {
            targetHeight = 0.0;
        } 
        else if (driver.getDigital(DigitalOperation.SecondFloor)) 
        {
            targetHeight = 50.0;
        } 
        else if (driver.getDigital(DigitalOperation.ThirdFloor)) 
        {
            targetHeight = 100.0;
        } 
        else if (driver.getDigital(DigitalOperation.FourthFloor)) 
        {
            targetHeight = 150.0;
        } 
        else if (driver.getDigital(DigitalOperation.FifthFloor)) 
        {
            targetHeight = 200.0;
        }

        // Use PID controller to compute the output to send to the motor
        double pidOutput = pidController.calculatePID(targetHeight);

        // Set motor power based on PID output (positive for up, negative for down)
        elevatorMotor.set(pidOutput);
    }

    @Override
    public void stop() 
    {
        // Stop the motor when the elevator is done
        elevatorMotor.set(0.0);
    }
}
