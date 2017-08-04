package org.usfirst.frc.team1318.robot.drivetrain;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class DriveTrainMechanism implements IMechanism
{
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private Driver driver;


    @Inject
    public DriveTrainMechanism(IWpilibProvider provider)
    {
        this.leftMotor = provider.getTalon(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
        this.driver = null;
    }

    @Override
    public void update()
    {
        double turnAmount = this.driver.getAnalog(Operation.DriveTrainTurn);
        double forwardVelocity = this.driver.getAnalog(Operation.DriveTrainMoveForward);
        
        if (this.state == GarageDoorState.Closed)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (this.state == GarageDoorState.Open)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
        }
        else if (this.state == GarageDoorState.Opening)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
            else if (this.openSensor.get())
            {
                this.state = GarageDoorState.Open;
            }
        }
        else // if (this.state == GarageDoorState.Closing)
        {
            if (buttonPressed || this.throughBeamSensor.get())
            {
                this.state = GarageDoorState.Opening;
            }
            else if (this.closedSensor.get())
            {
                this.state = GarageDoorState.Closed;
            }
        }

        switch (this.state)
        {
            case Closed:
            case Open:
                this.motor.set(0.0);
                break;

            case Opening:
                this.motor.set(1.0);
                break;

            case Closing:
                this.motor.set(-1.0);
                break;
        }
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    private enum GarageDoorState
    {
        Open, Closing, Closed, Opening;
    }
}
