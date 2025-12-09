package frc.robot.driver;

public enum OperationContext
{
    GarageDoorMechanism,
    ForkliftMechanism,
    General,
    Awesome, 
    ElevatorMechanism;

    public static OperationContext getDefault()
    {
        return General;
    }
}
