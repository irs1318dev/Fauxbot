package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.driver.buttons.*;
import org.usfirst.frc.team1318.robot.driver.descriptions.*;

public class ButtonMap
{
    @SuppressWarnings("serial")
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            put(
                Operation.GarageDoorButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Click));
        }
    };

    @SuppressWarnings("serial")
    public static Map<MacroOperation, MacroOperationDescription> MacroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {
        }
    };
}
