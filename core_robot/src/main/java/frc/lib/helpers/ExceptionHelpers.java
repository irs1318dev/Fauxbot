package frc.lib.helpers;

import frc.robot.TuningConstants;

/**
 * Helper functions for Exceptions
 */
public class ExceptionHelpers
{
    /**
     * Assert the the provided condition is true.  If not: throw an exception when we are doing so (non-competition), or print a string to standard error.
     * @param condition to verify is true
     * @param format string to use to use for the exception/print when the condition is not true
     * @param args to use for the format string
     */
    public static void Assert(boolean condition, String format, Object... args)
    {
        if (!condition)
        {
            String errorMessage = format;
            if (args != null && args.length > 0)
            {
                errorMessage = String.format(format, args);
            }

            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException(errorMessage);
            }
            else
            {
                System.err.println(errorMessage);
            }
        }
    }

    /**
     * Generate a string including the exception message as well as the callstack
     * @param ex to generate string for
     * @return generated string about the exception
     */
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
