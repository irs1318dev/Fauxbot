package frc.robot.mechanism;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.AnalogOperation;
import frc.lib.robotprovider.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor driveLefIMotor;
    private final IMotor driveRightIMotor;
    private final IDoubleSolenoid lifter;

    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider)
    { 
        this.driver = driver;
        this.driveLefIMotor = provider.getTalon(0);
        this.driveRightIMotor = provider.getTalon(1);
        this.lifter = provider.getDoubleSolenoid(null, 7, 8);
    }

    @Override
    public void readSensors()
    {

    }

    @Override
    public void update(RobotMode mode)
    {
      double leftPower = this.driver.getAnalog(AnalogOperation.LeftMotorPower);
      double rightPower = this.driver.getAnalog(AnalogOperation.RightMotorPower);
      this.driveLefIMotor.set(leftPower);
      this.driveRightIMotor.set(rightPower);

      if (this.driver.getDigitalOperation(LifterPowerDown)) {
        this.lifter.set(DoubleSolenoidValue.Reverse);
      }
       else if (this.driver.getDigitalOperation(LifterPowerUp)){
        this.lifter.set(DoubleSolenoidValue.Forward);
       }
      
    }

    @Override
    public void stop()
    {
     this.driveLefIMotor.set(power:0);
     this.driveRightIMotor.set(power:0);
     this.lifter.set(DoubleSolenoidValue.Off);
    }
    
}
