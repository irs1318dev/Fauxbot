package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

public class FauxbotJoystickManager
{
    private static Map<Integer, IJoystick> joystickMap = new HashMap<Integer, IJoystick>();
    private static int highestPort = 0;

    public static void set(int port, IJoystick sensor)
    {
        FauxbotJoystickManager.joystickMap.put(port, sensor);
        if (FauxbotJoystickManager.highestPort < port)
        {
            FauxbotJoystickManager.highestPort = port;
        }
    }

    public static IJoystick get(int port)
    {
        if (!FauxbotJoystickManager.joystickMap.containsKey(port))
        {
            return null;
        }

        return FauxbotJoystickManager.joystickMap.get(port);
    }

    public static int getHightestPort()
    {
        return FauxbotJoystickManager.highestPort;
    }
}
