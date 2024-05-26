package frc.lib.robotprovider;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.robot.LoggingKey;

@Singleton
public class FauxbotSmartDashboardLogger extends StringLogger implements ISmartDashboardLogger
{
    private HashMap<LoggingKey, String> loggedStrings;

    @Inject
    public FauxbotSmartDashboardLogger()
    {
        this.loggedStrings = new HashMap<LoggingKey, String>();
    }

    @Override
    public void internalLogString(LoggingKey key, String value)
    {
        synchronized (this)
        {
            this.loggedStrings.put(key, value);
        }
    }

    public String getString(LoggingKey key)
    {
        synchronized (this)
        {
            if (this.loggedStrings.containsKey(key))
            {
                return this.loggedStrings.get(key);
            }

            return null;
        }
    }
}
