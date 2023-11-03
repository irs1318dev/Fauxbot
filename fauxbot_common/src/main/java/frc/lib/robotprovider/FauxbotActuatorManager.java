package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

public class FauxbotActuatorManager
{
    public static final Map<FauxbotActuatorConnection, FauxbotActuatorBase> actuatorMap = new HashMap<>();

    public static void set(FauxbotActuatorConnection connection, FauxbotActuatorBase actuator)
    {
        if (FauxbotActuatorManager.actuatorMap.containsKey(connection))
        {
            throw new RuntimeException("Don't expect connection " + connection.toString() + " to be specified multiple times!");
        }

        FauxbotActuatorManager.actuatorMap.put(connection, actuator);
    }

    public static FauxbotActuatorBase get(FauxbotActuatorConnection connection)
    {
        if (!FauxbotActuatorManager.actuatorMap.containsKey(connection))
        {
            return null;
        }

        return FauxbotActuatorManager.actuatorMap.get(connection);
    }
}
