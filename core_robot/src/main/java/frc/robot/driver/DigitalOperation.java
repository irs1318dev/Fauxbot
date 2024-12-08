package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation
{
    // GarageDoor operations:
    ButtonPressed,

    // Forklift operations: 
    ForkliftActuatorExtended,
    ForkliftActuatorRetracted,


    // Elevator operations:
    ElevatorFirstFloor,
    ElevatorSecondFloor,
    ElevatorThirdFloor,
    ElevatorFourthFloor,
    ElevatorFifthFloor,
    // Printer operations:
    PenDown,
    PenUp,

    // Shooter operations:
    SpinButton,
    FireButton,
}
