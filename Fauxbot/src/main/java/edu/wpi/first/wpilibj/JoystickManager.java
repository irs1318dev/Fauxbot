package edu.wpi.first.wpilibj;

import java.util.HashMap;
import java.util.Map;

public class JoystickManager
{
    private static Map<Integer, Joystick> joystickMap = new HashMap<Integer, Joystick>();
    private static int highestPort = 0;

    public static void set(int port, Joystick sensor)
    {
        JoystickManager.joystickMap.put(port, sensor);
        if (JoystickManager.highestPort < port)
        {
            JoystickManager.highestPort = port;
        }
    }

    public static Joystick get(int port)
    {
        if (!JoystickManager.joystickMap.containsKey(port))
        {
            return null;
        }

        return JoystickManager.joystickMap.get(port);
    }

    public static int getHightestPort()
    {
        return JoystickManager.highestPort;
    }
}
