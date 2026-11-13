package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton 
public class ForkliftMechanism implements IMechanism 
{
    private final IDriver driver;
    private final IMotor driveLeftMotor;
    private final IMotor driveRightMotor;
    private final IDoubleSolenoid lifter;

    @Inject 
    public ForkliftMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.driveLeftMotor = provider.getTalon(0);
        this.driveRightMotor = provider.getTalon(1);
        this.lifter = provider.getDoubleSolenoid(null, 7, 8);
    }

    @Override
    public void readSensors() {

    }

    @Override
    public void update(RobotMode mode) {
        double leftPower = this.driver.getAnalog(AnalogOperation.leftMotorPower);
        double rightPower = this.driver.getAnalog(AnalogOperation.rightMotorPower);
        this.driveLeftMotor.set(leftPower);
        this.driveRightMotor.set(rightPower);

        if (this.driver.getDigital(DigitalOperation.LifterPowerDown)) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        } else if (this.driver.getDigital(DigitalOperation.LifterPowerUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
    }

    @Override
    public void stop() {
        this.driveLeftMotor.set(0);
        this.driveRightMotor.set(0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
    
}
