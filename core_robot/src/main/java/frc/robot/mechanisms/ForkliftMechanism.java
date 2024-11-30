package frc.robot.mechanisms; 

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism {

    private IMotor leftMotor;
    private IMotor rightMotor;
    private IDoubleSolenoid piston;
    private IDriver driver;

    @Inject
    public ForkliftMechanism (IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(0);
        this.rightMotor = provider.getTalon(1);
        this.piston = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule , 7 , 8);
    }

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(RobotMode mode) {
        // TODO Auto-generated method stub
        double leftPower = this.driver.getAnalog(AnalogOperation.DriveTrainLeft);
        double rightPower = this.driver.getAnalog(AnalogOperation.DriveTrainRight);
        boolean pistonUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
        boolean pistonDown = this.driver.getDigital(DigitalOperation.ForkliftDown);
        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);
        if (pistonUp){
            this.piston.set(DoubleSolenoidValue.Forward);
        }
        else if (pistonDown) {
            this.piston.set(DoubleSolenoidValue.Reverse);
        }
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.piston.set(DoubleSolenoidValue.Off);
        
    }
    
}