package edu.wpi.first.wpilibj;

import java.util.HashMap;
import java.util.Map;

public class SensorManager
{
    private static Map<Integer, SensorBase> sensorMap = new HashMap<>();
    private static int highestPort = 0;

    public static void set(int port, SensorBase sensor)
    {
        if (SensorManager.sensorMap.containsKey(port))
        {
            throw new RuntimeException("Don't expect port " + port + " to be specified multiple times!");
        }

        SensorManager.sensorMap.put(port, sensor);
        if (SensorManager.highestPort < port)
        {
            SensorManager.highestPort = port;
        }
    }

    public static SensorBase get(int port)
    {
        if (!SensorManager.sensorMap.containsKey(port))
        {
            return null;
        }

        return SensorManager.sensorMap.get(port);
    }

    public static int getHightestPort()
    {
        return SensorManager.highestPort;
    }
}
