package frc.robot.mechanisms;

import javax.swing.plaf.TreeUI;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDigitalOutput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class GarageDoorMechanism implements IMechanism
{

    private final IMotor motor;
    private final IDriver driver;

    private final IDigitalInput openSensor;
    private final IDigitalInput closeSensor;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.motor = provider.getTalon(0);
        this.driver = driver;

        this.openSensor = provider.getDigitalInput(1);
        this.closeSensor = provider.getDigitalInput(2);

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
