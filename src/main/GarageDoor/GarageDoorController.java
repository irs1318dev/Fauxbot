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
		this.state = GarageDoorState.Closed;
		this.component = component;
	}

	@Override
	public void setDriver(Driver driver)
	{
		this.driver = driver;
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
		else if (this.state == GarageDoorState.Closing)
		{
			if (this.component.isClosed())
			{
				this.state = GarageDoorState.Closed;
			}

			if (buttonPressed)
			{
				this.state = GarageDoorState.Opening;
			}
		}
		else if (this.state == GarageDoorState.Opening)
		{
			if (this.component.isOpen())
			{
				this.state = GarageDoorState.Open;
			}

			if (buttonPressed)
			{
				this.state = GarageDoorState.Closing;
			}
		}
		else if (this.state == GarageDoorState.Open)
		{
			if (buttonPressed)
			{
				this.state = GarageDoorState.Closing;
			}
		}

		switch (this.state)
		{
			case Open:
				this.component.setMotor(0);
				break;

			case Closed:
				this.component.setMotor(0);
				break;

			case Closing:
				this.component.setMotor(-1);
				break;

			case Opening:
				this.component.setMotor(1);
				break;
		}
	}

	@Override
	public void stop()
	{
	}
	private enum GarageDoorState
	{
		Closed,
		Opening,
		Open,
		Closing;
	}
}
