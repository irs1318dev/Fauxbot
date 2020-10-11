package frc.robot.driver.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @SuppressWarnings(value="")
    private void VerifyInternal(IButtonMap buttonMap)
    {
        if (TuningConstants.THROW_EXCEPTIONS)
        {
            // verify that there isn't overlap between buttons
            HashMap<ButtonCombination, HashMap<Shift, List<OperationDescription>>> mapping = new HashMap<ButtonCombination, HashMap<Shift, List<OperationDescription>>>();
            for (DigitalOperationDescription description : buttonMap.getDigitalOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(),
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    description.getUserInputDeviceAxis());

                HashMap<Shift, List<OperationDescription>> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, List<OperationDescription>>();
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
                            List<OperationDescription> otherDescriptions = shiftMap.get(shift);
                            for (OperationDescription otherDescription : otherDescriptions)
                            {
                                if (this.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                            otherDescriptions.add(description);
                            shiftMap.put(shift, otherDescriptions);
                        }
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        List<OperationDescription> otherDescriptions = shiftMap.get(requiredShifts);
                        for (OperationDescription otherDescription : otherDescriptions)
                        {
                            if (this.isOverlappingRange(description, otherDescription))
                            {
                                throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                            }
                        }

                        otherDescriptions.add(description);
                    }
                    else
                    {
                        List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                        otherDescriptions.add(description);
                        shiftMap.put(requiredShifts, otherDescriptions);
                    }
                }
            }

            for (AnalogOperationDescription description : buttonMap.getAnalogOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(),
                    UserInputDeviceButton.ANALOG_AXIS_RANGE,
                    -1,
                    description.getUserInputDeviceAxis());

                HashMap<Shift, List<OperationDescription>> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, List<OperationDescription>>();
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
                            List<OperationDescription> otherDescriptions = shiftMap.get(shift);
                            for (OperationDescription otherDescription : otherDescriptions)
                            {
                                if (this.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                            otherDescriptions.add(description);
                            shiftMap.put(shift, otherDescriptions);
                        }
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        List<OperationDescription> otherDescriptions = shiftMap.get(requiredShifts);
                        for (OperationDescription otherDescription : otherDescriptions)
                        {
                            if (this.isOverlappingRange(description, otherDescription))
                            {
                                throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                            }
                        }

                        otherDescriptions.add(description);
                    }
                    else
                    {
                        List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                        otherDescriptions.add(description);
                        shiftMap.put(requiredShifts, otherDescriptions);
                    }
                }
            }

            for (MacroOperationDescription description : buttonMap.getMacroOperationSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(),
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    description.getUserInputDeviceAxis());

                HashMap<Shift, List<OperationDescription>> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, List<OperationDescription>>();
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
                            List<OperationDescription> otherDescriptions = shiftMap.get(shift);
                            for (OperationDescription otherDescription : otherDescriptions)
                            {
                                if (this.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                            otherDescriptions.add(description);
                            shiftMap.put(shift, otherDescriptions);
                        }
                    }
                }
                else
                {
                    if (shiftMap.containsKey(requiredShifts))
                    {
                        List<OperationDescription> otherDescriptions = shiftMap.get(requiredShifts);
                        for (OperationDescription otherDescription : otherDescriptions)
                        {
                            if (this.isOverlappingRange(description, otherDescription))
                            {
                                throw new RuntimeException("Disagreement between " + description.getOperation().toString() + " and " + otherDescription.getOperation().toString());
                            }
                        }

                        otherDescriptions.add(description);
                    }
                    else
                    {
                        List<OperationDescription> otherDescriptions = new ArrayList<OperationDescription>();
                        otherDescriptions.add(description);
                        shiftMap.put(requiredShifts, otherDescriptions);
                    }
                }
            }

            for (ShiftDescription description : buttonMap.getShiftSchema())
            {
                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(),
                    description.getUserInputDeviceButton(),
                    description.getUserInputDevicePovValue(),
                    AnalogAxis.NONE);

                HashMap<Shift, List<OperationDescription>> shiftMap;
                if (!mapping.containsKey(button))
                {
                    shiftMap = new HashMap<Shift, List<OperationDescription>>();
                    mapping.put(button, shiftMap);
                    shiftMap.put(description.getShift(), null);
                }
                else
                {
                    shiftMap = mapping.get(button);
                    for (Shift key : shiftMap.keySet())
                    {
                        List<OperationDescription> value = shiftMap.get(key);
                        if (value == null || value.size() == 0)
                        {
                            throw new RuntimeException("conflcit between shift " + description.getShift().toString() + " and " + key.toString());
                        }

                        throw new RuntimeException("conflict between shift " + description.getShift().toString() + " and operation " + value.get(0).getOperation().toString());
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

        @Override
        public int hashCode()
        {
            return this.device.hashCode() ^ this.button.hashCode() ^ this.pov ^ this.axis.hashCode();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof ButtonCombination))
            {
                return false;
            }

            ButtonCombination other = (ButtonCombination)obj;
            return (this.device == other.device &&
                this.button == other.button &&
                this.pov == other.pov &&
                this.axis == other.axis);
        }
    }

    private boolean isOverlappingRange(OperationDescription one, OperationDescription two)
    {
        return this.isOverlappingRange(one.getUserInputDeviceRangeMin(), one.getUserInputDeviceRangeMax(), two.getUserInputDeviceRangeMin(), two.getUserInputDeviceRangeMax());
    }

    private boolean isOverlappingRange(double oneMin, double oneMax, double twoMin, double twoMax)
    {
            // verify range overlap (either at one end, the other end, or all-encompasing):
            return (twoMin >= oneMin && twoMin <= oneMax) ||
                (twoMax >= oneMin && twoMax <= oneMax) ||
                (oneMin >= twoMin && oneMin <= twoMax) ||
                (oneMax >= twoMin && oneMax <= twoMax);
    }
}