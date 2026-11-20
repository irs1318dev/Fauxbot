package frc.robot.driver;

public enum OperationContext
{
    General,
    Awesome,
    Forklift,
    GarageDoor;

    public static OperationContext getDefault()
    {
        return General;
    }
}
