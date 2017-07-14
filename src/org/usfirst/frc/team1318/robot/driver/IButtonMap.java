package org.usfirst.frc.team1318.robot.driver;

import java.util.Map;

import org.usfirst.frc.team1318.robot.driver.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationDescription;

public interface IButtonMap
{
    Map<Operation, OperationDescription> getOperationSchema();
    Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema();
}
