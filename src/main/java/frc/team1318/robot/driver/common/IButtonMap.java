package frc.team1318.robot.driver.common;

import java.util.Map;

import frc.team1318.robot.driver.MacroOperation;
import frc.team1318.robot.driver.Operation;
import frc.team1318.robot.driver.Shift;
import frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import frc.team1318.robot.driver.common.descriptions.OperationDescription;
import frc.team1318.robot.driver.common.descriptions.ShiftDescription;

public interface IButtonMap
{
    Map<Shift, ShiftDescription> getShiftMap();
    Map<Operation, OperationDescription> getOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
