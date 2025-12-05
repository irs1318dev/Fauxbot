package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.lib.robotprovider.*;

@Singleton
public class ForkliftMechanism implements IMechanism {

    private IMotor leftMotor;
    private IMotor rightMotor;
    private IDoubleSolenoid piston;
    private IDriver driver;

    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
    this.driver = driver;
    this.leftMotor = provider.getTalon(ElectronicsConstants.LEFT_MOTOR_PWM_CHANNEL);
    this.rightMotor = provider.getTalon(ElectronicsConstants.RIGHT_MOTOR_PWM_CHANNEL);
    this.piston = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.LIFTER_PNEUMATICS_FORWARD_CHANNEL, ElectronicsConstants.LIFTER_PNEUMATICS_REVERSE_CHANNEL);
    }
    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        double leftPower = this.driver.getAnalog(AnalogOperation.DriveTrainLeft); // double variables are numbers with decimals (0.1, 3.3). In this case, we are assigning the double variable to the analog operation. 
        double rightPower = this.driver.getAnalog(AnalogOperation.DriveTrainRight);
        if (this.driver.getDigital(DigitalOperation.Lifterdownbutton)){
            piston.set(DoubleSolenoidValue.Reverse);
        }
        else if (this.driver.getDigital(DigitalOperation.Lifterupbutton)) {
            piston.set(DoubleSolenoidValue.Forward);
        }
        leftMotor.set(leftPower);
        rightMotor.set(rightPower);
    }

    @Override
    public void stop() {
        // stop is when you want the robot to immedietly stop doing something. This is good for safety reasons such as when
        // a part of the robot is threatening to hurt a member and needs immediate deactivation. 
        leftMotor.set(0.0);
        rightMotor.set(0.0);
        piston.set(DoubleSolenoidValue.Off);
    }
    
}

























