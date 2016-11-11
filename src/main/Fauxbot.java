package main;

import java.io.IOException;
import java.util.Scanner;

import main.Common.Motor;
import main.Common.MotorManager;
import main.Common.Sensor;
import main.Common.SensorManager;
import main.Driver.Driver;
import main.Driver.Operation;

public class Fauxbot
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		Driver driver = new Driver();
		ComponentManager components = new ComponentManager();
		ControllerManager controllers = new ControllerManager(components);
		controllers.setDriver(driver);
		controllers.update();

		Scanner inScanner = new Scanner(System.in);
		boolean run = true;
		while (run)
		{
			controllers.update();

			int i = 0;
			String[] partsToPrint = new String[4];
			Sensor throughBeamSensor = SensorManager.get(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM_CHANNEL);
			Sensor openSensor = SensorManager.get(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
			Sensor closedSensor = SensorManager.get(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
			Motor motor = MotorManager.get(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
			if (motor != null)
			{
				partsToPrint[i++] = "current Motor speed: " + motor.get();
			}

			if (throughBeamSensor != null)
			{
				partsToPrint[i++] = "throughBeam: " + throughBeamSensor.get();
			}

			if (openSensor != null)
			{
				partsToPrint[i++] = "openSensor: " + openSensor.get();
			}

			if (closedSensor != null)
			{
				partsToPrint[i++] = "closedSensor: " + closedSensor.get();
			}

			System.out.println(String.join(", ", partsToPrint));

			int available = System.in.available();
			if (available > 0)
			{
				String inString = null;
				byte[] b = new byte[available];
				int read = System.in.read(b);
				if (read > 0)
				{
					inString = new String(b);
				}

				if (inString != null && !inString.isEmpty())
				{
					if (inString.contains("t") && throughBeamSensor != null)
					{
						throughBeamSensor.set(!throughBeamSensor.get());
					}

					if (inString.contains("o") && openSensor != null)
					{
						openSensor.set(!openSensor.get());
					}

					if (inString.contains("c") && closedSensor != null)
					{
						closedSensor.set(!closedSensor.get());
					}

					if (inString.contains(" "))
					{
						driver.pressButton(Operation.GarageDoorButton);
					}

					if (inString.contains("q"))
					{
						run = false;
					}
				}
			}

			Thread.sleep(10);
		}

		inScanner.close();
	}
}
