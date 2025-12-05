package frc.robot.driver;

public enum OperationContext
{
    General,
    ForkliftMechanism,
    GarageDoorMechanism,
    ElevatorMechanism,
    ShooterMechanism,
    Awesome;


    public static OperationContext getDefault()
    {
        return General;
    }
}
