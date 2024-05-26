package frc.lib.driver;

import java.util.EnumMap;
import java.util.EnumSet;

import javax.inject.Singleton;

import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.lib.driver.descriptions.*;
import frc.lib.driver.states.*;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.SetHelper;
import frc.lib.mechanisms.*;
import frc.lib.robotprovider.*;
import frc.robot.driver.*;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Driver that represents something that operates the robot.  This is either autonomous or teleop/user driver.
 *
 */
@Singleton
public class Driver implements IDriver
{
    private final ILogger logger;

    protected final Injector injector;
    protected final EnumMap<AnalogOperation, AnalogOperationState> analogOperationStateMap;
    protected final EnumMap<DigitalOperation, DigitalOperationState> digitalOperationStateMap;

    private final IJoystick[] joysticks;
    private final DigitalOperation[] allDigitalOperations;
    private final AnalogOperation[] allAnalogOperations;

    private final EnumMap<Shift, ShiftDescription> shiftMap;
    private final EnumMap<MacroOperation, IMacroOperationState> macroStateMap;

    private final AutonomousRoutineSelector routineSelector;
    private IControlTask autonomousTask;

    private RobotMode currentMode;

    /**
     * Initializes a new Driver
     * @param logger used to log data to the dashboard
     * @param injector used to retrieve the components to utilize within the robot
     * @param buttonMap to control the mapping of joysticks to the corresponding operations
     * @param provider to retrieve abstracted robot joysticks
     */
    @Inject
    public Driver(
        LoggingManager logger,
        Injector injector,
        IButtonMap buttonMap,
        IRobotProvider provider)
    {
        this.logger = logger;
        this.injector = injector;

        EnumSet<UserInputDevice> devices = EnumSet.noneOf(UserInputDevice.class);
        AnalogOperationDescription[] analogOperationSchema = buttonMap.getAnalogOperationSchema();
        DigitalOperationDescription[] digitalOperationSchema = buttonMap.getDigitalOperationSchema();

        this.allDigitalOperations = DigitalOperation.values();
        this.allAnalogOperations = AnalogOperation.values();

        this.digitalOperationStateMap = new EnumMap<DigitalOperation, DigitalOperationState>(DigitalOperation.class);
        for (DigitalOperationDescription description : digitalOperationSchema)
        {
            devices.add(description.getUserInputDevice());
            this.digitalOperationStateMap.put(description.getOperation(), new DigitalOperationState(description));
        }

        for (DigitalOperation operation : this.allDigitalOperations)
        {
            if (!this.digitalOperationStateMap.containsKey(operation))
            {
                this.digitalOperationStateMap.put(
                    operation,
                    new DigitalOperationState(new DigitalOperationDescription(operation)));
            }
        }

        this.analogOperationStateMap = new EnumMap<AnalogOperation, AnalogOperationState>(AnalogOperation.class);
        for (AnalogOperationDescription description : analogOperationSchema)
        {
            devices.add(description.getUserInputDevice());
            this.analogOperationStateMap.put(description.getOperation(), new AnalogOperationState(description));
        }

        for (AnalogOperation operation : this.allAnalogOperations)
        {
            if (!this.analogOperationStateMap.containsKey(operation))
            {
                this.analogOperationStateMap.put(
                    operation,
                    new AnalogOperationState(new AnalogOperationDescription(operation)));
            }
        }

        this.routineSelector = injector.getInstance(AutonomousRoutineSelector.class);

        ShiftDescription[] shiftSchema = buttonMap.getShiftSchema();
        this.shiftMap = new EnumMap<Shift, ShiftDescription>(Shift.class);
        for (ShiftDescription description : shiftSchema)
        {
            this.shiftMap.put(description.getShift(), description);
        }

        this.macroStateMap = new EnumMap<MacroOperation, IMacroOperationState>(MacroOperation.class);
        MacroOperationDescription[] macroSchema = buttonMap.getMacroOperationSchema();
        for (MacroOperationDescription description : macroSchema)
        {
            devices.add(description.getUserInputDevice());
            this.macroStateMap.put(
                (MacroOperation)description.getOperation(),
                new MacroOperationState(
                    description,
                    this.analogOperationStateMap,
                    this.digitalOperationStateMap,
                    this.injector));
        }

        ButtonMapVerifier.Verify(buttonMap);

        this.joysticks = new IJoystick[UserInputDevice.MaxCount.getId()];
        for (UserInputDevice device : UserInputDevice.values())
        {
            if (device != UserInputDevice.None &&
                devices.contains(device))
            {
                int id = device.getId();
                this.joysticks[id] = provider.getJoystick(id);
            }
        }

        this.currentMode = RobotMode.Disabled;

        // initialize the path manager and load all of the paths
        injector.getInstance(TrajectoryManager.class);
    }

    /**
     * Checks whether the driver is in autonomous mode
     */
    @Override
    public RobotMode getMode()
    {
        return this.currentMode;
    }

    /**
     * Tell the driver that some time has passed
     */
    @Override
    public void update()
    {
        this.logger.logString(LoggingKey.DriverMode, this.currentMode.toString());

        // keep track of macros that were running before we checked user input...
        EnumSet<MacroOperation> previouslyActiveMacroOperations = EnumSet.noneOf(MacroOperation.class);
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            IMacroOperationState macroState = this.macroStateMap.get(macroOperation);
            if (macroState.getIsActive())
            {
                previouslyActiveMacroOperations.add(macroOperation);
            }
        }

        // check inputs and update shifts based on it...
        EnumSet<Shift> activeShifts = EnumSet.noneOf(Shift.class);
        for (Shift shift : this.shiftMap.keySet())
        {
            ShiftDescription shiftDescription = this.shiftMap.get(shift);
            if (this.currentMode != RobotMode.Autonomous && shiftDescription.checkInput(this.joysticks))
            {
                activeShifts.add(shift);
            }
        }

        // check user inputs for various analog operations and keep track of:
        // operations that were interrupted already, and operations that were modified by user input in this update
        EnumSet<AnalogOperation> modifiedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        EnumSet<AnalogOperation> interruptedAnalogOperations = EnumSet.noneOf(AnalogOperation.class);
        for (AnalogOperation analogOperation : this.allAnalogOperations)
        {
            AnalogOperationState opState = this.analogOperationStateMap.get(analogOperation);
            boolean receivedInput = this.currentMode != RobotMode.Autonomous && opState.checkInput(this.joysticks, activeShifts);
            if (receivedInput)
            {
                modifiedAnalogOperations.add(analogOperation);
            }

            if (this.analogOperationStateMap.get(analogOperation).getIsInterrupted())
            {
                interruptedAnalogOperations.add(analogOperation);
            }
        }

        // check user inputs for various digital operations and keep track of:
        // operations that were interrupted already, and operations that were modified by user input in this update
        EnumSet<DigitalOperation> modifiedDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
        EnumSet<DigitalOperation> interruptedDigitalOperations = EnumSet.noneOf(DigitalOperation.class);
        for (DigitalOperation digitalOperation : this.allDigitalOperations)
        {
            DigitalOperationState opState = this.digitalOperationStateMap.get(digitalOperation);
            boolean receivedInput = this.currentMode != RobotMode.Autonomous && opState.checkInput(this.joysticks, activeShifts);
            if (receivedInput)
            {
                modifiedDigitalOperations.add(digitalOperation);
            }

            if (this.digitalOperationStateMap.get(digitalOperation).getIsInterrupted())
            {
                interruptedDigitalOperations.add(digitalOperation);
            }
        }

        // check user inputs for various macro operations
        // also keep track of modified and active macro operations, and how macro operations and operations link together
        EnumSet<MacroOperation> activeMacroOperations = EnumSet.noneOf(MacroOperation.class);
        EnumMap<AnalogOperation, EnumSet<MacroOperation>> activeMacroAnalogOperationMap = new EnumMap<AnalogOperation, EnumSet<MacroOperation>>(AnalogOperation.class);
        EnumMap<DigitalOperation, EnumSet<MacroOperation>> activeMacroDigitalOperationMap = new EnumMap<DigitalOperation, EnumSet<MacroOperation>>(DigitalOperation.class);
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            IMacroOperationState macroState = this.macroStateMap.get(macroOperation);
            if (this.currentMode != RobotMode.Autonomous)
            {
                macroState.checkInput(this.joysticks, activeShifts);
            }

            if (macroState.getIsActive())
            {
                activeMacroOperations.add(macroOperation);

                for (AnalogOperation affectedAnalogOperation : macroState.getMacroCancelAnalogOperations())
                {
                    EnumSet<MacroOperation> relevantMacroOperations = activeMacroAnalogOperationMap.get(affectedAnalogOperation);
                    if (relevantMacroOperations == null)
                    {
                        relevantMacroOperations = EnumSet.noneOf(MacroOperation.class);
                        activeMacroAnalogOperationMap.put(affectedAnalogOperation, relevantMacroOperations);
                    }

                    relevantMacroOperations.add(macroOperation);
                }

                for (DigitalOperation affectedDigitalOperation : macroState.getMacroCancelDigitalOperations())
                {
                    EnumSet<MacroOperation> relevantMacroOperations = activeMacroDigitalOperationMap.get(affectedDigitalOperation);
                    if (relevantMacroOperations == null)
                    {
                        relevantMacroOperations = EnumSet.noneOf(MacroOperation.class);
                        activeMacroDigitalOperationMap.put(affectedDigitalOperation, relevantMacroOperations);
                    }

                    relevantMacroOperations.add(macroOperation);
                }
            }
        }

        // Determine the list of macro operations to cancel.  Only keep macros that:
        // 1. have not been usurped by a user action
        // 2. have not been usurped by a new macro (i.e. that was started in this round)
        // 3. are new macros that do not overlap with other new macros
        EnumSet<MacroOperation> macroOperationsToCancel = EnumSet.noneOf(MacroOperation.class);

        // first perform checks for analog operations:
        for (AnalogOperation operation : activeMacroAnalogOperationMap.keySet())
        {
            EnumSet<MacroOperation> relevantMacroOperations = activeMacroAnalogOperationMap.get(operation);
            if (modifiedAnalogOperations.contains(operation))
            {
                // disobeys rule #1:
                // (macro usurped by user action)
                macroOperationsToCancel.addAll(relevantMacroOperations);
            }
            else if (SetHelper.<MacroOperation>Count(relevantMacroOperations) > 1)
            {
                EnumSet<MacroOperation> newRelevantMacroOperations = SetHelper.<MacroOperation>RelativeComplement(previouslyActiveMacroOperations, relevantMacroOperations);
                if (SetHelper.<MacroOperation>Count(newRelevantMacroOperations) > 1)
                {
                    // disobeys rule #3:
                    // (there are 2 or more active macros that weren't previously active)
                    macroOperationsToCancel.addAll(relevantMacroOperations);
                }
                else
                {
                    // some disobey rule #2 (remove only those that were previously active, and not the 1 that is newly active...)
                    ExceptionHelpers.Assert(!newRelevantMacroOperations.isEmpty(), "how did we end up with conflicting relevant macros for %s when there are no new ones (among %s)?", operation, relevantMacroOperations);
                    macroOperationsToCancel.addAll(SetHelper.<MacroOperation>RelativeComplement(newRelevantMacroOperations, relevantMacroOperations));
                }
            }
        }

        // and then for digital operations:
        for (DigitalOperation operation : activeMacroDigitalOperationMap.keySet())
        {
            EnumSet<MacroOperation> relevantMacroOperations = activeMacroDigitalOperationMap.get(operation);
            if (modifiedDigitalOperations.contains(operation))
            {
                // disobeys rule #1:
                // (macro usurped by user action)
                macroOperationsToCancel.addAll(relevantMacroOperations);
            }
            else if (SetHelper.<MacroOperation>Count(relevantMacroOperations) > 1)
            {
                EnumSet<MacroOperation> newRelevantMacroOperations = SetHelper.<MacroOperation>RelativeComplement(previouslyActiveMacroOperations, relevantMacroOperations);
                if (SetHelper.<MacroOperation>Count(newRelevantMacroOperations) > 1)
                {
                    // disobeys rule #3:
                    // (there are 2 or more active macros that weren't previously active)
                    macroOperationsToCancel.addAll(relevantMacroOperations);
                }
                else
                {
                    // some disobey rule #2 (remove only those that were previously active, and not the 1 that is newly active...)
                    ExceptionHelpers.Assert(!newRelevantMacroOperations.isEmpty(), "how did we end up with conflicting relevant macros for %s when there are no new ones (among %s)?", operation, relevantMacroOperations);
                    macroOperationsToCancel.addAll(SetHelper.<MacroOperation>RelativeComplement(newRelevantMacroOperations, relevantMacroOperations));
                }
            }
        }

        // cancel macros that didn't follow the rules list above
        for (MacroOperation macroOperationToCancel : macroOperationsToCancel)
        {
            this.macroStateMap.get(macroOperationToCancel).setIsInterrupted(true);
            activeMacroOperations.remove(macroOperationToCancel);
        }

        // first, run all of the inactive macros (to clear any old interrupts)...
        EnumSet<MacroOperation> inactiveMacroOperations = SetHelper.<MacroOperation>RelativeComplement(activeMacroOperations, this.macroStateMap.keySet());
        for (MacroOperation macroOperation : inactiveMacroOperations)
        {
            this.macroStateMap.get(macroOperation).run();
        }

        // second, run all of the active macros (which could add interrupts that were cleared in the previous phase)...
        // while we're doing that, grab the names of the macros for logging
        String macroString = "";
        int activeMacroCount = activeMacroOperations.size();
        if (activeMacroCount > 0)
        {
            int i = 0;
            String[] macroStrings = new String[activeMacroCount];
            for (MacroOperation macroOperation : activeMacroOperations)
            {
                macroStrings[i++] = macroOperation.toString();
                this.macroStateMap.get(macroOperation).run();
            }

            macroString = String.join(", ", macroStrings);
        }

        this.logger.logString(LoggingKey.DriverActiveMacros, macroString);
        this.logger.logString(LoggingKey.DriverActiveShifts, activeShifts.toString());
    }

    /**
     * Tell the driver that operation is stopping
     */
    @Override
    public void stop()
    {
        this.currentMode = RobotMode.Disabled;

        if (this.macroStateMap.containsKey(MacroOperation.AutonomousRoutine))
        {
            this.macroStateMap.remove(MacroOperation.AutonomousRoutine);
        }

        // cancel all interruption of buttons:
        for (AnalogOperationState state : this.analogOperationStateMap.values())
        {
            state.setIsInterrupted(false);
        }

        // cancel all interruption of buttons:
        for (DigitalOperationState state : this.digitalOperationStateMap.values())
        {
            state.setIsInterrupted(false);
        }

        // cancel all ongoing macros:
        for (IMacroOperationState macroOperationState : this.macroStateMap.values())
        {
            macroOperationState.cancel();
        }
    }

    /**
     * Starts a particular mode of the match
     * @param mode that is being started
     */
    @Override
    public void startMode(RobotMode mode)
    {
        this.currentMode = mode;

        this.autonomousTask = this.routineSelector.selectRoutine(mode);
        if (this.autonomousTask != null)
        {
            this.autonomousTask.initialize(this.analogOperationStateMap, this.digitalOperationStateMap, injector);
            this.macroStateMap.put(
                MacroOperation.AutonomousRoutine,
                new AutonomousOperationState(this.autonomousTask, this.analogOperationStateMap, this.digitalOperationStateMap));
        }
    }

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(DigitalOperation digitalOperation)
    {
        DigitalOperationState state = this.digitalOperationStateMap.get(digitalOperation);
        return state.getState();
    }

    /**
     * Get a double between -1.0 and 1.0 indicating the current value of the analog operation
     * @param analogOperation to get
     * @return the current value of the analog operation
     */
    public double getAnalog(AnalogOperation analogOperation)
    {
        AnalogOperationState state = this.analogOperationStateMap.get(analogOperation);
        return state.getState();
    }

    /**
     * Instructs the joystick to rumble (if supported)
     * @param device device to attempt to rumble
     * @param type whether left or right rumbler
     * @param value between 0.0 for no rumble and 1.0 for full rumble
     */
    public void setRumble(UserInputDevice device, JoystickRumbleType type, double value)
    {
        IJoystick joystick = this.joysticks[device.getId()];
        if (joystick == null || !joystick.isConnected())
        {
            ExceptionHelpers.Assert(TuningConstants.EXPECT_UNUSED_JOYSTICKS, "Unexpected user input device " + device.toString());
            return;
        }

        joystick.setRumble(type, value);
    }
}
