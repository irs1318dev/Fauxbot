package frc.robot.mechanisms;

import javax.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ForkliftMechanism implements IMechanism{
    private final IDriver driver;
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;
    
    @Inject
    public ForkliftMechanism(IDriver driver,IRobotProvider provider) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_MOTOR_LEFT_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_MOTOR_RIGHT_CHANNEL);
        this.lifter = provider.getDoubleSolenoid(
        PneumaticsModuleType.PneumaticsControlModule, 
        ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD,
        ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE);
    }   
    public void readSensors() 
    {
        
        
    }

    @Override
    public void update() {
        leftMotor.set(driver.getAnalog(AnalogOperation.SpinMotor));
        rightMotor.set(driver.getAnalog(AnalogOperation.SpinMotor));

        boolean forkliftUp = driver.getDigital(DigitalOperation.ForkliftActuatorExtended);
        boolean forkliftDown = driver.getDigital(DigitalOperation.ForkliftActuatorRetracted);
        
        if (forkliftUp) {
            lifter.set(DoubleSolenoidValue.Forward);
        }
        
        else if (forkliftDown) {
            lifter.set(DoubleSolenoidValue.Reverse);
        }

        
    }


    @Override
    public void stop() {
        
        
    }
    
}
