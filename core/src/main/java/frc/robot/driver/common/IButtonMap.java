package frc.robot.driver.common;

import java.util.Map;

import frc.robot.driver.MacroOperation;
import frc.robot.driver.Operation;
import frc.robot.driver.Shift;
import frc.robot.driver.common.descriptions.MacroOperationDescription;
import frc.robot.driver.common.descriptions.OperationDescription;
import frc.robot.driver.common.descriptions.ShiftDescription;

public interface IButtonMap
{
    Map<Shift, ShiftDescription> getShiftMap();
    Map<Operation, OperationDescription> getOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
