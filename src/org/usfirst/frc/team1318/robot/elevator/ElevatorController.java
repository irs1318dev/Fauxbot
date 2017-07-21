package org.usfirst.frc.team1318.robot.elevator;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.PIDHandler;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class ElevatorController implements IController
{
    private final ElevatorComponent component;
    private Driver driver;

    private Floor requestedFloor;
    private PIDHandler pid;

    @Inject
    public ElevatorController(
        ElevatorComponent component,
        IDashboardLogger logger,
        ITimer timer)
    {
        this.component = component;
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

        double currentHeight = this.component.getElevatorHeight();
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

        this.component.setMotorPower(this.pid.calculatePosition(desiredHeight, currentHeight));
    }

    @Override
    public void stop()
    {
        this.component.setMotorPower(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    private enum Floor
    {
        One,
        Two,
        Three,
        Four,
        Five;
    }
}
