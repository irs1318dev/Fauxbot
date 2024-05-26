package frc.lib.driver;

// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.verify;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import com.google.inject.Injector;

// import frc.lib.driver.buttons.ButtonType;
// import frc.lib.driver.descriptions.*;
// import frc.lib.mechanisms.LoggingManager;
// import frc.lib.robotprovider.IJoystick;
// import frc.lib.robotprovider.ILogger;
// import frc.lib.robotprovider.IRobotProvider;
// import frc.robot.LoggingKey;
// import frc.robot.driver.*;
// import frc.robot.driver.controltasks.ControlTaskBase;

public class DriverTests
{
    // @Test
    // public void testDriver1()
    // {
    //     ILogger logger = mock(ILogger.class);
    //     LoggingManager loggingManager = new LoggingManager(logger);

    //     Injector injector = mock(Injector.class);
    //     when(injector.getInstance(AutonomousRoutineSelector.class)).thenReturn(null);
    //     when(injector.getInstance(TrajectoryManager.class)).thenReturn(null);

    //     IButtonMap buttonMap = mock(IButtonMap.class);
    //     when(buttonMap.getAnalogOperationSchema()).thenReturn(
    //         new AnalogOperationDescription[]
    //         {
    //             new AnalogOperationDescription(
    //                 AnalogOperation.ExampleOne,
    //                 UserInputDevice.Driver,
    //                 AnalogAxis.XBONE_LSY,
    //                 true,
    //                 0.1),
    //             new AnalogOperationDescription(
    //                 AnalogOperation.ExampleTwo,
    //                 UserInputDevice.Driver,
    //                 AnalogAxis.XBONE_LSX,
    //                 false,
    //                 0.1),
    //         });

    //     when(buttonMap.getDigitalOperationSchema()).thenReturn(
    //         new DigitalOperationDescription[]
    //         {
    //             new DigitalOperationDescription(
    //                 DigitalOperation.ExampleA,
    //                 UserInputDevice.Driver,
    //                 UserInputDeviceButton.XBONE_A_BUTTON,
    //                 ButtonType.Simple),
    //             new DigitalOperationDescription(
    //                 DigitalOperation.ExampleB,
    //                 UserInputDevice.Driver,
    //                 UserInputDeviceButton.XBONE_Y_BUTTON,
    //                 ButtonType.Click),
    //         }
    //     );

    //     TestControlTask firstTask = new TestControlTask();
    //     TestControlTask secondTask = new TestControlTask();
    //     when(buttonMap.getMacroOperationSchema()).thenReturn(
    //         new MacroOperationDescription[]
    //         {
    //             new MacroOperationDescription(
    //                 MacroOperation.ExampleAlpha,
    //                 UserInputDevice.Driver,
    //                 UserInputDeviceButton.XBONE_B_BUTTON,
    //                 ButtonType.Toggle,
    //                 () -> firstTask,
    //                 new IOperation[]
    //                 {
    //                     AnalogOperation.ExampleOne,
    //                     AnalogOperation.ExampleTwo,
    //                     DigitalOperation.ExampleA,
    //                     DigitalOperation.ExampleB,
    //                 }),
    //             new MacroOperationDescription(
    //                 MacroOperation.ExampleBeta,
    //                 UserInputDevice.Driver,
    //                 UserInputDeviceButton.XBONE_X_BUTTON,
    //                 ButtonType.Toggle,
    //                 () -> secondTask,
    //                 new IOperation[]
    //                 {
    //                     AnalogOperation.ExampleOne,
    //                     AnalogOperation.ExampleTwo,
    //                     DigitalOperation.ExampleA,
    //                     DigitalOperation.ExampleB,
    //                 }),
    //         }
    //     );

    //     when(buttonMap.getShiftSchema()).thenReturn(
    //         new ShiftDescription[]
    //         {
    //             new ShiftDescription(
    //                 Shift.DriverDebug,
    //                 UserInputDevice.Driver,
    //                 UserInputDeviceButton.XBONE_LEFT_BUTTON),
    //         });

    //     IRobotProvider robotProvider = mock(IRobotProvider.class);
    //     IJoystick driverJoystick = mock(IJoystick.class);
    //     when(driverJoystick.isConnected()).thenReturn(true);

    //     when(robotProvider.getJoystick(0)).thenReturn(driverJoystick);

    //     when(driverJoystick.getPOV()).thenReturn(-1);
    //     when(driverJoystick.getAxis(AnalogAxis.XBONE_LSY.Value)).thenReturn(0.0);
    //     when(driverJoystick.getAxis(AnalogAxis.XBONE_LSX.Value)).thenReturn(0.0);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_A_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_Y_BUTTON.Value)).thenReturn(false);

    //     Driver driver = new Driver(loggingManager, injector, buttonMap, robotProvider);
    //     driver.update();
    //     verify(logger).logString(LoggingKey.DriverActiveMacros, "");

    //     // test 2 analog operations at the same time (starts no macros)
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_A_BUTTON.Value)).thenReturn(true);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_Y_BUTTON.Value)).thenReturn(true);
    //     driver.update();
    //     verify(logger, Mockito.times(2)).logString(LoggingKey.DriverActiveMacros, "");

    //     // test 2 conflicting macros at the same time (starts nothing)
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_A_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_Y_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(true);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(true);
    //     driver.update();
    //     verify(logger, Mockito.times(3)).logString(LoggingKey.DriverActiveMacros, "");

    //     // test starting macros
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(true);
    //     driver.update();
    //     verify(logger, Mockito.times(1)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleAlpha.toString());

    //     // test starting other macro cancels original macro
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(true);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(false);
    //     driver.update();
    //     verify(logger, Mockito.times(1)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleBeta.toString());

    //     // test macro continues running
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(false);
    //     driver.update();
    //     verify(logger, Mockito.times(2)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleBeta.toString());

    //     // test macro continues running
    //     driver.update();
    //     verify(logger, Mockito.times(3)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleBeta.toString());

    //     // test cancelling macro with digital operation
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_A_BUTTON.Value)).thenReturn(true);
    //     driver.update();
    //     verify(logger, Mockito.times(4)).logString(LoggingKey.DriverActiveMacros, "");

    //     // test trying to start a macro while its analog operation is being controlled (starts nothing)
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_A_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(true);
    //     when(driverJoystick.getAxis(AnalogAxis.XBONE_LSX.Value)).thenReturn(0.5);
    //     driver.update();
    //     verify(logger, Mockito.times(5)).logString(LoggingKey.DriverActiveMacros, "");

    //     // clear analog operation state...
    //     when(driverJoystick.getAxis(AnalogAxis.XBONE_LSX.Value)).thenReturn(0.0);
    //     driver.update();
    //     verify(logger, Mockito.times(6)).logString(LoggingKey.DriverActiveMacros, "");

    //     // test starting macro
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_X_BUTTON.Value)).thenReturn(false);
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(true);
    //     driver.update();
    //     verify(logger, Mockito.times(2)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleAlpha.toString());

    //     // test macro continues running
    //     when(driverJoystick.getRawButton(UserInputDeviceButton.XBONE_B_BUTTON.Value)).thenReturn(false);
    //     driver.update();
    //     verify(logger, Mockito.times(3)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleAlpha.toString());

    //     // after cancelling the macro, it should appear one last time in the list of active macros...
    //     firstTask.hasCompleted = true;
    //     driver.update();
    //     verify(logger, Mockito.times(4)).logString(LoggingKey.DriverActiveMacros, MacroOperation.ExampleAlpha.toString());

    //     // now it should stop showing up...
    //     driver.update();
    //     verify(logger, Mockito.times(7)).logString(LoggingKey.DriverActiveMacros, "");
    // }

    // private static class TestControlTask extends ControlTaskBase
    // {
    //     public boolean hasCompleted;

    //     public TestControlTask()
    //     {
    //         this.hasCompleted = false;
    //     }

    //     @Override
    //     public void begin()
    //     {
    //         this.setAnalogOperationState(AnalogOperation.ExampleOne, 1.0);
    //     }

    //     @Override
    //     public void update()
    //     {
    //         this.setAnalogOperationState(AnalogOperation.ExampleOne, 1.0);
    //     }

    //     @Override
    //     public void end()
    //     {
    //         this.setAnalogOperationState(AnalogOperation.ExampleOne, 0.0);
    //     }

    //     @Override
    //     public boolean hasCompleted()
    //     {
    //         return this.hasCompleted;
    //     }
    // }
}
