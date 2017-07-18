package edu.wpi.first.wpilibj;

import java.util.HashMap;
import java.util.Map;

public class ActuatorManager
{
    private static Map<Integer, ActuatorBase> actuatorMap = new HashMap<>();
    private static int highestPort = 0;

    public static void set(int port, ActuatorBase sensor)
    {
        ActuatorManager.actuatorMap.put(port, sensor);
        if (ActuatorManager.highestPort < port)
        {
            ActuatorManager.highestPort = port;
        }
    }

    public static ActuatorBase get(int port)
    {
        if (!ActuatorManager.actuatorMap.containsKey(port))
        {
            return null;
        }

        return ActuatorManager.actuatorMap.get(port);
    }

    public static int getHightestPort()
    {
        return ActuatorManager.highestPort;
    }
}
