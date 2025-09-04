package frc.robot.driver;

public enum OperationContext
{
    General,
    Awesome;

    public static OperationContext getDefault()
    {
        return General;
    }
}
