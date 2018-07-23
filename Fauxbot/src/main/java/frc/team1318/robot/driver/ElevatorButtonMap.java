package frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import frc.team1318.robot.driver.common.IButtonMap;
import frc.team1318.robot.driver.common.UserInputDeviceButton;
import frc.team1318.robot.driver.common.buttons.ButtonType;
import frc.team1318.robot.driver.common.descriptions.DigitalOperationDescription;
import frc.team1318.robot.driver.common.descriptions.MacroOperationDescription;
import frc.team1318.robot.driver.common.descriptions.OperationDescription;
import frc.team1318.robot.driver.common.descriptions.ShiftDescription;
import frc.team1318.robot.driver.common.descriptions.UserInputDevice;

@Singleton
public class ElevatorButtonMap implements IButtonMap
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
    public static Map<Operation, OperationDescription> OperationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            /** Example Analog operation entry:
            put(
                Operation.SomeAnalogOperation,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.X,
                    ElectronicsConstants.INVERT_X_AXIS,
                    TuningConstants.DRIVETRAIN_X_DEAD_ZONE));*/
            
            /** Example Digital operation entry:
            put(
                Operation.SomeDigitalOperation,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    ButtonType.Toggle));*/
            put(
                Operation.ElevatorOneButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.ElevatorTwoButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.ElevatorThreeButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.ElevatorFourButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.ElevatorFiveButton,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
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
        return ElevatorButtonMap.ShiftButtons;
    }

    @Override
    public Map<Operation, OperationDescription> getOperationSchema()
    {
        return ElevatorButtonMap.OperationSchema;
    }

    @Override
    public Map<MacroOperation, MacroOperationDescription> getMacroOperationSchema()
    {
        return ElevatorButtonMap.MacroSchema;
    }
}
