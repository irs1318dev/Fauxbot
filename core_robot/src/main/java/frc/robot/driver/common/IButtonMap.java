package frc.robot.driver.common;

import java.util.Map;

import frc.robot.driver.*;
import frc.robot.driver.common.descriptions.*;

public interface IButtonMap
{
    Map<Shift, ShiftDescription> getShiftMap();
    Map<AnalogOperation, AnalogOperationDescription> getAnalogOperationSchema();
    Map<DigitalOperation, DigitalOperationDescription> getDigitalOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
