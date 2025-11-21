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
    public enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }

    private final IMotor motor;
    private final IDriver driver;

    private final IDigitalInput openSensor;
    private final IDigitalInput closeSensor;
    private final IDigitalInput throughBeamSensor;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
    

    }
    @Override
    public void readSensors()
    {
   
    }

    @Override
    public void update(RobotMode mode)
    {
        
    }

    @Override
    public void stop()
    {

    }
    
}
