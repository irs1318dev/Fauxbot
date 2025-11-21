package frc.robot.mechanisms;

import javax.swing.plaf.TreeUI;

import com.google.inject.Inject;

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

public class ForkliftMechanism implements IMechanism
{

    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;
    private final IDriver driver;

    @Inject
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.leftMotor = provider.getTalon(0);
        this.rightMotor = provider.getTalon(1);
        this.driver = driver;
        this.lifter = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, 7, 8);

    }
    @Override
    public void readSensors()
    {
   
    }

    @Override
    public void update(RobotMode mode)
    {
       double leftPower = this.driver.getAnalog(AnalogOperation.DriveTrainOne);
       double rightPower = this.driver.getAnalog(AnalogOperation.DriveTrainTwo);
       
       boolean pistonUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
       boolean pistonDown = this.driver.getDigital(DigitalOperation.ForkliftDown);

       this.leftMotor.set(leftPower);
       this.rightMotor.set(rightPower);
       
       if (pistonUp) {
            this.lifter.set(DoubleSolenoidValue.Forward);
       }
        if (pistonDown) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
       }
       

    }

    @Override
    public void stop()
    {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
    }
    
}
