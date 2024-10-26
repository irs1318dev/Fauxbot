package frc.lib.robotprovider;

/**
 * Represents the current state of limit switches when interacting with a Talon SRX
 */
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
