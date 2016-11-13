package main.GarageDoor;

import main.ElectronicsConstants;
import main.Common.Motor;
import main.Common.DigitalInput;

public class GarageDoorComponent
{
	private Motor motor;
	private DigitalInput throughBeam;
	private DigitalInput openSensor;
	private DigitalInput closedSensor;
	
	public GarageDoorComponent()
	{
		this.motor = new Motor(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
		this.throughBeam = new DigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM_CHANNEL);
		this.openSensor = new DigitalInput(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
		this.closedSensor = new DigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
	}

	public boolean isBeamBroken()
	{
		return this.throughBeam.get();
	}

	public boolean isOpen()
	{
		return this.openSensor.get();
	}

	public boolean isClosed()
	{
		return this.closedSensor.get();
	}

	public void setMotor(double speed)
	{
		this.motor.set(speed);
	}
}
