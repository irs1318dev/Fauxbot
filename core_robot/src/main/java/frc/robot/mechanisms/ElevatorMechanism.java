package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ElevatorMechanism implements IMechanism{
    private final IMotor ElevatorIMotor;
    private final IDriver driver;
    private final IEncoder encoder;
    private final PIDHandler pidHandler;
    private double position;
    private double desiredPosition;

    @Inject
    public ElevatorMechanism(IDriver driver, IRobotProvider provider, ITimer timer){
        this.ElevatorIMotor=provider.getTalon(ElectronicsConstants.ELEVATOR_MAIN_MOTOR_PCMCHANNEL);
        this.driver=driver;
        this.encoder = provider.getEncoder(ElectronicsConstants.ENCODER_ELEVATOR_PCMCHANNEL_ZERO, ElectronicsConstants.ENCODER_ELEVATOR_PCMCHANNEL_ONE);
        this.pidHandler=new PIDHandler(
            TuningConstants.ELEVATOR_PID_KP, 
            TuningConstants.ELEVATOR_PID_KI, 
            TuningConstants.ELEVATOR_PID_KD, 
            TuningConstants.ELEVATOR_PID_KF, 
            TuningConstants.ELEVATOR_PID_KS, 
            TuningConstants.ELEVATOR_PID_MINOUTPUT, 
            TuningConstants.ELEVATOR_PID_MAXOUTPUT,
            timer
            );

    }
    @Override
    public void readSensors() {
        this.position = this.encoder.getDistance();
    }

    @Override
    public void update(RobotMode mode) {
        if(this.driver.getDigital(DigitalOperation.ElevatorFloor1)){
            this.desiredPosition=TuningConstants.ELEVATOR_FIRST_FLOOR;
        }
        if(this.driver.getDigital(DigitalOperation.ElevatorFloor2)){
            this.desiredPosition=TuningConstants.ELEVATOR_SECOND_FLOOR;
        }
        if(this.driver.getDigital(DigitalOperation.ElevatorFloor3)){
            this.desiredPosition=TuningConstants.ELEVATOR_THIRD_FLOOR;
        }
        if(this.driver.getDigital(DigitalOperation.ElevatorFloor4)){
            this.desiredPosition=TuningConstants.ELEVATOR_FOURTH_FLOOR;
        }
        if(this.driver.getDigital(DigitalOperation.ElevatorFloor5)){
            this.desiredPosition=TuningConstants.ELEVATOR_FITH_FLOOR;
        }
        this.ElevatorIMotor.set(this.pidHandler.calculatePosition(this.desiredPosition, this.position));
    }

    @Override
    public void stop() {
        this.ElevatorIMotor.set(0);
    }
    

}
