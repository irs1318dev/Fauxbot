package edu.wpi.first.wpilibj;

import java.util.HashMap;
import java.util.Map;

public class MotorManager
{
    private static Map<Integer, MotorBase> motorMap = new HashMap<>();
    private static int highestPort = 0;

    public static void set(int port, MotorBase sensor)
    {
        MotorManager.motorMap.put(port, sensor);
        if (MotorManager.highestPort < port)
        {
            MotorManager.highestPort = port;
        }
    }

    public static MotorBase get(int port)
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
