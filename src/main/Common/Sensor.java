package main.Common;

public class Sensor
{
	private boolean isSet; 

	public Sensor(int port)
	{
		this.isSet = false;
		SensorManager.set(port, this);
	}

	/**
	 * gets the current value of the sensor
	 * @return true if the sensor is set (closed), false otherwise (open)
	 */
	public boolean get()
	{
		return this.isSet;
	}

	public void set(boolean newValue)
	{
		this.isSet = newValue;
	}
}
