package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

public class GarageDoorMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor motor;
    private boolean pressed;

    private enum State
    {
        Opening,
        Closing,
        Open,
        Closed,
    }

    private State currentState;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeam;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR_CHANNEL);
        this.currentState = State.Closed;

        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_OPEN_SENSOR_CHANNEL); // Fill in with correct implementation
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_CLOSED_SENSOR_CHANNEL); // Fill in with correct implementation
        this.throughBeam = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_THROUGH_BEAM); // Fill in with correct implementation

    }

    @Override
    public void readSensors() {

        if (this.closedSensor.get() && this.currentState == State.Closing) { // Fill in with correct implementation
            this.currentState = State.Closed;
        }
        else {
            if (this.openSensor.get() && this.currentState == State.Opening) { // Fill in with correct implementation
                this.currentState = State.Open;
            }
        }
        if (this.throughBeam.get()) {
            this.currentState = State.Opening;
        }
    }


    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.GarageToggle) && !this.pressed) {
            this.pressed = true;
            if (this.currentState == State.Closed || this.currentState == State.Closing) {
                this.currentState = State.Opening;
            }
            else if (this.currentState == State.Open || this.currentState == State.Opening) {
                this.currentState = State.Closing;
            }
        }
        else if (!this.driver.getDigital(DigitalOperation.GarageToggle)) {
            this.pressed = false;
        }

        if (this.currentState == State.Opening) {
            this.motor.set(1.0);
        }
        if (this.currentState == State.Closing) {
            this.motor.set(-1.0);
        }
    }

    @Override
    public void stop() {
        this.motor.set(0);
    }
}