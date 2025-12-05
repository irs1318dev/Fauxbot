package frc.robot.driver;

public enum OperationContext
{
    General,
    Forklift,
    GarageDoor,
    Awesome;

    public static OperationContext getDefault()
    {
        return General;
    }
}
