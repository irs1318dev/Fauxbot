package frc.robot.driver;

public enum OperationContext
{
    GarageDoorMechanism,
    General,
    Awesome;

    public static OperationContext getDefault()
    {
        return General;
    }
}
