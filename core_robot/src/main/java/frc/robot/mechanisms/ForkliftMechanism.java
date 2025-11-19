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

    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;
    private final IDriver driver;
    
    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_PWM_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_PWM_CHANNEL);

        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.FORKLIFT_LIFTER_FORWARD_CHANNEL, ElectronicsConstants.FORKLIFT_LIFTER_BACKWARD_CHANNEL);
    }

    @Override
    public void readSensors() {

    }

    @Override
    public void update(RobotMode mode) {
        double leftMotorPower = this.driver.getAnalog(AnalogOperation.LeftMotorPower);
        double rightMotorPower = this.driver.getAnalog(AnalogOperation.RightMotorPower);
        this.leftMotor.set(leftMotorPower);
        this.rightMotor.set(rightMotorPower);
        if (this.driver.getDigital(DigitalOperation.ForkliftUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        if (this.driver.getDigital(DigitalOperation.ForkliftDown)) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }



    }

    @Override
    public void stop() {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
    }
    
}
