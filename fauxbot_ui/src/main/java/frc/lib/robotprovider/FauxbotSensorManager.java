package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

public class FauxbotSensorManager
{
    public static final Map<FauxbotSensorConnection, FauxbotSensorBase> sensorMap = new HashMap<>();

    public static void set(FauxbotSensorConnection connection, FauxbotSensorBase sensor)
    {
        if (FauxbotSensorManager.sensorMap.containsKey(connection))
        {
            throw new RuntimeException("Don't expect port " + connection.toString() + " to be specified multiple times!");
        }

        FauxbotSensorManager.sensorMap.put(connection, sensor);
    }

    public static FauxbotSensorBase get(FauxbotSensorConnection connection)
    {
        if (!FauxbotSensorManager.sensorMap.containsKey(connection))
        {
            return null;
        }

        return FauxbotSensorManager.sensorMap.get(connection);
    }
}
