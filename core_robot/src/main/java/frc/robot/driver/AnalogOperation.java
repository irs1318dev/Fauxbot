package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum AnalogOperation implements IOperation
{
    // Forklift operations: 
    ForkliftDriveLeft,
    ForkliftDriveRight,

    // Printer operations:
    PrinterMoveX,
    PrinterMoveY,

    // Shooter operations:
    ShooterAngle,
}
