package frc.robot.driver;

public enum OperationContext
{
    GarageDoorMechanism,
    ForkliftMechanism,
    General,
    Awesome;

    public static OperationContext getDefault()
    {
        return General;
    }
}
