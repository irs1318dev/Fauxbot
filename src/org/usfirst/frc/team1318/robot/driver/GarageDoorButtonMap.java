package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.driver.common.IButtonMap;
import org.usfirst.frc.team1318.robot.driver.common.UserInputDeviceButton;
import org.usfirst.frc.team1318.robot.driver.common.buttons.ButtonType;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.ShiftDescription;
import org.usfirst.frc.team1318.robot.driver.common.descriptions.UserInputDevice;

@Singleton
public class GarageDoorButtonMap implements IButtonMap
{
    @SuppressWarnings("serial")
    private static Map<Shift, ShiftDescription> ShiftButtons = new HashMap<Shift, ShiftDescription>()
    {
        {
        }
    };

    @SuppressWarnings("serial")
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
        }
    };

    @SuppressWarnings("serial")
    public static Map<MacroOperation, MacroOperationDescription> MacroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {
        }
    };

    @Override
    public Map<Shift, ShiftDescription> getShiftMap()
    {
        return GarageDoorButtonMap.ShiftButtons;
    }

    @Override
    public Map<Operation, OperationDescription> getOperationSchema()
    {
        return GarageDoorButtonMap.OperationSchema;
    }

    @Override
    public Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema()
    {
        return GarageDoorButtonMap.MacroSchema;
    }
}
