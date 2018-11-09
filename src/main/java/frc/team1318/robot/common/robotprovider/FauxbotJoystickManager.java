package frc.team1318.robot.common.robotprovider;

import java.util.HashMap;
import java.util.Map;

public class FauxbotJoystickManager
{
    private static Map<Integer, FauxbotJoystick> joystickMap = new HashMap<Integer, FauxbotJoystick>();
    private static int highestPort = 0;

    public static void set(int port, FauxbotJoystick sensor)
    {
        FauxbotJoystickManager.joystickMap.put(port, sensor);
        if (FauxbotJoystickManager.highestPort < port)
        {
            FauxbotJoystickManager.highestPort = port;
        }
    }

    public static FauxbotJoystick get(int port)
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
