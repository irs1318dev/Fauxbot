package frc.robot.mechanisms;

import frc.robot.common.IMechanism;

import frc.robot.common;
import java.security.Provider;

import org.yaml.snakeyaml.events.Event.ID;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.robot.ElectronicsConstants;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.LoggingManager;
import frc.robot.common.robotprovider;
import frc.robot.driver;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.common.IDriver;

@Singleton

public class GarageDoorMechanisms implements IMechanism {

    private final frc.lib.driver.IDriver driver;
    private final IMotor motor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;
    private final IDigitalInput throughBeamSensor;
    private boolean openedSensed;
    private boolean closedSensed;
    private boolean throughBeamBroken;
    public enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }

    private GarageDoorState state;

    @Inject

    public GarageDoorMechanism(IDriver driver, IRobotProvider provider)(
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGE_DOOR_MOTOR);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_OPEN);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGE_DOOR_SENSOR_CLOSED);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GET_DOOR_THROUGH_BEAM);
        this.state = GarageDoorState.Closed;
        this.openSensor = false;
        this.closedSensed = false;
        this.throughBeamBroken = false;

    )   
    
    


    @Override
    public void readSensors() {
        this.openedSensed = this.openSensor.get();
        this.closedSensed = this.closedSensor.get();
        this.throughBeamBroken = this.throughBeamSensor.get();
    }
    @Override
    public void update () {
        boolean buttonPressed = driver.getDigital(DigitalOperation.Button)

        if (state == GarageDoorState.Opening)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }

            if (openedSensed)
            {
                this.state = GarageDoorState.Opened;
            }

        }
        else if (state == GarageDoorState.Closing)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
            else if (closedSensed)
            {
                this.state = GarageDoorState.Closed;
            }
            if (throughBeamBroken)
            {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (state == GarageDoorState.Closed)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
            else if (closedSensed)
            {
                this.state = GarageDoorState.Closed;
            }
            if (throughBeamBroken)
            {
                this.state = GarageDoorState.Opening;
            }
    }

    if (this.state == GarageDoorState.Opening)
    {
        this.motor.set(1.0);
    }
    else if (this.state == GarageDoorState.Closing)
    {
        this.motor.set(-1,0);
    }
    else
    {
        this.motor.set(0);
    }
    @Override
    public void stop () {
        this.motor.set(0);
    }
}

    
}
