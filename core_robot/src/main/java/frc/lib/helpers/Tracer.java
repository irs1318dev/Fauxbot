package frc.lib.helpers;

import frc.robot.TuningConstants;

public class Tracer
{
    public static boolean SHOULD_LOG = false;

    public static void trace(String format, Object... args)
    {
        if (TuningConstants.TRACER_ENABLED && Tracer.SHOULD_LOG)
        {
            String message = format;
            if (args != null && args.length > 0)
            {
                message = String.format(format, args);
            }

            System.out.println(String.valueOf(System.currentTimeMillis()) + " " + message);
        }
    }
}
