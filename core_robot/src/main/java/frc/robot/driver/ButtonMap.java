package frc.robot.driver;

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
        new AnalogOperationDescription(
            AnalogOperation.SpinMotor,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_Y,
            true,
            0.1),
        
        new AnalogOperationDescription(
            AnalogOperation.X_AxisPositionTalon,
            UserInputDevice.Driver,
            AnalogAxis.BUTTONPAD_DPAD_X,
            true,
            TuningConstants.PRINTER_MOTOR_DEADZONE),

        new AnalogOperationDescription(
            AnalogOperation.Y_AxisPositionTalon,
            UserInputDevice.Driver,
            AnalogAxis.BUTTONPAD_DPAD_Y,
            true,
            TuningConstants.PRINTER_MOTOR_DEADZONE),
        
        new AnalogOperationDescription(
            AnalogOperation.HoodAnglePosition,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_Y,
            true,
            TuningConstants.ZERO),
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.SomeDigitalOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Toggle),*/

        new DigitalOperationDescription(
            DigitalOperation.ForkliftActuatorExtended,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.ForkliftActuatorRetracted,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.ButtonPressed,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_X_BUTTON,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFirstFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            ButtonType.Click),

        new DigitalOperationDescription(
            DigitalOperation.ElevatorSecondFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_STICK_BUTTON,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.ElevatorThirdFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_STICK_BUTTON,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFourthFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_STICK_BUTTON,
            ButtonType.Click),

        new DigitalOperationDescription(
            DigitalOperation.ElevatorFifthFloor,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),

        new DigitalOperationDescription(
            DigitalOperation.PenDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_1,
            ButtonType.Click),

        new DigitalOperationDescription(
            DigitalOperation.PenUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
            ButtonType.Click),

        new DigitalOperationDescription(
            DigitalOperation.SpinButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_3,
            ButtonType.Click),
        
        new DigitalOperationDescription(
            DigitalOperation.FireButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.BUTTON_PAD_BUTTON_4,
            ButtonType.Click),
        
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
