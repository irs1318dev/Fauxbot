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

public class ForkliftMechanism implements IMechanism {
    //create new things above inject, create a double solenoid and 2 motors
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;
    private final IDriver driver;
    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFTMOTOR_PCMCHANNEL);

        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHTMOTOR_PCMCHANNEL);

        this.driver = driver;

        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.FORKLIFT_LIFTER_FORWARD_PWMCHANNEL, ElectronicsConstants.FORKLIFT_LIFTER_BACKWARD_PWMCHANNEL);
    }
    @Override
    public void readSensors()
    {
        
    }

    @Override
    public void update(RobotMode mode)
    {
        double leftMotorPower = this.driver.getAnalog(AnalogOperation.leftMotor);
        double rightMotorPower = this.driver.getAnalog(AnalogOperation.rightMotor);

        if (this.driver.getDigital(DigitalOperation.ForkLiftUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ForkLiftDown)) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }

        this.leftMotor.set(leftMotorPower);
        this.rightMotor.set(rightMotorPower);
    }

    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
    }

}
