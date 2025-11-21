package frc.robot.driver;

import java.util.EnumSet;

import javax.inject.Singleton;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IButtonMap;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.buttons.ButtonType;
import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.descriptions.MacroOperationDescription;
import frc.lib.driver.descriptions.ShiftDescription;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.controltasks.SetOperationContextTask;

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
        /** Example Analog operation entry:
        new AnalogOperationDescription(
            AnalogOperation.ExampleOne,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.1),*/

        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveLeft,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            EnumSet.of(OperationContext.Forklift),
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.1),

        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveRight,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSY,
            EnumSet.of(OperationContext.Forklift),
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.1),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.ExampleA,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Toggle),*/

        new DigitalOperationDescription(
            DigitalOperation.ForkliftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Toggle),
        
        new DigitalOperationDescription(
            DigitalOperation.ForkliftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Toggle),
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        /** Example Macro operation entry:
        new MacroOperationDescription(
            MacroOperation.ExampleAlpha,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(),
            new IOperation[]
            {
                AnalogOperation.ExampleOne,
                DigitalOperation.ExampleAlphaExampleA,
            }),*/
        new MacroOperationDescription(
            MacroOperation.ForkliftContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.Forklift)),

        new MacroOperationDescription(
            MacroOperation.GeneralContext,
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
