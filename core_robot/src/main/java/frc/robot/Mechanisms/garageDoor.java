package frc.robot.Mechanisms;

import frc.robot.common.Imechanism;
import frc.robot.common.*;
import java.security.Provider;

import com.google.inject.Inject;
import com.google.inject.singleton;

import frc.lib.mechanisms.GarageDoor;
import frc.lib.mechanisms.GarageDoor.GarageDoorState;
import frc.robot.EletronicsConstants;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.LoggingManager;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.Idriver;

//GarageDoor exercise introduces a non-trivial mechanism that controls a motor based on driver inputs + sensors

public class garageDoor implements IMechanism {
    // Defining
    // Motors
    private final IMotor motor;

    // Drivers
    private final IDriver driver;

    // Sensors
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDIgitalInput throughBeamSensor;

    // Boolean derived from sensors
    private boolean openedSensed;
    private boolean closedSensed;
    private boolean throughBeamBroken;

    // garage door state constructers
    public enum GarageDoorState {
        Opened,
        Opening,
        Closed,
        Closing
    }
    private GarageDoorState state;


    @Inject
    public GarageDoor(IRobotProvider provider, IDriver driver) {
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

    }

    @Override
    public void update() {
        boolean buttonPressed = driver.getDigital(DigitalOperation.Button);

        if (state == GarageDoorState.Opening) {
            if (buttonPressed) {
                this.state = GarageDoorState.Closing;
            }
            else if (openedSensed) {
                this.state = GarageDoorState.Opened;
            }
        }

        else if (state == GarageDoorState.Opened) {
            if (buttonPressed) {
                this.state = GarageDoorState.Closing;
            }
            else if (openedSensed) {
                this.state = GarageDoorState.Opened;
            }
        }

        else if (state == GarageDoorState.Closing) {
            if (buttonPressed) {
                this.state = GarageDoorState.Opening;
            }
            else if (closedSensed) {
                this.state = GarageDoorState.Closed;
            }
            if (throughBeamBroken) {
                this.state = GarageDoorState.Opening;
            }
        }

        else if (state == GarageDoorState.Closed) {
            if (buttonPressed) {
                this.state = GarageDoorState.Opening;
            }
            else if (closedSensed) {
                this.state = GarageDoorState.Closed;
            }
            if (throughBeamBroken) {
                this.state = GarageDoorState.Opening;
            }
        }

        //Begin execution for motor
        if (this.state == GarageDoorState.Opening) {
            this.motor.set(power:1.0);
        }
        else if (this.state == GarageDoorState.Closing) {
            this.motor.set(-1.0);
        }
        else {
            this.motor.set(power:0);
        }
    }

    @Override
    public void stop() {
        this.motor.set(power:0);
    }


}
