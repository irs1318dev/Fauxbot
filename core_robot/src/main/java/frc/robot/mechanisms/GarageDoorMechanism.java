package frc.robot.mechanisms;
import javax.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;


@Singleton
public class GarageDoorMechanism implements IMechanism {
    private IDriver driver;
    private IRobotProvider provider;
    private IMotor motor;
    private IDigitalInput openSensor;
    private IDigitalInput closedSensor;
    private IDigitalInput throughBeamSensor;

    private boolean isOpenSensed;
    private boolean isClosedSensed;
    private boolean isThroughBeamSensed;

    private enum GarageDoorState {
        Opened,
        Opening,
        Closed,
        Closing
    }

    private GarageDoorState state;

    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(0);
        this.openSensor = provider.getDigitalInput(1);
        this.closedSensor = provider.getDigitalInput(2);
        this.throughBeamSensor  = provider.getDigitalInput(3);
        this.state = GarageDoorState.Closed;
        
    }
    
    
    

    public void readSensors() {
        // TODO Auto-generated method stub
        this.isOpenSensed = this.openSensor.get();
        this.isClosedSensed = this.closedSensor.get();
        this.isThroughBeamSensed = this.throughBeamSensor.get();
        
    } 

    public boolean buttonPressed = this.driver.getDigital(DigitalOperation.ButtonPressed);

    public void update() {
        if (isOpenSensed && (this.state == GarageDoorState.Opening)) {
            this.state = GarageDoorState.Opened;
        }
        else if ((this.state == GarageDoorState.Opening) && buttonPressed){
            this.state = GarageDoorState.Closing;
        }
        else if ((this.state == GarageDoorState.Opened) && buttonPressed) {
            this.state = GarageDoorState.Opening;
        }
        else if ((this.state == GarageDoorState.Closing) && isThroughBeamSensed) {
            this.state = GarageDoorState.Opening;
        }
        else if ((this.state == GarageDoorState.Closing) && buttonPressed) {
            this.state = GarageDoorState.Opening;
        }
        else if ((this.state == GarageDoorState.Closing) && isClosedSensed) {
            this.state = GarageDoorState.Closed;
        }
        else if ((this.state == GarageDoorState.Closed) && buttonPressed) {
            this.state = GarageDoorState.Opening;
        }
    }

    

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
