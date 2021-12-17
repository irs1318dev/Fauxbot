package frc.robot.driver;

import javax.inject.Singleton;

import frc.robot.*;
import frc.robot.common.Helpers;
import frc.robot.driver.common.*;
import frc.robot.driver.common.buttons.*;
import frc.robot.driver.common.descriptions.*;
import frc.robot.driver.controltasks.*;

@Singleton
public class ButtonMap implements IButtonMap
{
    private static ShiftDescription[] ShiftButtonSchema = new ShiftDescription[]
    {
        new ShiftDescription(
            Shift.DriverDebug,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_LEFT_BUTTON),
    };

    public static AnalogOperationDescription[] AnalogOperationSchema = new AnalogOperationDescription[]
    {
        /** Example Analog operation entry:
        new AnalogOperationDescription(
            Operation.SomeAnalogOperation,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            TuningConstants.DRIVETRAIN_X_DEAD_ZONE),*/

        /*
        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveLeft,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            TuningConstants.FORKLIFT_DRIVE_DEAD_ZONE),
        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveRight,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_Y,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            TuningConstants.FORKLIFT_DRIVE_DEAD_ZONE),
        */
        /*
        new AnalogOperationDescription(
            AnalogOperation.PrinterMoveX,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            0.0),
        new AnalogOperationDescription(
            AnalogOperation.PrinterMoveY,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_Y,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.0),
        */
        /**/
        new AnalogOperationDescription(
            AnalogOperation.ShooterAngle,
            UserInputDevice.Driver,
            AnalogAxis.JOYSTICK_X,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            TuningConstants.SHOOTER_ANGLE_DEAD_ZONE),
        /**/
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.SomeDigitalOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Toggle),*/

        /*
        new DigitalOperationDescription(
            DigitalOperation.ElevatorOneButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorTwoButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorThreeButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFourButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFiveButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
            ButtonType.Click),
        */
        /*
        new DigitalOperationDescription(
            DigitalOperation.ForkliftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ForkliftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            ButtonType.Click),
        */
        /*
        new DigitalOperationDescription(
            DigitalOperation.GarageDoorButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
            ButtonType.Click),
        */
        /*
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            ButtonType.Click),
        */
        /**/
        new DigitalOperationDescription(
            DigitalOperation.ShooterSpin,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
            ButtonType.Toggle),
        new DigitalOperationDescription(
            DigitalOperation.ShooterFire,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
            ButtonType.Click),
        /**/
    };

    public static MacroOperationDescription[] MacroSchema = new MacroOperationDescription[]
    {
        /** Example Macro operation entry:
        new MacroOperationDescription(
            MacroOperation.SomeMacroOperation,
            UserInputDevice.Driver,
            UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
            ButtonType.Simple,
            () -> new SequentialTask(),
            new Operation[]
            {
                Operation.SomeAnalogOperation,
                Operation.SomeDigitalOperation,
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
