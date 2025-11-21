package frc.robot.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;


public class garageDoorMechanism implements IMechanism {
    private final IMotor motor;
    private final IDriver driver;
    private final IDigitalInput closedSensor;
    private final IDigitalInput openSensor;
    private final IDigitalInput throughBeamSensor;
    private boolean isClosed;
    private boolean isOpen;
    private boolean throughBeamSensorBroken;
    private garageDoorState state;
    private boolean closedSensorHit;
    private boolean openSensorHit;
    private IDigitalInput garageDoorButton;
    public garageDoorMechanism (IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGEDOOR_MOTOR_PCMCHANNEL);

        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_DIGITALCHANNEL );
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_DIGITALCHANNEL );
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAMSENSOR_DIGITALCHANNEL );
        this.isClosed = false;
        this.isOpen = false;
        this.throughBeamSensorBroken = false;
        this.state = garageDoorState.closed;



}

    @Override
    public void readSensors() {
        this.openSensorHit = openSensor.get();
        this.closedSensorHit = closedSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean buttonPressed = this.driver.getDigital(DigitalOperation.garageDoorButton);
        if (this.state == garageDoorState.open) {
            if (buttonPressed) {
                this.state = garageDoorState.closing;
            }
        }

        if (this.state == garageDoorState.closing) {
            if (throughBeamSensorBroken) {
                this.state = garageDoorState.opening;
            }
            
            if (buttonPressed) {
                this.state = garageDoorState.opening;
            }

            if (this.closedSensorHit) {
                this.state = garageDoorState.closed;
            }
        }

        if (this.state == garageDoorState.closed) {
            if (buttonPressed) {
                this.state = garageDoorState.opening;
            }
        }

        if (this.state == garageDoorState.opening) {
            if (buttonPressed) {
                this.state = garageDoorState.closing;
            }
            if (this.openSensorHit) {
                this.state = garageDoorState.open;  
            }
        }
    
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");

    }
    private enum garageDoorState {
        open, closing, closed, opening;
    }
}
