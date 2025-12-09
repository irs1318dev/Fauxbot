package frc.robot.driver;

import java.util.EnumSet;

import javax.inject.Singleton;

import frc.lib.driver.*;
import frc.lib.driver.buttons.*;
import frc.lib.driver.descriptions.*;
import frc.lib.helpers.Helpers;
import frc.robot.*;
import frc.robot.driver.controltasks.*;
import frc.robot.mechanisms.PrinterMechanism;

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
            UserInputDeviceButton.XBONE_RIGHT_BUTTON),
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
        // new AnalogOperationDescription(
        //     AnalogOperation.RightMotorPower,
        //     UserInputDevice.Driver,
        //     AnalogAxis.XBONE_LSY,
        //     ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
        //     0.1
        //  ),
        //  new AnalogOperationDescription(
        //     AnalogOperation.LeftMotorPower,
        //     UserInputDevice.Driver,
        //     AnalogAxis.XBONE_LSX,
        //     ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
        //     0.1
        // ),
        new AnalogOperationDescription(
            AnalogOperation.XAxisPosition,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_TWIST,
            EnumSet.of(OperationContext.PrinterMechanism),
            ElectronicsConstants.INVERT_PS4_RIGHT_Y_AXIS,
            0.1
        ),
         new AnalogOperationDescription(
            AnalogOperation.YAxisPosition,
            UserInputDevice.Driver,
            AnalogAxis.PS4_LSY,
            EnumSet.of(OperationContext.PrinterMechanism),
            ElectronicsConstants.INVERT_PS4_LEFT_TRIGGER,
            0.1
         )
        
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
            DigitalOperation.Button,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            EnumSet.of(OperationContext.GarageDoorMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ForkliftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_STICK_BUTTON,
            EnumSet.of(OperationContext.ForkliftMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ForkliftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            EnumSet.of(OperationContext.ForkliftMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFloor1,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_10,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFloor2,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_11,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click
        ),      
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFloor3,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_12,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFloor4,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_13,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFloor5,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_14,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_15,
            EnumSet.of(OperationContext.PrinterMechanism),
            ButtonType.Click
        ),
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_1,
            EnumSet.of(OperationContext.PrinterMechanism),
            ButtonType.Click
        )
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
                DigitalOperation.ExampleA,
            }),*/
        new MacroOperationDescription(
            MacroOperation.EnableForkliftContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_1,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.ForkliftMechanism)
        ),
        new MacroOperationDescription(
            MacroOperation.EnableGeneralContextFD,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_14,
            EnumSet.of(OperationContext.ForkliftMechanism),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.General)
        ),
        new MacroOperationDescription(
            MacroOperation.EnableGarageDoorContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.GarageDoorMechanism)
        ),
        new MacroOperationDescription(
            MacroOperation.EnableGeneralContextGD,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON,
            EnumSet.of(OperationContext.GarageDoorMechanism),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.General)
        ),
        new MacroOperationDescription(
            MacroOperation.EnableElevatorContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_7,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.ElevatorMechanism)
            ),
        new MacroOperationDescription(
            MacroOperation.EnableGeneralContextEV,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_4,
            EnumSet.of(OperationContext.ElevatorMechanism),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.General)
        ),
        new MacroOperationDescription(
            MacroOperation.EnablePrinterContext,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.PrinterMechanism)
        ),
        new MacroOperationDescription(
            MacroOperation.EnableGeneralContextP,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_16,
            EnumSet.of(OperationContext.PrinterMechanism),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.General)
        )
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
    };
}
