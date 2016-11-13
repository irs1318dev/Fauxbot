package main.GarageDoor;

import main.Common.IController;
import main.Driver.Driver;
import main.Driver.Operation;

public class GarageDoorController implements IController
{
    private GarageDoorComponent component;
    private Driver driver;
    private GarageDoorState state;

    public GarageDoorController(GarageDoorComponent component)
    {
        this.component = component;
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
            else if (this.component.isOpen())
            {
                this.state = GarageDoorState.Open;
            }
        }
        else // if (this.state == GarageDoorState.Closing)
        {
            if (buttonPressed || this.component.isBlocked())
            {
                this.state = GarageDoorState.Opening;
            }
            else if (this.component.isClosed())
            {
                this.state = GarageDoorState.Closed;
            }
        }

        switch (this.state)
        {
            case Closed:
            case Open:
                this.component.setMotorPower(0.0);
                break;

            case Opening:
                this.component.setMotorPower(1.0);
                break;

            case Closing:
                this.component.setMotorPower(-1.0);
                break;
        }
    }

    @Override
    public void stop()
    {
        this.component.setMotorPower(0.0);
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
