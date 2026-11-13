package frc.robot.driver;

public enum OperationContext
{
    General,
    Awesome,
    Forklift;

    public static OperationContext getDefault()
    {
        return General;
    }
}
