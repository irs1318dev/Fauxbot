package frc.lib.robotprovider;

public class TalonXLimitSwitchStatus
{
    public final boolean isForwardClosed;
    public final boolean isReverseClosed;

    public TalonXLimitSwitchStatus(boolean isForwardClosed, boolean isReverseClosed)
    {
        this.isForwardClosed = isForwardClosed;
        this.isReverseClosed = isReverseClosed;
    }
}
