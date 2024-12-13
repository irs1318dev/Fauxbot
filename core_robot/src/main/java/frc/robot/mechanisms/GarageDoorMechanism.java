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

//GarageDoor exercise introduces a non-trivial mechanism that controls a motor based on driver inputs + sensors

public class GarageDoorMechanism implements IMechanism
{
    // Defining
    // Motors
    private final IMotor motor;

    // Drivers
    private final IDriver driver;

    // Sensors
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeamSensor;

    // Boolean derived from sensors
    private boolean openedSensed;
    private boolean closedSensed;
    private boolean throughBeamBroken;

    // garage door state constructers
    private GarageDoorState state;

    private enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }


    @Inject
    public GarageDoorMechanism(IRobotProvider provider, IDriver driver) {
        // Defining ports ig
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_OPEN);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_CLOSED);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_THROUGH_BEAM);
        // Original state
        this.state = GarageDoorState.Closed;
        this.openedSensed = false;
        this.closedSensed = false;
        this.throughBeamBroken = false;
    }

    @Override
    public void readSensors()
    {
        this.openedSensed = openSensor.get();
        this.closedSensed = closedSensor.get();
        this.throughBeamBroken = throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        boolean buttonPressed = driver.getDigital(DigitalOperation.Button);

        if (null != state) switch (state) {
            case Opening -> {
                if (buttonPressed) {
                    this.state = GarageDoorState.Closing;
                }
                else if (openedSensed) {
                    this.state = GarageDoorState.Opened;
                }
            }
            case Opened -> {
                if (buttonPressed) {
                    this.state = GarageDoorState.Closing;
                }
                else if (openedSensed) {
                    this.state = GarageDoorState.Opened;
                }
            }
            case Closing -> {
                if (buttonPressed) {
                    this.state = GarageDoorState.Opening;
                }
                else if (closedSensed) {
                    this.state = GarageDoorState.Closed;
                }   if (throughBeamBroken) {
                    this.state = GarageDoorState.Opening;
                }
            }
            case Closed -> {
                if (buttonPressed) {
                    this.state = GarageDoorState.Opening;
                }
                else if (closedSensed) {
                    this.state = GarageDoorState.Closed;
                }   if (throughBeamBroken) {
                    this.state = GarageDoorState.Opening;
                }
            }
            default -> {
            }
        }

        if (null == this.state) {
            this.motor.set(0);
        }
        else //Begin execution for motor
        switch (this.state) {
            case Opening -> this.motor.set(1.0);
            case Closing -> this.motor.set(-1.0);
            default -> this.motor.set(0);
        }
    }

    @Override
    public void stop() {
        this.motor.set(1.0); // For opening the door
        this.motor.set(-1.0); // For closing the door
        this.motor.set(0); // For stopping the motor
    }


}
