package frc.robot.driver;

public enum OperationContext
{
    General,
    ForkliftMechanism,
    Awesome;

    public static OperationContext getDefault()
    {
        return General;
    }
}
