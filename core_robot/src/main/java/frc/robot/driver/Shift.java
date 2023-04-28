package frc.robot.driver;

import frc.lib.helpers.FlagBase;

public class Shift extends FlagBase
{
    // Note - the integer passed into shift should be the next single bit (0, 1, 2, 4, 8, 16, 32, ...)
    public static final Shift None = new Shift(0x0);
    public static final Shift DriverDebug = new Shift(0x1);
    public static final Shift CodriverDebug = new Shift(0x2);
    public static final Shift Test1Debug = new Shift(0x4);
    public static final Shift Test2Debug = new Shift(0x8);

    public static final Shift[] AllShifts = new Shift[] { Shift.None, Shift.DriverDebug, Shift.CodriverDebug, Shift.Test1Debug, Shift.Test2Debug };

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
