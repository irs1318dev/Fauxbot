package frc.robot.Mechanisms;

import com.google.inject.inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PheumaticModuleType;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;


@Singleton
public class ForkLiftMechanism implements IMechanism
{
    // Lifter
    private final IDoubleSolenoid lifter;
    // Motors
    private final IMotor rightMotor;
    private final IMotor leftMotor;
    // Drivers
    private final IDriver driver;

    @Inject
    public Forkliftmechanism(IRobotProvider provider, IDriver driver)
    {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORLIFT_LEFT_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.lifter = provider.getDoubleSolenoid(
            PneumaticModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE
        );
    }


    @Override
    public void readSensors() // lol no sensors?
    {
       
    }


    @Override
    public void update()
    {
        private double leftPower;
        private double rightPower;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        leftPower = this.driver.getAnalogOperation(AnalogOperation.TurnLeft);
        rightPower = this.driver.getAnalogOperation(AnalogOperation.TurnRight);


        if (this.driver.getDigitalOperation(getDigitalOperation.Lifter))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else (this.driver.getAnalogOperation(AnalogOperation.TurnRight))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
    }


    @Override
    public void stop()
    {
        this.leftMotor.set(power:0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
       
        this.rightMotor.set(power:1.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
}
