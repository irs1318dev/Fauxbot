package frc.robot.driver.common;

import java.util.HashMap;

import frc.robot.*;
import frc.robot.driver.*;
import frc.robot.driver.common.descriptions.*;

public class ButtonMapVerifier
{
    private static final ButtonMapVerifier instance = new ButtonMapVerifier();

    public static void Verify(IButtonMap buttonMap)
    {
        ButtonMapVerifier.instance.VerifyInternal(buttonMap);
    }

    private void VerifyInternal(IButtonMap buttonMap)
    {
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // verify that there isn't overlap between buttons
            HashMap<ButtonCombination, HashMap<Shift, OperationDescription>> mapping = new HashMap<ButtonCombination, HashMap<Shift, OperationDescription>>();
            for (DigitalOperationDescription description : buttonMap.getDigitalOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(), 
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    description.getUserInputDeviceAxis());

                HashMap<Shift, OperationDescription> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, OperationDescription>();
                    mapping.put(button, shiftMap);
                }
                else
                {
                    shiftMap = mapping.get(button);
                }

                Shift requiredShifts = description.getRequiredShifts();
                if (requiredShifts == null)
                {
                    for (Shift shift : Shift.AllShifts)
                    {
                        if (shiftMap.containsKey(shift))
                        {
                            OperationDescription otherDescription = shiftMap.get(shift);
                            throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                        }

                        shiftMap.put(shift, description);
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        OperationDescription otherDescription = shiftMap.get(requiredShifts);
                        throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                    }

                    shiftMap.put(requiredShifts, description);
                }
            }

            for (AnalogOperationDescription description : buttonMap.getAnalogOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(), 
                    UserInputDeviceButton.ANALOG_AXIS_RANGE,
                    -1,
                    description.getUserInputDeviceAxis());

                HashMap<Shift, OperationDescription> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, OperationDescription>();
                    mapping.put(button, shiftMap);
                }
                else
                {
                    shiftMap = mapping.get(button);
                }

                Shift requiredShifts = description.getRequiredShifts();
                if (requiredShifts == null)
                {
                    for (Shift shift : Shift.AllShifts)
                    {
                        if (shiftMap.containsKey(shift))
                        {
                            OperationDescription otherDescription = shiftMap.get(shift);
                            throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                        }

                        shiftMap.put(shift, description);
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        OperationDescription otherDescription = shiftMap.get(requiredShifts);
                        throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                    }

                    shiftMap.put(requiredShifts, description);
                }
            }

            for (MacroOperationDescription description : buttonMap.getMacroOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(), 
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    description.getUserInputDeviceAxis());

                HashMap<Shift, OperationDescription> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, OperationDescription>();
                    mapping.put(button, shiftMap);
                }
                else
                {
                    shiftMap = mapping.get(button);
                }

                Shift requiredShifts = description.getRequiredShifts();
                if (requiredShifts == null)
                {
                    for (Shift shift : Shift.AllShifts)
                    {
                        if (shiftMap.containsKey(shift))
                        {
                            OperationDescription otherDescription = shiftMap.get(shift);
                            throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                        }

                        shiftMap.put(shift, description);
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        OperationDescription otherDescription = shiftMap.get(requiredShifts);
                        throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                    }

                    shiftMap.put(requiredShifts, description);
                }
            }

            for (ShiftDescription description : buttonMap.getShiftSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(), 
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    AnalogAxis.NONE);

                HashMap<Shift, OperationDescription> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, OperationDescription>();
                    mapping.put(button, shiftMap);
                    shiftMap.put(description.getShift(), null);
                }
                else
                {
                    shiftMap = mapping.get(button);
                    for (Shift key : shiftMap.keySet())
                    {
                        OperationDescription value = shiftMap.get(key);
                        if (value == null)
                        {
                            throw new RuntimeException("conflcit between shift " + description.getShift().toString() + " and " + key.toString());
                        }

                        throw new RuntimeException("conflict between shift " + description.getShift().toString() + " and operation " + value.getOperation().toString());
                    }
                }
            }
        }
    }

    private class ButtonCombination
    {
        public final UserInputDevice device;
        public final UserInputDeviceButton button;
        public final int pov;
        public final AnalogAxis axis;

        public ButtonCombination(UserInputDevice device, UserInputDeviceButton button, int pov, AnalogAxis axis)
        {
            this.device = device;
            this.button = button;
            this.pov = pov;
            this.axis = axis;
        }
    }
}