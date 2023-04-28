package frc.lib.driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import frc.lib.driver.descriptions.*;
import frc.robot.driver.*;

public class ButtonMapVerifier
{
    public static void main(String[] args)
    {
        ButtonMapVerifier.Verify(new ButtonMap(), true, true);
    }

    public static void Verify(IButtonMap buttonMap)
    {
        ButtonMapVerifier.Verify(buttonMap, true, false);
    }

    public static void Verify(IButtonMap buttonMap, boolean failOnError, boolean printMapping)
    {
        if (failOnError)
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

                // skip unmapped buttons
                if (button.device == UserInputDevice.None)
                {
                    continue;
                }

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
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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
                            if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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

                // skip unmapped buttons
                if (button.device == UserInputDevice.None)
                {
                    continue;
                }

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
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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
                            if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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

                // skip unmapped buttons
                if (button.device == UserInputDevice.None)
                {
                    continue;
                }

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
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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
                            if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
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

                // skip unmapped buttons
                if (button.device == UserInputDevice.None)
                {
                    continue;
                }

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

            if (printMapping)
            {
                List<ButtonCombination> buttonCombinationKeys = new ArrayList<ButtonCombination>(mapping.keySet());
                buttonCombinationKeys.sort(new ButtonCombinationComparator());
                for (ButtonCombination buttonCombination : buttonCombinationKeys)
                {
                    HashMap<Shift, List<OperationDescription>> shiftMap = mapping.get(buttonCombination);
                    List<Shift> shiftKeys = new ArrayList<Shift>(shiftMap.keySet());
                    shiftKeys.sort(new ShiftComparator());
                    for (Shift shift : shiftKeys)
                    {
                        String shiftString = "None";
                        if (shift == Shift.DriverDebug)
                        {
                            shiftString = "DriverDebug";
                        }
                        else if (shift == Shift.CodriverDebug)
                        {
                            shiftString = "CodriverDebug";
                        }
                        else if (shift == Shift.Test1Debug)
                        {
                            shiftString = "Test1Debug";
                        }
                        else if (shift == Shift.Test2Debug)
                        {
                            shiftString = "Test2Debug";
                        }

                        List<OperationDescription> operationDescriptions = shiftMap.get(shift);
                        if (operationDescriptions == null)
                        {
                            continue;
                        }

                        for (OperationDescription operationDescription : operationDescriptions)
                        {
                            System.out.println(
                                String.format(
                                    "%s (Shift: %s)  -->  %s",
                                    buttonCombination.toString(),
                                    shiftString,
                                    operationDescription.getOperation().toString()));
                        }
                    }
                }
            }
        }
    }

    private static boolean isOverlappingRange(OperationDescription one, OperationDescription two)
    {
        return ButtonMapVerifier.isOverlappingRange(one.getUserInputDeviceRangeMin(), one.getUserInputDeviceRangeMax(), two.getUserInputDeviceRangeMin(), two.getUserInputDeviceRangeMax());
    }

    private static boolean isOverlappingRange(double oneMin, double oneMax, double twoMin, double twoMax)
    {
            // verify range overlap (either at one end, the other end, or all-encompasing):
            return (twoMin >= oneMin && twoMin <= oneMax) ||
                (twoMax >= oneMin && twoMax <= oneMax) ||
                (oneMin >= twoMin && oneMin <= twoMax) ||
                (oneMax >= twoMin && oneMax <= twoMax);
    }

    private static class ButtonCombinationComparator implements Comparator<ButtonCombination>
    {
        @Override
        public int compare(ButtonCombination o1, ButtonCombination o2)
        {
            if (o1 == o2)
            {
                return 0;
            }

            if (o1 == null)
            {
                return -1;
            }

            return o1.compareTo(o2);
        }
    }

    private static class ShiftComparator implements Comparator<Shift>
    {
        @Override
        public int compare(Shift o1, Shift o2)
        {
            if (o1 == o2)
            {
                return 0;
            }

            if (o1 == null)
            {
                return -1;
            }

            if (o2 == null)
            {
                return 1;
            }

            return o1.getValue() - o2.getValue();
        }
    }
}