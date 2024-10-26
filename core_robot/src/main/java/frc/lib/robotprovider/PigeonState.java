package frc.lib.robotprovider;

/**
 * The possible states that a Pigeon IMU can have
 */
public enum PigeonState
{
    Unknown(-1),
    NoComm(0),
    Initializing(1),
    Ready(2),
    UserCalibration(3);

    public final int value;

    private PigeonState(int value)
    {
        this.value = value;
    }

    public static PigeonState getValue(int value)
    {
        switch (value)
        {
            case 0:
                return PigeonState.NoComm;

            case 1:
                return PigeonState.Initializing;

            case 2:
                return PigeonState.Ready;

            case 3:
                return PigeonState.UserCalibration;

            default:
            case -1:
                return PigeonState.Unknown;
        }
    }
}
