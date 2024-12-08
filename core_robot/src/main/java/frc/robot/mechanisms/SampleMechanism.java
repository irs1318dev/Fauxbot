package frc.robot.mechanisms;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.driver.AnalogOperation;
@Singleton
public class SampleMechanism implements IMechanism {
    private final IDriver driver;
    private final IMotor motor;
    @Inject
    public SampleMechanism(IRobotProvider robotProvider, IDriver driver){
        this.driver = driver;
        this.motor = robotProvider.getTalon(0);
    }
    public void readSensors(){}
    
    public void update(){
        this.motor.set(this.driver.getAnalog(AnalogOperation.SpinMotor));
    }
    public void stop(){ 
        this.motor.set(0);
    }
}