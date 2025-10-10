package frc.robot.mechanisms; 

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism {

    private IMotor motor;
    private IDriver driver;
    private IDigitalInput openSensor; 
    private IDigitalInput closedSensor;
    private IDigitalInput throughBeamSensor;
    private boolean isOpen;
    private boolean isClosed;
    private boolean throughBeamBroken;

    public enum GarageDoorState {
        Opened,
        Opening,
        Closed,
        Closing,
    }
    private GarageDoorState currentState;

    
    @Inject
    public GarageDoorMechanism (IDriver driver, IRobotProvider provider) {
        this.driver = driver; 
        this.motor = provider.getTalon(0);
        this.openSensor = provider.getDigitalInput(1);
        this.closedSensor = provider.getDigitalInput(2);
        this.throughBeamSensor = provider.getDigitalInput(0);
        currentState = GarageDoorState.Closed;
    }



    @Override
    public void readSensors()
    {
        this.isOpen = this.openSensor.get();
        this.isClosed = this.closedSensor.get();
        this.throughBeamBroken = this.throughBeamSensor.get();
    }
    @Override
    public void update(RobotMode mode)
    {
        boolean pressed = driver.getDigital(DigitalOperation.GarageDoorButton);
        if (currentState == GarageDoorState.Closed && pressed) {
            currentState = GarageDoorState.Opening;
        }
        else if (currentState == GarageDoorState.Opening && pressed) {
            currentState = GarageDoorState.Closing;
        }
        else if (currentState == GarageDoorState.Closing && throughBeamBroken) {
            currentState = GarageDoorState.Opening;
        }
        else if (currentState == GarageDoorState.Opening && isOpen) {
            currentState = GarageDoorState.Opened;
        }
        else if (currentState == GarageDoorState.Opened && pressed) {
            currentState = GarageDoorState.Closing;
        }
        else if (currentState == GarageDoorState.Closing && isClosed) {
            currentState = GarageDoorState.Closed;
        }
        else if (currentState == GarageDoorState.Closing && pressed) {
            currentState = GarageDoorState.Opening;
        }
        if (currentState == GarageDoorState.Opening) {
            this.motor.set(1.0);
        }
        else if (currentState == GarageDoorState.Closing) {
            this.motor.set(-1.0);       
        }
        else {
            this.motor.set(0.0);
        }
    }
    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }    
}