package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.lib.controllers.*;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.*;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

import com.google.inject.Inject;

@Singleton
public class ElevatorMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor motor;
    private final IEncoder encoder;

    private Floor requestedFloor;
    private PIDHandler pid;
    
    private double currentHeight;

    @Inject
    public ElevatorMechanism(
        IDriver driver,
        IRobotProvider provider,
        ITimer timer)
    {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_PWM_CHANNEL);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_DIGITAL_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_DIGITAL_CHANNEL_B);

        this.requestedFloor = Floor.One;
        this.pid = new PIDHandler(
            TuningConstants.ELEVATOR_MOTOR_KP,
            TuningConstants.ELEVATOR_MOTOR_KI,
            TuningConstants.ELEVATOR_MOTOR_KD,
            TuningConstants.ELEVATOR_MOTOR_KF,
            TuningConstants.ELEVATOR_MOTOR_KS,
            TuningConstants.ELEVATOR_MOTOR_MIN_POWER,
            TuningConstants.ELEVATOR_MOTOR_MAX_POWER,
            timer);
    }
    
    @Override
    public void readSensors()
    {
        // read sensors
        this.currentHeight = this.encoder.getDistance();
    }

    @Override
    public void update()
    {
        if (this.driver.getDigital(DigitalOperation.ElevatorOneButton))
        {
            this.requestedFloor = Floor.One;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorTwoButton))
        {
            this.requestedFloor = Floor.Two;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorThreeButton))
        {
            this.requestedFloor = Floor.Three;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorFourButton))
        {
            this.requestedFloor = Floor.Four;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorFiveButton))
        {
            this.requestedFloor = Floor.Five;
        }

        double desiredHeight = this.currentHeight;
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

        this.motor.set(this.pid.calculatePosition(desiredHeight, this.currentHeight));
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    private enum Floor
    {
        One, Two, Three, Four, Five;
    }
}