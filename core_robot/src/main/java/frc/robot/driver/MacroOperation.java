package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    ExampleAlpha,
    ExampleBeta,

    EnableForkliftContext,
    EnableGarageDoorContext,
    EnableElevatorContext,

    EnableGeneralContextFL,
    EnableGeneralContextGD,
    EnableGeneralContextEL,
}
