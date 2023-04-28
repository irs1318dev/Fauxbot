package frc.robot.driver;

import javax.inject.Singleton;

import frc.lib.driver.*;
import frc.lib.driver.buttons.*;
import frc.lib.driver.descriptions.*;
import frc.lib.helpers.Helpers;
import frc.robot.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
        new ShiftDescription(
            Shift.DriverDebug,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_SELECT_BUTTON),
        new ShiftDescription(
            Shift.CodriverDebug,
            UserInputDevice.Codriver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
        new ShiftDescription(
            Shift.Test1Debug,
            UserInputDevice.Test1,
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
        // new ShiftDescription(
        //     Shift.Test2Debug,
        //     UserInputDevice.Test2,
        //     UserInputDeviceButton.XBONE_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        /** Example Analog operation entry:
        new AnalogOperationDescription(
            Operation.SomeAnalogOperation,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),*/
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.SomeDigitalOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Toggle),*/
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        /** Example Macro operation entry:
        new MacroOperationDescription(
            MacroOperation.SomeMacroOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(),
            new IOperation[]
            {
                AnalogOperation.SomeAnalogOperation,
                DigitalOperation.SomeDigitalOperation,
            }),*/
    };

    @Override
    public ShiftDescription[] getShiftSchema()
    {
        return ButtonMap.ShiftButtonSchema;
    }

    @Override
    public AnalogOperationDescription[] getAnalogOperationSchema()
    {
        return ButtonMap.AnalogOperationSchema;
    }

    @Override
    public DigitalOperationDescription[] getDigitalOperationSchema()
    {
        return ButtonMap.DigitalOperationSchema;
    }

    @Override
    public MacroOperationDescription[] getMacroOperationSchema()
    {
        return ButtonMap.MacroSchema;
    }
}
