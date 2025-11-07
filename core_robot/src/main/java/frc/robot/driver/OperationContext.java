package frc.robot.driver;

public enum OperationContext
{
    General,
    Forklift,
    Awesome;
    

    public static OperationContext getDefault()
    {
        return General;
    }
}
