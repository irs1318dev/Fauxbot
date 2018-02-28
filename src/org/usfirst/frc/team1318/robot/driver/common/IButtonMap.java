package org.usfirst.frc.team1318.robot.driver.common;

import java.util.Map;

import org.usfirst.frc.team1318.robot.driver.MacroOperation;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.Shift;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.ShiftDescription;

public interface IButtonMap
{
    Map<Shift, ShiftDescription> getShiftMap();
    Map<Operation, OperationDescription> getOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
