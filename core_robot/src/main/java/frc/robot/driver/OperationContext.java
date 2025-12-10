package frc.robot.driver;

import frc.robot.mechanisms.ShooterMechanism;

public enum OperationContext
{
    GarageDoorMechanism,
    ForkliftMechanism,
    General,
    Awesome, 
    ElevatorMechanism, 
    PrinterMechanism,
    ShooterMechanism;

    public static OperationContext getDefault()
    {
        return General;
    }
}
