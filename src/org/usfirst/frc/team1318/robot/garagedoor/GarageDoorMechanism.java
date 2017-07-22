package org.usfirst.frc.team1318.robot.garagedoor;

import javax.inject.Named;

import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class GarageDoorMechanism implements IMechanism
{
    private final IMotor motor;
    private final IDigitalInput throughBeamSensor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;

    private Driver driver;
    private GarageDoorState state;

    @Inject
    public GarageDoorMechanism(
        @Named("GARAGEDOOR_MOTOR") IMotor motor,
        @Named("GARAGEDOOR_THROUGHBEAM_SENSOR") IDigitalInput throughBeamSensor,
        @Named("GARAGEDOOR_OPEN_SENSOR") IDigitalInput openSensor,
        @Named("GARAGEDOOR_CLOSED_SENSOR") IDigitalInput closedSensor)
    {
        this.motor = motor;
        this.throughBeamSensor = throughBeamSensor;
        this.openSensor = openSensor;
        this.closedSensor = closedSensor;

        this.driver = null;
        this.state = GarageDoorState.Closed;
    }

    @Override
    public void update()
    {
        boolean buttonPressed = this.driver.getDigital(Operation.GarageDoorButton);
        if (this.state == GarageDoorState.Closed)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Opening;
            }
        }
        else if (this.state == GarageDoorState.Open)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
        }
        else if (this.state == GarageDoorState.Opening)
        {
            if (buttonPressed)
            {
                this.state = GarageDoorState.Closing;
            }
            else if (this.openSensor.get())
            {
                this.state = GarageDoorState.Open;
            }
        }
        else // if (this.state == GarageDoorState.Closing)
        {
            if (buttonPressed || this.throughBeamSensor.get())
            {
                this.state = GarageDoorState.Opening;
            }
            else if (this.closedSensor.get())
            {
                this.state = GarageDoorState.Closed;
            }
        }

        switch (this.state)
        {
            case Closed:
            case Open:
                this.motor.set(0.0);
                break;

            case Opening:
                this.motor.set(1.0);
                break;

            case Closing:
                this.motor.set(-1.0);
                break;
        }
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

    private enum GarageDoorState
    {
        Open, Closing, Closed, Opening;
    }
}
