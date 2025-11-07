package frc.robot.driver;

import java.util.EnumSet;

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
    static
    {
    }

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
        
        new AnalogOperationDescription(
            AnalogOperation.rightMotor,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            EnumSet.noneOf(Shift.class),
            EnumSet.noneOf(Shift.class),
            EnumSet.of(OperationContext.Forklift),
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            -0.1,
            0.1),

            new AnalogOperationDescription(
            AnalogOperation.leftMotor,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RSX,
            EnumSet.noneOf(Shift.class),
            EnumSet.noneOf(Shift.class),
            EnumSet.of(OperationContext.Forklift),
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            -0.1,
            0.1),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        new DigitalOperationDescription(
            DigitalOperation.ForkLiftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            EnumSet.of(OperationContext.Forklift),
            ButtonType.Click),

            new DigitalOperationDescription(
            DigitalOperation.ForkLiftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            EnumSet.of(OperationContext.Forklift),
            ButtonType.Click),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        new MacroOperationDescription(
            MacroOperation.SetForkliftContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.Forklift)),
        new MacroOperationDescription(
            MacroOperation.SetGeneralContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            EnumSet.of(OperationContext.Forklift),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.General)),
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
