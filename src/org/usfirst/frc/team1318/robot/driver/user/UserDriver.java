package org.usfirst.frc.team1318.robot.driver.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.usfirst.frc.team1318.robot.common.SetHelper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IJoystick;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.IButtonMap;
import org.usfirst.frc.team1318.robot.driver.MacroOperation;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.driver.states.MacroOperationState;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

/**
 * Driver for teleop mode.  User driver translates current state and joystick state information into
 * the specific actions that should be taken by the robot.
 * 
 */
public class UserDriver extends Driver
{
    private final IJoystick joystickDriver;
    private final IJoystick joystickCoDriver;

    private final Map<MacroOperation, MacroOperationState> macroStateMap;

    /**
     * Initializes a new UserDriver
     * @param injector used to retrieve the components to utilize within the robot
     */
    @Inject
    public UserDriver(
        Injector injector,
        IButtonMap buttonMap,
        @Named("USER_DRIVER_JOYSTICK") IJoystick joystickDriver,
        @Named("USER_CODRIVER_JOYSTICK") IJoystick joystickCoDriver)
    {
        super(injector, buttonMap);

        this.joystickDriver = joystickDriver;
        this.joystickCoDriver = joystickCoDriver;

        this.macroStateMap = new HashMap<MacroOperation, MacroOperationState>();
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
    }

    /**
     * Tell the driver that some time has passed
     */
    @Override
    public void update()
    {
        // keep track of macros that were running before we checked user input...
        Set<MacroOperation> previouslyActiveMacroOperations = new HashSet<MacroOperation>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            MacroOperationState macroState = this.macroStateMap.get(macroOperation);
            if (macroState.getIsActive())
            {
                previouslyActiveMacroOperations.add(macroOperation);
            }
        }

        // check user inputs for various operations (non-macro) and keep track of:
        // operations that were interrupted already, and operations that were modified by user input in this update
        Set<Operation> modifiedOperations = new HashSet<Operation>();
        Set<Operation> interruptedOperations = new HashSet<Operation>();
        for (Operation operation : this.operationStateMap.keySet())
        {
            boolean receivedInput = this.operationStateMap.get(operation).checkInput(this.joystickDriver, this.joystickCoDriver);
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
        Map<Operation, Set<MacroOperation>> activeMacroOperationMap = new HashMap<Operation, Set<MacroOperation>>();
        for (MacroOperation macroOperation : this.macroStateMap.keySet())
        {
            MacroOperationState macroState = this.macroStateMap.get(macroOperation);
            macroState.checkInput(this.joystickDriver, this.joystickCoDriver);

            if (macroState.getIsActive())
            {
                activeMacroOperations.add(macroOperation);

                for (Operation affectedOperation : macroState.getAffectedOperations())
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
        for (Operation operation : activeMacroOperationMap.keySet())
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
                Set<MacroOperation> newRelevantMacroOperations = SetHelper.<MacroOperation> RelativeComplement(
                    previouslyActiveMacroOperations, relevantMacroOperations);
                if (newRelevantMacroOperations.size() > 1)
                {
                    // disobeys rule #3:
                    // (there are 2 or more active macros that weren't previously active)
                    macroOperationsToCancel.addAll(relevantMacroOperations);
                }
                else
                {
                    // some disobey rule #2 (remove only those that were previously active, and not the 1 that is newly active...)
                    macroOperationsToCancel.addAll(SetHelper.<MacroOperation> RelativeComplement(newRelevantMacroOperations,
                        relevantMacroOperations));
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
        Set<MacroOperation> inactiveMacroOperations = SetHelper.<MacroOperation> RelativeComplement(activeMacroOperations,
            this.macroStateMap.keySet());
        for (MacroOperation macroOperation : inactiveMacroOperations)
        {
            this.macroStateMap.get(macroOperation).run();
        }

        // second, run all of the active macros (which could add interrupts that were cleared in the previous phase)...
        for (MacroOperation macroOperation : activeMacroOperations)
        {
            this.macroStateMap.get(macroOperation).run();
        }
    }

    /**
     * Tell the driver that operation is stopping
     */
    @Override
    public void stop()
    {
    }
}
