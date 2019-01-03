package frc.robot.common.robotprovider;

public class TalonSRXLimitSwitchStatus
{
    public final boolean isForwardClosed;
    public final boolean isReverseClosed;

    public TalonSRXLimitSwitchStatus(boolean isForwardClosed, boolean isReverseClosed)
    {
        this.isForwardClosed = isForwardClosed;
        this.isReverseClosed = isReverseClosed;
    }
}
