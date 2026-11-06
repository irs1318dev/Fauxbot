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

    private final IDriver driver;

    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;

    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;

        this.leftMotor = provider.getTalon(0);
        this.rightMotor = provider.getTalon(1); 
        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, 7, 8);
    }

    @Override
    public void readSensors() {

    }

    @Override
    public void update(RobotMode mode) {
        this.leftMotor.set(this.driver.getAnalog(AnalogOperation.LeftMotorPower));
        this.rightMotor.set(this.driver.getAnalog(AnalogOperation.LeftMotorPower));
        this.lifter.set();

        if (this.driver.getDigital(DigitalOperation. ))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        if (this.driver.getDigital(digitalOperation: DigitalOperation. ))

        

    }

    @Override
    public void stop() {
        this.leftMotor.set(0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
    
}
