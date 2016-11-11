package main.Common;

public class Motor
{
	private double currentSpeed; 

	public Motor(int port)
	{
		this.currentSpeed = 0.0;
		MotorManager.set(port, this);
	}

	public void set(double newValue)
	{
		this.currentSpeed = newValue;
	}

	public double get()
	{
		return this.currentSpeed;
	}
}
