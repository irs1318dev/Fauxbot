package frc.robot.mechanisms;
import java.beans.Encoder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;
@Singleton
public class ElevatorMechanism implements IMechanism{
    private final IMotor ElevatorIMotor;
    private final IDriver driver;
    private final IEncoder EncoderState;
    public ElevatorMechanism(IDriver driver, IRobotProvider provider){
        this.ElevatorIMotor=provider.getTalon(ElectronicsConstants.ELEVATOR_MAIN_MOTOR_PCMCHANNEL);
        this.driver=driver;
        this.EncoderState=provider.getEncoder(ElectronicsConstants.ENCODER_ELEVATOR_PCMCHANNEL_INCREASING, ElectronicsConstants.ENCODER_ELEVATOR_PCMCHANNEL_DECREASING);

    }
    @Override
    public void readSensors() {

    }

    @Override
    public void update(RobotMode mode) {

    }

    @Override
    public void stop() {

    }
    

}
