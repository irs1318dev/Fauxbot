package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
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
    private final IDriver driver;
    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.GarageDoorIMotor=provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR_PCMCHANNEL);
        this.driver=driver;
    }
    @Override
    public void readSensors() {
    }

    @Override
    public void update(RobotMode mode) {
        if (this.CurrentState==state.Open) {
            this.GarageDoorIMotor.set(1);
        }
        if (this.CurrentState==state.Close){
            this.GarageDoorIMotor.set(-1);
        }
        if (this.CurrentState==state.Opening){
            this.GarageDoorIMotor.set(1);
        }
        if (this.CurrentState==state.Closing){
            this.GarageDoorIMotor.set(-1);
        
        }
        if (this.CurrentState==state.Close){
            this.driver.getDigital(DigitalOperation.ClosedSensor);
        }
        else if (this.CurrentState==state.Open){
            this.driver.getDigital(DigitalOperation.OpenSensor);
        }
        else if (this.CurrentState==state.Opening){
            this.driver.getDigital(DigitalOperation.ThroughBeamSensor);
            this.driver.getDigital(DigitalOperation.Button);
        }
        else if (this.CurrentState==state.Closing){
            this.driver.getDigital(DigitalOperation.Button);
        }
    }

    @Override
    public void stop() {
        this.GarageDoorIMotor.set(0);
    }

    
}
