package frc.lib.helpers;

import frc.robot.TuningConstants;

public class ExceptionHelpers
{
    /**
     * Assert the the provided condition is true.  If not: throw an exception when we are doing so (non-competition), or print a string to standard error.
     * @param condition to verify is true
     * @param errorString to use when the condition is not true
     */
    public static void Assert(boolean condition, String errorString)
    {
        if (!condition)
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(errorString);
            }
            else
            {
                System.err.println(errorString);
            }
        }
    }

    public static String exceptionString(Exception ex)
    {
        StringBuilder b = new StringBuilder();
        b.append(ex.toString());
        b.append("\r\n");
        for (StackTraceElement stackTraceElement : ex.getStackTrace())
        {
            b.append(stackTraceElement.toString());
            b.append("\r\n");
        }

        return b.toString();
    }
}
