package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation
{
    // GarageDoor operations:
    GarageDoorButton,

    // Forklift operations: 
    ForkliftUp,
    ForkliftDown,

    // Elevator operations:
    ElevatorOneButton,
    ElevatorTwoButton,
    ElevatorThreeButton,
    ElevatorFourButton,
    ElevatorFiveButton,

    // Printer operations:
    PrinterPenDown,
    PrinterPenUp,

    // Shooter operations:
    ShooterSpin,
    ShooterFire,
}
