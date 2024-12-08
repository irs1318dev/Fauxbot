package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum AnalogOperation implements IOperation
{
    SpinMotor,

    // Forklift operations: 

    // Printer operations:
    X_AxisPositionTalon,
    Y_AxisPositionTalon,

    // Shooter operations:
    HoodAnglePosition,
}
