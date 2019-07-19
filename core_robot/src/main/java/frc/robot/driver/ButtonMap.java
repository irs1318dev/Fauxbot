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

            /*
            put(
                AnalogOperation.ForkliftDriveLeft,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DEAD_ZONE));
            put(
                AnalogOperation.ForkliftDriveRight,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_Y,
                    ElectronicsConstants.INVERT_Y_AXIS,
                    TuningConstants.DEAD_ZONE));
            */
            /*
            put(
                AnalogOperation.PrinterMoveX,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    0.0));
            put(
                AnalogOperation.PrinterMoveY,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_Y,
                    ElectronicsConstants.INVERT_Y_AXIS,
                    0.0));
            */

            put(
                AnalogOperation.ShooterAngle,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.JOYSTICK_X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DEAD_ZONE));
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
            /*
            put(
                DigitalOperation.ElevatorOneButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.ElevatorTwoButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.ElevatorThreeButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.ElevatorFourButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.ElevatorFiveButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Click));
            */
            /*
            put(
                DigitalOperation.ForkliftUp,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.ForkliftDown,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            */
            /*
            put(
                DigitalOperation.GarageDoorButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Click));
            */
            /*
            put(
                DigitalOperation.PrinterPenDown,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                DigitalOperation.PrinterPenUp,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            */

            put(
                DigitalOperation.ShooterSpin,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Toggle));
            put(
                DigitalOperation.ShooterFire,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
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
