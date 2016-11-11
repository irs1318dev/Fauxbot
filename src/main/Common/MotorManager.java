package main.Common;

import java.util.HashMap;
import java.util.Map;

public class MotorManager
{
	private static Map<Integer, Motor> motorMap = new HashMap<Integer, Motor>();

	public static void set(int port, Motor motor)
	{
		MotorManager.motorMap.put(port, motor);
	}

	public static Motor get(int port)
	{
		if (!MotorManager.motorMap.containsKey(port))
		{
			return null;
		}

		return MotorManager.motorMap.get(port);
	}
}