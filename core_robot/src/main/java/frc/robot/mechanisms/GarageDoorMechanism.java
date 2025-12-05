package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism{

    private final IMotor motor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closeSensor;
    private final IDigitalInput throughBeamSensor;
    private final IDriver driver;

    private boolean isOpen;
    private boolean isClosed;
    private boolean throughBeamBroken; 

    private  GarageDoorState state;
    
    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_MOTOR_PWM_CHANNEL);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.OPEN_SENSOR_PWN_CHANNEL);
        this.closeSensor = provider.getDigitalInput(ElectronicsConstants.CLOSE_SENSOR_PWN_CHANNEL);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.THROUGH_BEAM_SENSOR_PWN_CHANNEL);
        this.driver = driver;

        this.state = GarageDoorState.Closed;
        this.isOpen = false;
        this.isClosed = false;
        this.throughBeamBroken = false;


    }
    @Override
    public void readSensors() {
        this.isOpen = this.openSensor.get();
        this.isClosed = this.closeSensor.get();
        this.throughBeamBroken = this.throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean buttonPressed = this.driver.getDigital(DigitalOperation.ButtonPressed);
        
        if(this.state == GarageDoorState.Opening) {
            this.motor.set(TuningConstants.GARAGE_DOOR_OPENING_POWER);
            if(buttonPressed) {
                this.state = GarageDoorState.Closing;
            }
            
        }
        else if(this.state == GarageDoorState.Closing) {
            this.motor.set(TuningConstants.GARAGE_DOOR_CLOSING_POWER);
            if(buttonPressed) {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (this.state == GarageDoorState.Closed) {
            this.motor.set(TuningConstants.GARAGE_DOOR_OPEN_OR_CLOSED_POWER);
            if (buttonPressed) {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (this.state == GarageDoorState.Open) {
            this.motor.set(TuningConstants.GARAGE_DOOR_OPEN_OR_CLOSED_POWER);
            if (buttonPressed) {
                this.state = GarageDoorState.Closing;
            }
        }
        if (this.throughBeamBroken) {
            if (this.state != GarageDoorState.Closed){
                this.state = GarageDoorState.Opening;
            }
        }
        
    }

    @Override
    public void stop() {
        this.motor.set(0);
        
    }
    
    private enum GarageDoorState{
        Open, Opening, Closing, Closed
    }
}
