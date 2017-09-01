package org.usfirst.frc.team1318.robot.driver.common;

import java.util.Map;

import org.usfirst.frc.team1318.robot.driver.MacroOperation;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.OperationDescription;

public interface IButtonMap
{
    Map<Operation, OperationDescription> getOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
