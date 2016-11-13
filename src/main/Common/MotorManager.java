package main.Common;

import java.util.HashMap;
import java.util.Map;

public class MotorManager
{
    private static Map<Integer, Motor> motorMap = new HashMap<>();
    private static int highestPort = 0;

    public static void set(int port, Motor sensor)
    {
        MotorManager.motorMap.put(port, sensor);
        if (MotorManager.highestPort < port)
        {
            MotorManager.highestPort = port;
        }
    }

    public static Motor get(int port)
    {
        if (!MotorManager.motorMap.containsKey(port))
        {
            return null;
        }

        return MotorManager.motorMap.get(port);
    }

    public static int getHightestPort()
    {
        return MotorManager.highestPort;
    }
}
