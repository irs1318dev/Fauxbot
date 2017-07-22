package org.usfirst.frc.team1318.robot.elevator;

import javax.inject.Named;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.PIDHandler;
import org.usfirst.frc.team1318.robot.common.wpilib.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.ITimer;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ElevatorMechanism implements IMechanism
{
    private final IMotor motor;
    private final IEncoder encoder;
    private Driver driver;

    private Floor requestedFloor;
    private PIDHandler pid;

    @Inject
    public ElevatorMechanism(
        IWpilibProvider provider,
        IDashboardLogger logger,
        ITimer timer)
    {
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);
        this.driver = null;

        this.requestedFloor = Floor.One;
        this.pid = new PIDHandler(1.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, "elevator", logger, timer);
    }

    @Override
    public void update()
    {
        boolean onePressed = this.driver.getDigital(Operation.ElevatorOneButton);
        boolean twoPressed = this.driver.getDigital(Operation.ElevatorTwoButton);
        boolean threePressed = this.driver.getDigital(Operation.ElevatorThreeButton);
        boolean fourPressed = this.driver.getDigital(Operation.ElevatorFourButton);
        boolean fivePressed = this.driver.getDigital(Operation.ElevatorFiveButton);

        if (onePressed)
        {
            this.requestedFloor = Floor.One;
        }
        else if (twoPressed)
        {
            this.requestedFloor = Floor.Two;
        }
        else if (threePressed)
        {
            this.requestedFloor = Floor.Three;
        }
        else if (fourPressed)
        {
            this.requestedFloor = Floor.Four;
        }
        else if (fivePressed)
        {
            this.requestedFloor = Floor.Five;
        }

        double currentHeight = this.encoder.getDistance();
        double desiredHeight = currentHeight;
        switch (this.requestedFloor)
        {
            case One:
                desiredHeight = HardwareConstants.ELEVATOR_FLOOR_ONE_HEIGHT;
                break;

            case Two:
                desiredHeight = HardwareConstants.ELEVATOR_FLOOR_TWO_HEIGHT;
                break;

            case Three:
                desiredHeight = HardwareConstants.ELEVATOR_FLOOR_THREE_HEIGHT;
                break;

            case Four:
                desiredHeight = HardwareConstants.ELEVATOR_FLOOR_FOUR_HEIGHT;
                break;

            case Five:
                desiredHeight = HardwareConstants.ELEVATOR_FLOOR_FIVE_HEIGHT;
                break;
        }

        this.motor.set(this.pid.calculatePosition(desiredHeight, currentHeight));
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

    private enum Floor
    {
        One, Two, Three, Four, Five;
    }
}
