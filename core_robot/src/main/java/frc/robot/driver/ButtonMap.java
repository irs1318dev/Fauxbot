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
        new AnalogOperationDescription(AnalogOperation.DriveTrainLeft, UserInputDevice.Driver, AnalogAxis.XBONE_LSX, false, 0.1),
        new AnalogOperationDescription(AnalogOperation.DriveTrainRight, UserInputDevice.Driver, AnalogAxis.XBONE_RSX, false, 0.1),    
    };

    public static DigitalOperationDescription[] DigitalOperationSchema = new DigitalOperationDescription[]
    {
        /** Example Digital operation entry:
        new DigitalOperationDescription(
            DigitalOperation.ExampleA,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_A_BUTTON,
            ButtonType.Toggle),*/
        new DigitalOperationDescription(DigitalOperation.ForkliftDown, UserInputDevice.Driver, UserInputDeviceButton.XBONE_A_BUTTON, EnumSet.of(OperationContext.Forklift), ButtonType.Click), 
        new DigitalOperationDescription(DigitalOperation.ForkliftUp, UserInputDevice.Driver, UserInputDeviceButton.XBONE_B_BUTTON, EnumSet.of(OperationContext.Forklift), ButtonType.Click),   
        //new DigitalOperationDescription(DigitalOperation.GarageDoorButton, UserInputDevice.Driver, UserInputDeviceButton.XBONE_A_BUTTON, ButtonType.Click),
        //new DigitalOperationDescription(DigitalOperation.FirstFloor, UserInputDevice.Driver, UserInputDeviceButton.XBONE_B_BUTTON, ButtonType.Click),
        //new DigitalOperationDescription(DigitalOperation.SecondFloor, UserInputDevice.Driver, UserInputDeviceButton.XBONE_A_BUTTON, ButtonType.Click),
        //new DigitalOperationDescription(DigitalOperation.ThirdFloor, UserInputDevice.Driver, UserInputDeviceButton.XBONE_Y_BUTTON, ButtonType.Click),
        //new DigitalOperationDescription(DigitalOperation.FourthFloor, UserInputDevice.Driver, UserInputDeviceButton.XBONE_X_BUTTON, ButtonType.Click),
        //new DigitalOperationDescription(DigitalOperation.FifthFloor, UserInputDevice.Driver, UserInputDeviceButton.XBONE_LEFT_BUTTON, ButtonType.Click),
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
            MacroOperation.ExampleAlpha,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_RIGHT_BUTTON,
            EnumSet.of(OperationContext.General),
            ButtonType.Click,
            () -> new SetOperationContextTask(OperationContext.Forklift)),
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
