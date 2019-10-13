package frc.robot.driver;

import frc.robot.common.FlagBase;

public class Shift extends FlagBase
{
    // Note - the integer passed into shift should be the next single bit (0, 1, 2, 4, 8, 16, 32, ...)
    public static final Shift None = new Shift(0x0);
    public static final Shift Debug = new Shift(0x1);
    public static final Shift ButtonPadDebug = new Shift(0x2);

    public static final Shift[] AllShifts = new Shift[] { Shift.None, Shift.Debug, Shift.ButtonPadDebug };

    public Shift(int value)
    {
        super(value);
    }

    public static Shift Union(Shift... shifts)
    {
        return new Shift(FlagBase.Union(shifts));
    }

    public static Shift Intersect(Shift... shifts)
    {
        return new Shift(FlagBase.Intersect(shifts));
    }
}
