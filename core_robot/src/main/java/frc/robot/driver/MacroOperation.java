package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum MacroOperation implements IOperation
{
    AutonomousRoutine,

    ExampleAlpha,
    ExampleBeta,

    WriteHiTask,

    UseGeneralMode,
    UseForkliftMode,
    UseGarageDoorMode,
    UseElevatorMode,
    UseShooterMode,
    UsePrinterMode
}
