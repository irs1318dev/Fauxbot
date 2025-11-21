package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;

public class GarageDoorMechanism implements IMechanism {
    private GarageDoorState state;
    private final IDriver driver;
    private final IMotor motor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughbeamSensor;

    private boolean isOpen;
    private boolean isClosed;
    private boolean isThroughBeamSensorBroken;
    
    public enum GarageDoorState {
        Opened,
        Opening,
        Closed,
        Closing
    }
    
    
// A Constructor 
    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.motor = provider.getTalon(ElectronicsConstants.PWMCHANNEL_MOTOR);
        this.driver = driver;
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.OPENSENSOR_DIGITAL_IO);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.OPENSENSOR_DIGITAL_IO);
        this.throughbeamSensor = provider.getDigitalInput(ElectronicsConstants.OPENSENSOR_DIGITAL_IO);
        this.state = GarageDoorState.Closed;
        this.isOpen = false;
        this.isClosed = true; 
        this.isThroughBeamSensorBroken = false; 
    }
    

    @Override
    public void readSensors() {
        this.isOpen = this.openSensor.get();
        this.isClosed = this.closedSensor.get();
        this.isThroughBeamSensorBroken = this.throughbeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    
}
