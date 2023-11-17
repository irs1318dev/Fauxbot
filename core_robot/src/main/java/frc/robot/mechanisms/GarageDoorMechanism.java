package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.*;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.*;

import com.google.inject.Inject;

@Singleton
public class GarageDoorMechanism implements IMechanism
{
    private final IDriver driver;
    private final IMotor motor;
    private final IDigitalInput throughBeamSensor;
    private final IDigitalInput openSensor;
    private final IDigitalInput closedSensor;

    private GarageDoorState state;

    private boolean isOpen;
    private boolean isClosed;
    private boolean isThroughBeamSensorBroken;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.GARAGEDOOR_MOTOR_PWM_CHANNEL);
        this.throughBeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAMSENSOR_DIGITAL_CHANNEL);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_DIGITAL_CHANNEL);
        this.closedSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_DIGITAL_CHANNEL);

        this.state = GarageDoorState.Closed;

        this.isOpen = false;
        this.isClosed = false;
        this.isThroughBeamSensorBroken = false;
    }

    @Override
    public void readSensors()
    {
        this.isOpen = this.openSensor.get();
        this.isClosed = this.closedSensor.get();
        this.isThroughBeamSensorBroken = this.throughBeamSensor.get();
    }

    @Override
    public void update()
    {
        boolean buttonPressed = this.driver.getDigital(DigitalOperation.GarageDoorButton);
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
            else if (this.isOpen)
            {
                this.state = GarageDoorState.Open;
            }
        }
        else // if (this.state == GarageDoorState.Closing)
        {
            if (buttonPressed || this.isThroughBeamSensorBroken)
            {
                this.state = GarageDoorState.Opening;
            }
            else if (this.isClosed)
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
                this.motor.set(TuningConstants.GARAGEDOOR_OPENING_POWER);
                break;

            case Closing:
                this.motor.set(TuningConstants.GARAGEDOOR_CLOSING_POWER);
                break;
        }
    }

    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    private enum GarageDoorState
    {
        Open, Closing, Closed, Opening;
    }
}