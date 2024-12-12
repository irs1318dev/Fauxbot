package frc.robot.Mechanisms;

import frc.lib.mechanisms.IMechanism;
import frc.robot.common.*;
import java.security.Provider;

import com.google.inject.Inject;
import com.google.inject.singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.GarageDoor;
import frc.lib.mechanisms.GarageDoor.GarageDoorState;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.ElectronicsConstants;
import frc.robot.EletronicsConstants;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.LoggingManager;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.Idriver;

@Singleton
public class forkLiftMechanism implements IMechanism
{
    // Lifter
    private final IDoubleSolenoid lifter;
    // Motors
    private final IMotor rightMotor;
    private final IMotor leftMotor;
    // Drivers
    private final IDriver driver;

    @Inject
    public forkLiftMechanism(IRobotProvider provider, IDriver driver)
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

        if (this.driver.getDigitalOperation(getDigitalOperation.Lifter))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getAnalogOperation(AnalogOperation.TurnRight))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
    }


    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
       
        this.rightMotor.set(1.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
}
