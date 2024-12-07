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
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism
{
    private final IDoubleSolenoid forklift;
    private final IMotor rightMotor;
    private final IMotor leftMotor;
    private final IDriver driver;

    @Inject
    public ForkliftMechanism(IRobotProvider provider, IDriver driver)
    {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.forklift = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE);    
        
    }
    @Override
    public void readSensors()
    {

    }

    @Override
    public void update(RobotMode mode)
    {
        double leftPower = this.driver.getAnalog(AnalogOperation.TurnLeft);
        double rightPower = this.driver.getAnalog(AnalogOperation.TurnRight);
       
        if (this.driver.getDigital(DigitalOperation.ForkLiftUp)) {
            this.forklift.set(DoubleSolenoidValue.Forward); 
        }

        //private double rightPower;
        //rightPower = this.driver.getAnalogOperation(AnalogOperation.TurnRight);
        //this.rightMotor = rightMotor;
       else (this.driver.getAnalog(AnalogOperation.TurnRight)){
       // else (this.driver.getAnalogOperation(rightPower)){
            this.forklift.set(DoubleSolenoidValue.Forward); 
        }

          
    }
    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.forklift.set(DoubleSolenoidValue.Off);

        this.rightMotor.set(1.0);
        this.forklift.set(DoubleSolenoidValue.Off);
    }    
}