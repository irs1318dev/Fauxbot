package main.Common;

import java.util.HashMap;
import java.util.Map;

public class SensorManager
{
	private static Map<Integer, Sensor> sensorMap = new HashMap<Integer, Sensor>();

	public static void set(int port, Sensor sensor)
	{
		SensorManager.sensorMap.put(port, sensor);
	}

	public static Sensor get(int port)
	{
		if (!SensorManager.sensorMap.containsKey(port))
		{
			return null;
		}

		return SensorManager.sensorMap.get(port);
	}
}
