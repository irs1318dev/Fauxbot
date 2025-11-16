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
public class ForkliftMechanism implements IMechanism {
    private final IMotor leftIMotor;
    private final IMotor rightIMotor;
    private final IDoubleSolenoid lifter;
    private final IDriver driver;


    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.leftIMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFTMOTOR_PCMCHANNEL);
        this.rightIMotor = provider.getTalon(ElectronicsConstants.FORTKLIFT_RIGHTMORTOR_PCMCHANNEL);
        this.driver = driver;
        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.FORKLIFT_FORWARD_PCMCHANNEL, ElectronicsConstants.FORKLIFT_BACKWARD_PCMCHANNEL);
        
    }
    @Override
    public void readSensors() {
    }

    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.ForkliftUp)){
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ForkliftDown)){
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
        this.leftIMotor.set(this.driver.getAnalog(AnalogOperation.LeftMotorPower));
        this.rightIMotor.set(this.driver.getAnalog(AnalogOperation.RightMotorPower));
    }

    @Override
    public void stop() {
        this.leftIMotor.set(0);
        this.rightIMotor.set(0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
}
    
