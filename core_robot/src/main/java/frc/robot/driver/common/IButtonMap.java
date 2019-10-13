package frc.robot.driver.common;

import frc.robot.driver.common.descriptions.*;

public interface IButtonMap
{
    ShiftDescription[] getShiftSchema();
    AnalogOperationDescription[] getAnalogOperationSchema();
    DigitalOperationDescription[] getDigitalOperationSchema();
    MacroOperationDescription[] getMacroOperationSchema();
}
