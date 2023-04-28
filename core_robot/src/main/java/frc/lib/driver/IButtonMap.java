package frc.lib.driver;

import frc.lib.driver.descriptions.*;

public interface IButtonMap
{
    ShiftDescription[] getShiftSchema();
    AnalogOperationDescription[] getAnalogOperationSchema();
    DigitalOperationDescription[] getDigitalOperationSchema();
    MacroOperationDescription[] getMacroOperationSchema();
}
