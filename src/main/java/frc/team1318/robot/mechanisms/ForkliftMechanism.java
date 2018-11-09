package frc.team1318.robot.mechanisms;

import javax.inject.Singleton;

import frc.team1318.robot.ElectronicsConstants;
import frc.team1318.robot.common.IMechanism;
import frc.team1318.robot.common.wpilib.DoubleSolenoidValue;
import frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import frc.team1318.robot.common.wpilib.IMotor;
import frc.team1318.robot.common.wpilib.IWpilibProvider;
import frc.team1318.robot.driver.Operation;
import frc.team1318.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class ForkliftMechanism implements IMechanism
{
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;

    private Driver driver;

    @Inject
    public ForkliftMechanism(IWpilibProvider provider)
    {
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_DRIVE_LEFT_MOTOR_CAN_ID);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_DRIVE_RIGHT_MOTOR_CAN_ID);
        this.lifter = provider.getDoubleSolenoid(ElectronicsConstants.FORKLIFT_LIFTER_FORWARD_PCM_CHANNEL, ElectronicsConstants.FORKLIFT_LIFTER_BACKWARD_PCM_CHANNEL);

        this.driver = null;
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        double leftPower = this.driver.getAnalog(Operation.ForkliftDriveLeft);
        double rightPower = this.driver.getAnalog(Operation.ForkliftDriveRight);

        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);

        if (this.driver.getDigital(Operation.ForkliftUp))
        {
            this.lifter.set(DoubleSolenoidValue.kForward);
        }
        else if (this.driver.getDigital(Operation.ForkliftDown))
        {
            this.lifter.set(DoubleSolenoidValue.kReverse);
        }
    }

    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.lifter.set(DoubleSolenoidValue.kOff);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
