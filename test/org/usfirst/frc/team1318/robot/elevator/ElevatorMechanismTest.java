package org.usfirst.frc.team1318.robot.elevator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TestWpilibProvider;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilib.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.ITimer;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

public class ElevatorMechanismTest
{
    @Test
    public void testUpdateUpWhenTwoPressed()
    {
        IDashboardLogger logger = mock(IDashboardLogger.class);
        ITimer timer = mock(ITimer.class);
        TestWpilibProvider testProvider = new TestWpilibProvider();
        IMotor elevatorMotor = testProvider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        IEncoder elevatorEncoder = testProvider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);

        ElevatorMechanism e = new ElevatorMechanism(testProvider, logger, timer);

        Driver driver = mock(Driver.class);
        e.setDriver(driver);

        doReturn(false).when(driver).getDigital(Operation.ElevatorOneButton);
        doReturn(true).when(driver).getDigital(Operation.ElevatorTwoButton);
        doReturn(false).when(driver).getDigital(Operation.ElevatorThreeButton);
        doReturn(false).when(driver).getDigital(Operation.ElevatorFourButton);
        doReturn(false).when(driver).getDigital(Operation.ElevatorFiveButton);
        doReturn(0.0).when(elevatorEncoder).getDistance();
        doReturn(1.0).when(timer).get();

        e.update();

        verify(elevatorMotor).set(eq(1.0));
        verify(elevatorEncoder).getDistance();
        verifyNoMoreInteractions(elevatorMotor, elevatorEncoder);
    }
}
