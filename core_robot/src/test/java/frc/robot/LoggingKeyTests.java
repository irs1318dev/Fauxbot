package frc.robot;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class LoggingKeyTests
{
    @Test
    public void verifyLoggingKeyOverlap()
    {
        HashSet<String> loggingKeys = new HashSet<String>();
        for (LoggingKey key : LoggingKey.values())
        {
            if (!loggingKeys.add(key.value))
            {
                throw new RuntimeException(key.value);
            }
        }
    }
}