package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class ForkliftMechanism implements IMechanism
{
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;

    private Driver driver;

    @Inject
    public ForkliftMechanism(IRobotProvider provider)
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
        double leftPower = this.driver.getAnalog(AnalogOperation.ForkliftDriveLeft);
        double rightPower = this.driver.getAnalog(AnalogOperation.ForkliftDriveRight);

        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);

        if (this.driver.getDigital(DigitalOperation.ForkliftUp))
        {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.ForkliftDown))
        {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}