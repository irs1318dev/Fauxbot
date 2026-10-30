package frc.robot.mechanism;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;

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

    }

    @Override
    public void stop()
    {

    }
    
}
