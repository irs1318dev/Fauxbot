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
public class GarageDoorMechanism implements IMechanism{
    private enum state {
        Open,
        Close,
        Opening,
        Closing,
    }
    private state CurrentState;
    private final IMotor GarageDoorIMotor;
    private boolean pressed;
    private final IDriver driver;
    private final IDigitalInput OpenSensor;
    private final IDigitalInput ClosedSensor;
    private final IDigitalInput ThroughBeamSensor;
    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.GarageDoorIMotor=provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR_PCMCHANNEL);
        this.driver=driver;
        this.ClosedSensor=provider.getDigitalInput(ElectronicsConstants.CLOSED_DOOR_SENSOR_PCMCHANNEL);
        this.OpenSensor=provider.getDigitalInput(ElectronicsConstants.OPEN_DOOR_SENSOR_PCMCHANNEL);
        this.ThroughBeamSensor=provider.getDigitalInput(ElectronicsConstants.THROUGH_BEAM_SENSOR_PCMCHANNEL);
    }
    @Override
    public void readSensors() {
        if (this.ThroughBeamSensor.get() && (this.CurrentState!=state.Close)) {
            this.CurrentState=state.Opening;
        }
        if ((this.ClosedSensor.get()) && (this.CurrentState==state.Closing)){
            this.CurrentState=state.Close;        
        }
        if (this.OpenSensor.get() && this.CurrentState==state.Opening){
            this.CurrentState=state.Open;     
        }
        
        }
    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.Button) && (!this.pressed)){
            this.pressed= true;
            if ((this.CurrentState==state.Closing) || (this.CurrentState==state.Close)){
                this.CurrentState=state.Opening;
            }
            else {
                this.CurrentState = state.Closing;
            }
        }
        else if (!this.driver.getDigital(DigitalOperation.Button)){
            this.pressed = false;
        }
        if (this.CurrentState==state.Opening){
            this.GarageDoorIMotor.set(1.0);
        }
        if (this.CurrentState==state.Closing){
            this.GarageDoorIMotor.set(-1.0);
        }
    }

    @Override
    public void stop() {
        this.GarageDoorIMotor.set(0);
    }

    
}
