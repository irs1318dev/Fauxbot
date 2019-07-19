package frc.robot.driver.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.common.SetHelper;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.descriptions.*;
import frc.robot.driver.common.states.*;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Driver that represents something that operates the robot.  This is either autonomous or teleop/user driver.
 *
 */
public class Driver
{
    private static final String LogName = "driver";

    private final IDashboardLogger logger;

    protected final Injector injector;
    protected final Map<IOperation, OperationState> operationStateMap;
    
    private final IJoystick joystickDriver;
    private final IJoystick joystickCoDriver;

    private final Set<Shift> activeShifts;

    private final Map<Shift, ShiftDescription> shiftMap;
    private final Map<MacroOperation, IMacroOperationState> macroStateMap;

    private final AutonomousRoutineSelector routineSelector;
    private IControlTask autonomousTask;

    private boolean isAutonomous;

    /**
     * Initializes a new Driver
     * @param injector used to retrieve the components to utilize within the robot
     */
    @Inject
    public Driver(
        IDashboardLogger logger,
        Injector injector,
        IButtonMap buttonMap,
        IRobotProvider provider)
    {
        this.logger = logger;
        this.injector = injector;

        Map<AnalogOperation, AnalogOperationDescription> analogOperationSchema = buttonMap.getAnalogOperationSchema();
        Map<DigitalOperation, DigitalOperationDescription> digitalOperationSchema = buttonMap.getDigitalOperationSchema();

        DigitalOperation[] digitalOperations = DigitalOperation.values();
        AnalogOperation[] analogOperations = AnalogOperation.values();

        this.operationStateMap = new HashMap<IOperation, OperationState>(analogOperations.length + digitalOperations.length);
        for (DigitalOperation operation : digitalOperations)
        {
            this.operationStateMap.put(
                operation,
                new DigitalOperationState(digitalOperationSchema.getOrDefault(operation, DigitalOperationDescription.simple)));
        }

        for (AnalogOperation operation : analogOperations)
        {
            this.operationStateMap.put(
                operation,
                new AnalogOperationState(analogOperationSchema.getOrDefault(operation, AnalogOperationDescription.simple)));
        }

        this.routineSelector = injector.getInstance(AutonomousRoutineSelector.class);

        this.joystickDriver = provider.getJoystick(ElectronicsConstants.JOYSTICK_DRIVER_PORT);
        this.joystickCoDriver = provider.getJoystick(ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT);

        this.activeShifts = new HashSet<Shift>();
        this.shiftMap = buttonMap.getShiftMap();
        this.macroStateMap = new HashMap<MacroOperation, IMacroOperationState>();
        Map<MacroOperation, MacroOperationDescription> macroSchema = buttonMap.getMacroOperationSchema();
        for (MacroOperation macroOperation : macroSchema.keySet())
        {
            this.macroStateMap.put(
                macroOperation,
                new MacroOperationState(
                    macroSchema.get(macroOperation),
                    this.operationStateMap,
                    this.injector));
        }

        this.isAutonomous = false;

        // initialize the path manager and load all of the paths
        PathManager manager = injector.getInstance(PathManager.class);
        manager.loadPaths();
    }

    /**
     * Checks whether the driver is in autonomous mode
     */
    public boolean isAutonomous()
    {
        return this.isAutonomous;
    }

    /**
     * Tell the driver that some time has passed
     */
    public void update()
    {
        this.logger.logBoolean(Driver.LogName, "isAuto", this.isAutonomous);

        // keep track of macros that were running before we checked user input...
        Set<MacroOperation> previouslyActiveMacroOperations = new HashSet<MacroOperation>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            IMacroOperationState macroState = this.macroStateMap.get(macroOperation);
            if (macroState.getIsActive())
            {
                previouslyActiveMacroOperations.add(macroOperation);
            }
        }

        // check inputs and update shifts based on it...
        this.activeShifts.clear();
        for (Shift shift : this.shiftMap.keySet())
        {
            ShiftDescription shiftDescription = this.shiftMap.get(shift);
            if (shiftDescription.checkInput(this.joystickDriver, this.joystickCoDriver))
            {
                this.activeShifts.add(shift);
            }
        }

        // if no other shifts are active, we will use the "None" shift
        if (this.activeShifts.size() == 0)
        {
            this.activeShifts.add(Shift.None);
        }

        // no matter what, we will set the "Any" shift
        this.activeShifts.add(Shift.Any);

        // check user inputs for various operations (non-macro) and keep track of:
        // operations that were interrupted already, and operations that were modified by user input in this update
        Set<IOperation> modifiedOperations = new HashSet<IOperation>();
        Set<IOperation> interruptedOperations = new HashSet<IOperation>();
        for (IOperation operation : this.operationStateMap.keySet())
        {
            OperationState opState = this.operationStateMap.get(operation);
            boolean receivedInput = opState.checkInput(this.joystickDriver, this.joystickCoDriver, this.activeShifts);
            if (receivedInput)
            {
                modifiedOperations.add(operation);
            }

            if (this.operationStateMap.get(operation).getIsInterrupted())
            {
                interruptedOperations.add(operation);
            }
        }

        // check user inputs for various macro operations
        // also keep track of modified and active macro operations, and how macro operations and operations link together
        Set<MacroOperation> activeMacroOperations = new HashSet<MacroOperation>();
        Map<IOperation, Set<MacroOperation>> activeMacroOperationMap = new HashMap<IOperation, Set<MacroOperation>>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            IMacroOperationState macroState = this.macroStateMap.get(macroOperation);
            macroState.checkInput(this.joystickDriver, this.joystickCoDriver, this.activeShifts);

            if (macroState.getIsActive())
            {
                activeMacroOperations.add(macroOperation);

                for (IOperation affectedOperation : macroState.getMacroCancelOperations())
                {
                    Set<MacroOperation> relevantMacroOperations = activeMacroOperationMap.get(affectedOperation);
                    if (relevantMacroOperations == null)
                    {
                        relevantMacroOperations = new HashSet<MacroOperation>();
                        activeMacroOperationMap.put(affectedOperation, relevantMacroOperations);
                    }

                    relevantMacroOperations.add(macroOperation);
                }
            }
        }

        // Determine the list of macro operations to cancel.  Only keep macros that:
        // 1. have not been usurped by a user action
        // 2. have not been usurped by a new macro (i.e. that was started in this round)
        // 3. are new macros that do not overlap with other new macros
        Set<MacroOperation> macroOperationsToCancel = new HashSet<MacroOperation>();
        for (IOperation operation : activeMacroOperationMap.keySet())
        {
            Set<MacroOperation> relevantMacroOperations = activeMacroOperationMap.get(operation);
            if (modifiedOperations.contains(operation))
            {
                // disobeys rule #1:
                // (macro usurped by user action)
                macroOperationsToCancel.addAll(relevantMacroOperations);
            }
            else if (relevantMacroOperations.size() > 1)
            {
                Set<MacroOperation> newRelevantMacroOperations = SetHelper.<MacroOperation>RelativeComplement(previouslyActiveMacroOperations, relevantMacroOperations);
                if (newRelevantMacroOperations.size() > 1)
                {
                    // disobeys rule #3:
                    // (there are 2 or more active macros that weren't previously active)
                    macroOperationsToCancel.addAll(relevantMacroOperations);
                }
                else
                {
                    // some disobey rule #2 (remove only those that were previously active, and not the 1 that is newly active...)
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
        Set<MacroOperation> inactiveMacroOperations = SetHelper.<MacroOperation>RelativeComplement(activeMacroOperations, this.macroStateMap.keySet());
        for (MacroOperation macroOperation : inactiveMacroOperations)
        {
            this.macroStateMap.get(macroOperation).run();
        }

        // second, run all of the active macros (which could add interrupts that were cleared in the previous phase)...
        // while we're doing that, grab the names of the macros for logging
        int i = 0;
        String[] macroStrings = new String[activeMacroOperations.size()];
        for (MacroOperation macroOperation : activeMacroOperations)
        {
            macroStrings[i++] = macroOperation.toString();
            this.macroStateMap.get(macroOperation).run();
        }

        this.logger.logString(Driver.LogName, "activeMacros", String.join(", ", macroStrings));
    }

    /**
     * Tell the driver that operation is stopping
     */
    public void stop()
    {
        this.isAutonomous = false;

        if (TuningConstants.CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE &&
            this.macroStateMap.containsKey(MacroOperation.AutonomousRoutine))
        {
            this.macroStateMap.remove(MacroOperation.AutonomousRoutine);
        }

        // cancel all interruption of buttons:
        for (OperationState state : this.operationStateMap.values())
        {
            state.setIsInterrupted(false);
        }

        // cancel all ongoing macros:
        for (IMacroOperationState macroOperationState : this.macroStateMap.values())
        {
            macroOperationState.cancel();
        }
    }

    public void startAutonomous()
    {
        this.isAutonomous = true;
        this.autonomousTask = this.routineSelector.selectRoutine();
        this.autonomousTask.initialize(this.operationStateMap, injector);
        if (!TuningConstants.CANCEL_AUTONOMOUS_ROUTINE_ON_DISABLE &&
            this.macroStateMap.containsKey(MacroOperation.AutonomousRoutine))
        {
            this.macroStateMap.remove(MacroOperation.AutonomousRoutine);
        }

        this.macroStateMap.put(
            MacroOperation.AutonomousRoutine,
            new AutonomousOperationState(this.autonomousTask, this.operationStateMap));
    }

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(DigitalOperation digitalOperation)
    {
        OperationState state = this.operationStateMap.get(digitalOperation);
        if (!(state instanceof DigitalOperationState))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("not a digital operation!");
            }

            return false;
        }

        DigitalOperationState digitalState = (DigitalOperationState)state;
        return digitalState.getState();
    }

    /**
     * Get a double between -1.0 and 1.0 indicating the current value of the analog operation
     * @param analogOperation to get
     * @return the current value of the analog operation
     */
    public double getAnalog(AnalogOperation analogOperation)
    {
        OperationState state = this.operationStateMap.get(analogOperation);
        if (!(state instanceof AnalogOperationState))
        {
            if (TuningConstants.THROW_EXCEPTIONS)
            {
                throw new RuntimeException("not an analog operation!");
            }

            return 0.0;
        }

        AnalogOperationState analogState = (AnalogOperationState)state;
        return analogState.getState();
    }
}
