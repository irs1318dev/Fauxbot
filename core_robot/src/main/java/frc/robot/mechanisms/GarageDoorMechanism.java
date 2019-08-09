package frc.robot.mechanisms;

import javax.management.openmbean.OpenMBeanConstructorInfoSupport;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.robotprovider.IDashboardLogger;
import frc.robot.common.robotprovider.IDigitalInput;
import frc.robot.common.robotprovider.IMotor;
import frc.robot.common.robotprovider.IRobotProvider;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.Driver;

@Singleton
public class GarageDoorMechanism implements IMechanism {

    public enum State{
        Open,
        Opening,
        Closing,
        Close;
    }

    private State state;

    private IDigitalInput openSensor;
    private IDigitalInput closeSensor;
    private IDigitalInput throughbeamSensor;

    private boolean isOpen;
    private boolean isClose;
    private boolean isthroughbeamBroken;

    private IDashboardLogger logger;

    private Driver driver;

    private IMotor motor;

    //button map
    //grab motor from robotprovider (type Imotor - getTalon())
    @Inject
    public GarageDoorMechanism(IRobotProvider robotProvider, IDashboardLogger dashboardLogger){
        //making my sensors

        this.logger = dashboardLogger;
        this.openSensor = robotProvider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_OPEN_SENSOR);
        this.closeSensor = robotProvider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSE_SENSOR);
        this.throughbeamSensor = robotProvider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM_SENSOR);

        this.motor = robotProvider.getTalonSRX(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);

        this.state = State.Close;
    }
    @Override
    public void readSensors() {
        //boolean values saved in variables
        this.isOpen = this.openSensor.get();
        this.isClose = this.closeSensor.get();
        this.isthroughbeamBroken = this.throughbeamSensor.get();
        //logging to logger
        this.logger.logBoolean("GarageDoor", "isOpen", this.isOpen);
        this.logger.logBoolean("GarageDoor", "isClose", this.isClose);
        this.logger.logBoolean("GarageDoor", "isthroughbeamBroken", this.isthroughbeamBroken);
    }

    @Override
    public void update() {
    //update the state of the garageDoorMechanism
    //check current state
    boolean isPressed = this.driver.getDigital(DigitalOperation.GarageDoorButton);

    if(state == State.Close && isPressed){
        this.state = State.Opening;
    }
    else if(state == State.Open && isPressed){
        this.state = State.Closing;
    }
    else if(state == State.Opening && isPressed){
        this.state = State.Closing;
    }
    else if(state == State.Closing && isPressed){
        this.state = State.Opening;
    }

    else if(this.isOpen && state == State.Opening){
        this.state = State.Open;
    }
    else if(this.isClose && state == State.Closing){
        this.state = State.Close;
    }
    else if(state == State.Closing && isthroughbeamBroken){
        this.state = State.Opening;
    }

    if(this.state == State.Opening){
        this.motor.set(TuningConstants.GARAGE_DOOR_OPENING_POWER);
    }
    else if(this.state == State.Open || this.state == State.Close){
        this.motor.set(0.0);
    }
    else if(this.state == State.Closing){
        this.motor.set(TuningConstants.GARAGE_DOOR_CLOSING_POWER);
    }
    }

    @Override
    public void stop() {
    //sets the motor to 0
        this.motor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver) {
        this.driver = driver;
	}

}