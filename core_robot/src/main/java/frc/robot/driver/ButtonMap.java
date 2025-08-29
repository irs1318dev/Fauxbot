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
        /** Example Analog operation entry:
        new AnalogOperationDescription(
            AnalogOperation.ExampleOne,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.1),*/

        /**/
        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveLeft,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSY,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            TuningConstants.FORKLIFT_DRIVE_DEAD_ZONE),
        new AnalogOperationDescription(
            AnalogOperation.ForkliftDriveRight,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_RSY,
            ElectronicsConstants.INVERT_XBONE_RIGHT_Y_AXIS,
            TuningConstants.FORKLIFT_DRIVE_DEAD_ZONE),
        /**/
        /*
        new AnalogOperationDescription(
            AnalogOperation.PrinterMoveX,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            0.0),
        new AnalogOperationDescription(
            AnalogOperation.PrinterMoveY,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSY,
            ElectronicsConstants.INVERT_XBONE_LEFT_Y_AXIS,
            0.0),
        /**/
        /*
        new AnalogOperationDescription(
            AnalogOperation.ShooterAngle,
            UserInputDevice.Driver,
            AnalogAxis.XBONE_LSX,
            ElectronicsConstants.INVERT_XBONE_LEFT_X_AXIS,
            TuningConstants.SHOOTER_ANGLE_DEAD_ZONE),
        /**/
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.ExampleA,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Toggle),*/

        /*
        new DigitalOperationDescription(
            DigitalOperation.ElevatorOneButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorTwoButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorThreeButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_X_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFourButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ElevatorFiveButton,
            UserInputDevice.Driver,
            0,
            ButtonType.Click),
        /**/
        /**/
        new DigitalOperationDescription(
            DigitalOperation.ForkliftUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.ForkliftDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click),
        /**/
        /*
        new DigitalOperationDescription(
            DigitalOperation.GarageDoorButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),
        /**/
        /*
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenDown,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Click),
        new DigitalOperationDescription(
            DigitalOperation.PrinterPenUp,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click),
        /**/
        /*
        new DigitalOperationDescription(
            DigitalOperation.ShooterSpin,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Toggle),
        new DigitalOperationDescription(
            DigitalOperation.ShooterFire,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click),
        /**/
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
            
        /*
        new MacroOperationDescription(
            MacroOperation.WriteHiTask,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_Y_BUTTON,
            ButtonType.Toggle,
            () -> SequentialTask.Sequence(
                new PenWriteTask(false),
                new PenMoveTask(10.0, 10.0),
                new PenWriteTask(true),
                new PenMoveTask(10.0, 90.0),
                new PenWriteTask(false),
                new PenMoveTask(10.0, 50.0),
                new PenWriteTask(true),
                new PenMoveTask(40.0, 50.0),
                new PenWriteTask(false),
                new PenMoveTask(40.0, 10.0),
                new PenWriteTask(true),
                new PenMoveTask(40.0, 90.0),
                new PenWriteTask(false),
                new PenMoveTask(80.0, 10.0),
                new PenWriteTask(true),
                new PenMoveTask(80.0, 90.0),
                new PenWriteTask(false)),
            new IOperation[]
            {
                AnalogOperation.PrinterMoveX,
                AnalogOperation.PrinterMoveY,
                DigitalOperation.PrinterPenDown,
                DigitalOperation.PrinterPenUp,
            }),
        /**/
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
