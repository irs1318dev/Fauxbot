package frc.robot.driver;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import frc.robot.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.common.*;
import frc.robot.driver.common.buttons.*;
import frc.robot.driver.common.descriptions.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    @SuppressWarnings("serial")
    private static Map<Shift, ShiftDescription> ShiftButtons = new HashMap<Shift, ShiftDescription>()
    {
        {
            /** Example Shift entry:
            put(
                Shift.Debug,
                new ShiftDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON));*/
        }
    };

    @SuppressWarnings("serial")
    public static Map<AnalogOperation, AnalogOperationDescription> AnalogOperationSchema = new HashMap<AnalogOperation, AnalogOperationDescription>()
    {
        {
            /** Example Analog operation entry:
            put(
                Operation.SomeAnalogOperation,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DRIVETRAIN_X_DEAD_ZONE));*/
        }
    };

    @SuppressWarnings("serial")
    public static Map<DigitalOperation, DigitalOperationDescription> DigitalOperationSchema = new HashMap<DigitalOperation, DigitalOperationDescription>()
    {
        {
            /** Example Digital operation entry:
            put(
                DigitalOperation.SomeDigitalOperation,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Toggle));*/
        }
    };

    @SuppressWarnings("serial")
    public static Map<MacroOperation, MacroOperationDescription> MacroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {
            /** Example Macro operation entry:
            put(
                MacroOperation.SomeMacroOperation,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    ButtonType.Simple,
                    () -> new SequentialTask(),
                    new Operation[]
                    {
                        Operation.SomeAnalogOperation,
                        Operation.SomeDigitalOperation,
                    }));*/
        }
    };

    @Override
    public Map<Shift, ShiftDescription> getShiftMap()
    {
        return ButtonMap.ShiftButtons;
    }

    @Override
    public Map<AnalogOperation, AnalogOperationDescription> getAnalogOperationSchema()
    {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public Map<DigitalOperation, DigitalOperationDescription> getDigitalOperationSchema()
    {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
