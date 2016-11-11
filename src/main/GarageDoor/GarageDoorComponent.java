package main.GarageDoor;

import main.ElectronicsConstants;
import main.Common.Motor;
import main.Common.Sensor;

public class GarageDoorComponent
{
	private Motor motor;
	private Sensor throughBeam;
	private Sensor openSensor;
	private Sensor closedSensor;
	
	public GarageDoorComponent()
	{
		this.motor = new Motor(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
		this.throughBeam = new Sensor(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM_CHANNEL);
		this.openSensor = new Sensor(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
		this.closedSensor = new Sensor(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
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
