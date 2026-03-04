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
    EnableShooterContext,
    EnablePrinterContext,
    EnablePrinterMacroContext,

    EnableGeneralContextFL,
    EnableGeneralContextGD,
    EnableGeneralContextEL,
    EnableGeneralContextSH,
    EnableGeneralContextPR,
    EnableGeneralContextPM,
}
