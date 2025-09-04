package frc.robot.driver;

public enum OperationContext
{
    General,
    Forklift,
    GarageDoor,
    Elevator,
    Shooter,
    Printer;

    public static OperationContext getDefault()
    {
        return General;
    }
}
