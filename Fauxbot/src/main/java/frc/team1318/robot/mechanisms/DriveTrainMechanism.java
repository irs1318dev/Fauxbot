package frc.team1318.robot.mechanisms;

import javax.inject.Singleton;

import frc.team1318.robot.ElectronicsConstants;
import frc.team1318.robot.TuningConstants;
import frc.team1318.robot.common.IMechanism;
import frc.team1318.robot.common.wpilib.IMotor;
import frc.team1318.robot.common.wpilib.IWpilibProvider;
import frc.team1318.robot.driver.Operation;
import frc.team1318.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class DriveTrainMechanism implements IMechanism
{
    private final IMotor leftMotor;
    private final IMotor rightMotor;

    private Driver driver;

    @Inject
    public DriveTrainMechanism(IWpilibProvider provider)
    {
        this.leftMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_LEFT_MOTOR_CAN_ID);
        this.rightMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_RIGHT_MOTOR_CAN_ID);

        this.driver = null;
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        double leftPower = this.driver.getAnalog(Operation.DriveTrainLeft);
        double rightPower = this.driver.getAnalog(Operation.DriveTrainRight);

        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);
    }

    // Create a switch with states to make the robot turn
    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
