package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.controllers.ADRCHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

public class ElevatorMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor motor;
    private final IEncoder encoder;
    private final ADRCHandler adrcHandler;
    private double targetPosition;
    private double currentPosition;

    @Inject
    public ElevatorMechanism(IDriver driver, IRobotProvider provider, ITimer timer) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        this.adrcHandler = new ADRCHandler(
            TuningConstants.ELEVATOR_ADRC_OC,
            TuningConstants.ELEVATOR_ADRC_OO,
            TuningConstants.ELEVATOR_ADRC_B0,
            TuningConstants.ELEVATOR_MIN_OUTPUT,
            TuningConstants.ELEVATOR_MAX_OUTPUT,
            timer
            ); // Init pid handler
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);
    }

    @Override
    public void readSensors() {
        this.currentPosition = this.encoder.getDistance();
    }


    @Override
    public void update(RobotMode mode) {

        if (this.driver.getDigital(DigitalOperation.ElevatorFloor1)){
            this.targetPosition = TuningConstants.FLOOR_1;
        } else if (this.driver.getDigital(DigitalOperation.ElevatorFloor2)){
            this.targetPosition = TuningConstants.FLOOR_2;
        } else if (this.driver.getDigital(DigitalOperation.ElevatorFloor3)){
            this.targetPosition = TuningConstants.FLOOR_3;
        } else if (this.driver.getDigital(DigitalOperation.ElevatorFloor4)){
            this.targetPosition = TuningConstants.FLOOR_4;
        } else if (this.driver.getDigital(DigitalOperation.ElevatorFloor5)){
            this.targetPosition = TuningConstants.FLOOR_5;
        }
        double motorOutput = this.adrcHandler.calculatePosition(this.targetPosition, this.currentPosition);
        this.motor.set(motorOutput);
    }

    @Override
    public void stop() {
        this.motor.set(0);
    }
}