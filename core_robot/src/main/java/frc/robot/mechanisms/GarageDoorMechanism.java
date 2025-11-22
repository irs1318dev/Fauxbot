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
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism {
    private final IMotor garageDoorMotor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDriver driver;
    private final IDigitalInput throughBeamSensor;
    private boolean sensorOpen;
    private boolean sensorClosed;
    private boolean sensorThroughBeam;
    private enum State {
        OPEN,
        OPENING,
        CLOSED,
        CLOSING,
    }
    private State currentState = State.CLOSED;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;

        this.garageDoorMotor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR_PWM_CHANNEL);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_OPEN_SENSOR_DIO_CHANNEL);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_CLOSED_SENSOR_DIO_CHANNEL);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_THROUGH_BEAM_SENSOR_DIO_CHANNEL);
    }
    
    @Override
    public void readSensors() {
        this.sensorOpen = this.openSensor.get();
        this.sensorClosed = this.closedSensor.get();
        this.sensorThroughBeam = this.throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {  // States
        if (this.currentState == State.OPEN) {
            if (this.driver.getDigital(DigitalOperation.GarageDoorButton)) {
                this.currentState = State.CLOSING;
            }
        } else if (this.currentState == State.CLOSING) {
            if (this.driver.getDigital(DigitalOperation.GarageDoorButton) || (this.sensorThroughBeam)) {
                this.currentState = State.OPENING;
            } else if (this.sensorClosed) {
                this.currentState = State.CLOSED;   
            }
        } else if (this.currentState == State.CLOSED) {
            if (this.driver.getDigital(DigitalOperation.GarageDoorButton)) {
                this.currentState = State.OPENING;
            }
        } else if (this.currentState == State.OPENING) {
            if (this.driver.getDigital(DigitalOperation.GarageDoorButton)) {
                this.currentState = State.CLOSING;
            } else if (this.sensorOpen) {
                this.currentState = State.OPEN;   
            }
        }

        System.out.println("State is: " + this.currentState);

        // todo: What do we do with the motor for each state
        if (this.currentState == State.OPEN || this.currentState ==State.CLOSED) {
            this.garageDoorMotor.set(0.0);
        } else if (this.currentState == State.OPENING) {
            this.garageDoorMotor.set(0.75);
        } else if (this.currentState == State.CLOSING) {
            this.garageDoorMotor.set(-0.75);
        }
    }

    @Override
    public void stop() {
       this.garageDoorMotor.set(0.0);
    }
    
}


// Hi! I know how to comment :D

/* PID     "bang-bang" controler         porportinal         goes between short distance of points
//              thermostat
//   skim beneath point
// don't save bc that causes a wierd error
*/