package frc.robot.driver.mechanisms;

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

    public ForkliftMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_PWM_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_PWM_CHANNEL);
        this.lifter = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_LIFTER_SOLENOID_FORWARD_PCM_CHANNEL,
            ElectronicsConstants.FORKLIFT_LIFTER_SOLENOID_REVERSE_PCM_CHANNEL);
    }

    @Override
    public void readSensors()
    {

    }

    @Override
    public void update(RobotMode mode)
    {
        leftMotor.set(this.driver.getAnalog(AnalogOperation.DriveTrainLeft));
        rightMotor.set(this.driver.getAnalog(AnalogOperation.DriveTrainRight));
        if (this.driver.getDigital(DigitalOperation.ForkliftUp))
        {
            lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ForkliftDown))
        {
            lifter.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop()
    {
        leftMotor.set(0);
        rightMotor.set(0);
        lifter.set(DoubleSolenoidValue.Off);
    }

}