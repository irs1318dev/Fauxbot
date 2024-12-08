package frc.robot.mechanisms;

// import java.util.spi.ResourceBundleControlProvider;


import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

public class ElevatorMechanism implements IMechanism {

    
    private IMotor motor;
    private IDriver driver;
    private IRobotProvider provider;
    private IEncoder encoder;
    private PIDHandler handler;

    int currentPosition;
    int desiredPosition;
    boolean firstFloorPressed;
    boolean secondFloorPressed;
    boolean thirdFloorPressed;
    boolean fourthFloorPressed;
    boolean fifthFloorPressed;

    public ElevatorMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.provider = provider;
        this.motor = this.provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        this.handler = new PIDHandler(0, 0, 0, 0, 0, null, null, null);
        this.encoder = this.provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);

        this.firstFloorPressed = false;
        this.secondFloorPressed = false;
        this.thirdFloorPressed = false;
        this.fourthFloorPressed = false;
        this.fifthFloorPressed = false;
    }
	
    

    @Override
	public void readSensors() {
		// TODO Auto-generated method stub
		currentPosition = this.encoder.get();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
        //gets the current position
        readSensors();

        //checks all buttons for pressed status
        this.firstFloorPressed = this.driver.getDigital(DigitalOperation.ElevatorFirstFloor);
        this.secondFloorPressed = this.driver.getDigital(DigitalOperation.ElevatorFirstFloor);
        this.thirdFloorPressed = this.driver.getDigital(DigitalOperation.ElevatorFirstFloor);
        this.fourthFloorPressed = this.driver.getDigital(DigitalOperation.ElevatorFirstFloor);
        this.fifthFloorPressed = this.driver.getDigital(DigitalOperation.ElevatorFirstFloor);
        
        //check whichever is true and move to that value
        if (this.firstFloorPressed)
        {
            this.desiredPosition = 0;
        }
        else if (this.secondFloorPressed)
        {
            this.desiredPosition = 50;
        }
        else if (this.thirdFloorPressed)
        {
            this.desiredPosition = 100;
        }
        else if (this.fourthFloorPressed)
        {
            this.desiredPosition = 150;
        }
        else if (this.fifthFloorPressed)
        {
            this.desiredPosition = 200;
        }
        
        //sets the position I think
        this.motor.set(handler.calculatePosition(this.desiredPosition, currentPosition));
	}

    
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
    
}
