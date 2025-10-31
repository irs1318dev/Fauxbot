package frc.robot.mechanisms;

import com.google.inject.Inject;

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

public class ForkliftMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;

    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_MOTOR_LEFT_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_MOTOR_RIGHT_CHANNEL);
        this.lifter = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD, 
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE);
    }

    @Override
    public void readSensors() {

    }


    @Override
    public void update(RobotMode mode) {

        double leftMotorPower = this.driver.getAnalog(AnalogOperation.LeftMotorPower);
        double RightMotorPower = this.driver.getAnalog(AnalogOperation.RightMotorPower);
        
        if (this.driver.getDigital(DigitalOperation.ForkliftUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ForkliftDown)) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
        
        this.leftMotor.set(leftMotorPower);
        this.rightMotor.set(RightMotorPower);
    }

    @Override
    public void stop() {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
    }
}