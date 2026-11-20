package frc.robot.driver.mechanisms;

import javax.inject.Singleton;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.DigitalOperation;

@Singleton 
public class GarageDoorMechanism implements IMechanism {

    private final IDriver driver;
    private final IMotor motor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeamSensor;

    public enum GarageDoorState {
        Open,
        Closed,
        Opening,
        Closing
    }

    private boolean openSensorSetting;
    private boolean closedSensorSetting;
    private boolean throughBeamSensorSetting;

    private GarageDoorState garageDoorState;

    @Inject 
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;

        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR_PWM_CHANNEL);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_OPEN_SENSOR_IO_CHANNEL);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_CLOSED_SENSOR_IO_CHANNEL);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_THROUGH_BEAM_SENSOR_IO_CHANNEL);

        this.openSensorSetting = false;
        this.closedSensorSetting = false;
        this.throughBeamSensorSetting = false;

        this.garageDoorState = GarageDoorState.Closed;
    }

    @Override
    public void readSensors() {
        this.openSensorSetting = this.openSensor.get();
        this.closedSensorSetting = this.closedSensor.get();
        this.throughBeamSensorSetting = this.throughBeamSensor.get();
    }

    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.GarageDoor)) {
            switch (garageDoorState) {
                case Open:
                    garageDoorState = GarageDoorState.Closing;
                    this.motor.set(TuningConstants.GARAGE_DOOR_CLOSING_POWER);
                    break;
                case Closed:
                    garageDoorState = GarageDoorState.Opening;
                    this.motor.set(TuningConstants.GARAGE_DOOR_OPENING_POWER);
                    break;
                case Opening:
                    garageDoorState = GarageDoorState.Closing;
                    this.motor.set(TuningConstants.GARAGE_DOOR_CLOSING_POWER);
                    break;
                case Closing:
                    garageDoorState = GarageDoorState.Opening;
                    this.motor.set(TuningConstants.GARAGE_DOOR_OPENING_POWER);
                    break;
            }
        }
        if (garageDoorState == GarageDoorState.Closing && this.throughBeamSensorSetting) {
            garageDoorState = GarageDoorState.Opening;
            this.motor.set(TuningConstants.GARAGE_DOOR_OPENING_POWER);
        }
        if (garageDoorState == GarageDoorState.Opening && this.openSensorSetting) {
            garageDoorState = GarageDoorState.Open;
            this.motor.set(0);
        }
        if (garageDoorState == GarageDoorState.Closing && this.closedSensorSetting) {
            garageDoorState = GarageDoorState.Closed;
            this.motor.set(0);
        }
    }

    @Override
    public void stop() {
        this.motor.set(0);
    }

}
