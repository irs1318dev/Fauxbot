package frc.robot.driver;

public enum OperationContext
{
    General,
    ForkliftMechanism,
    GarageDoorMechanism,
    ElevatorMechanism,
    ShooterMechanism,
    PrinterMechanism,
    PrinterMacro,
    Awesome;


    public static OperationContext getDefault()
    {
        return General;
    }
}
