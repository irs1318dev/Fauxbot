package frc.lib.driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import frc.lib.driver.descriptions.*;
import frc.robot.driver.*;

public class ButtonMapVerifier
{
    public static void main(String[] args)
    {
        ButtonMapVerifier.verify(new ButtonMap(), true, true);
    }

    /**
     * Verify the button map according the the rules to ensure that there is no overlap
     * 
     * @param buttonMap to verify
     */
    public static void verify(IButtonMap buttonMap)
    {
        ButtonMapVerifier.verify(buttonMap, true, false);
    }

    /**
     * Verify the button map according the the rules to ensure that there is no overlap
     * 
     * @param buttonMap    to verify
     * @param failOnError  whether to throw an exception if there is a failed rule
     * @param printMapping whether to print the mapping of buttons to operations
     */
    public static void verify(IButtonMap buttonMap, boolean failOnError, boolean printMapping)
    {
        OperationContext[] operationContexts = OperationContext.values();

        EnumSet<DigitalOperation> digitalOperations = EnumSet.noneOf(DigitalOperation.class);
        EnumSet<AnalogOperation> analogOperations = EnumSet.noneOf(AnalogOperation.class);
        EnumSet<MacroOperation> macroOperations = EnumSet.noneOf(MacroOperation.class);
        if (failOnError)
        {
            // verify that there isn't overlap between buttons
            HashMap<ButtonCombination, List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>> mapping = new HashMap<ButtonCombination, List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>>();
            for (DigitalOperationDescription description : buttonMap.getDigitalOperationSchema())
            {
                DigitalOperation operation = description.getOperation();
                if (!digitalOperations.add(operation))
                {
                    throw new RuntimeException("Two descriptions used for digital operation " + operation.toString());
                }

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

                List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>> operationContextList;
                if (!mapping.containsKey(button))
                {
                    operationContextList = new ArrayList<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>(operationContexts.length);
                    for (int i = 0; i < operationContexts.length; i++)
                    {
                        operationContextList.add(new HashMap<EnumSet<Shift>, List<OperationDescription<?>>>());
                    }

                    mapping.put(button, operationContextList);
                }
                else
                {
                    operationContextList = mapping.get(button);
                }

                for (OperationContext relevantContext : ensureNotNull(description.getRelevantContexts()))
                {
                    HashMap<EnumSet<Shift>, List<OperationDescription<?>>> shiftMap = operationContextList.get(relevantContext.ordinal());

                    EnumSet<Shift> requiredShifts = description.getRequiredShifts();
                    if (requiredShifts == null)
                    {
                        for (Shift shift : Shift.values())
                        {
                            EnumSet<Shift> singleShift = EnumSet.of(shift);
                            if (shiftMap.containsKey(singleShift))
                            {
                                List<OperationDescription<?>> otherDescriptions = shiftMap.get(singleShift);
                                for (OperationDescription<?> otherDescription : otherDescriptions)
                                {
                                    if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                    {
                                        throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                    }
                                }

                                otherDescriptions.add(description);
                            }
                            else
                            {
                                List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                                otherDescriptions.add(description);
                                shiftMap.put(singleShift, otherDescriptions);
                            }
                        }
                    }
                    else
                    {
                        if (shiftMap.containsKey(requiredShifts))
                        {
                            List<OperationDescription<?>> otherDescriptions = shiftMap.get(requiredShifts);
                            for (OperationDescription<?> otherDescription : otherDescriptions)
                            {
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                            otherDescriptions.add(description);
                            shiftMap.put(requiredShifts, otherDescriptions);
                        }
                    }
                }
            }

            for (AnalogOperationDescription description : buttonMap.getAnalogOperationSchema())
            {
                AnalogOperation operation = description.getOperation();
                if (!analogOperations.add(operation))
                {
                    throw new RuntimeException("Two descriptions used for analog operation " + operation.toString());
                }

                ButtonCombination button = new ButtonCombination(
                    description.getUserInputDevice(),
                    UserInputDeviceButton.ANALOG_AXIS_RANGE,
                    UserInputDevicePOV.NONE,
                    description.getUserInputDeviceAxis());

                // skip unmapped buttons
                if (button.device == UserInputDevice.None)
                {
                    continue;
                }

                List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>> operationContextList;
                if (!mapping.containsKey(button))
                {
                    operationContextList = new ArrayList<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>(operationContexts.length);
                    for (int i = 0; i < operationContexts.length; i++)
                    {
                        operationContextList.add(new HashMap<EnumSet<Shift>, List<OperationDescription<?>>>());
                    }

                    mapping.put(button, operationContextList);
                }
                else
                {
                    operationContextList = mapping.get(button);
                }

                for (OperationContext relevantContext : ensureNotNull(description.getRelevantContexts()))
                {
                    HashMap<EnumSet<Shift>, List<OperationDescription<?>>> shiftMap = operationContextList.get(relevantContext.ordinal());

                    EnumSet<Shift> requiredShifts = description.getRequiredShifts();
                    if (requiredShifts == null)
                    {
                        for (Shift shift : Shift.values())
                        {
                            EnumSet<Shift> singleShift = EnumSet.of(shift);
                            if (shiftMap.containsKey(singleShift))
                            {
                                List<OperationDescription<?>> otherDescriptions = shiftMap.get(singleShift);
                                for (OperationDescription<?> otherDescription : otherDescriptions)
                                {
                                    if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                    {
                                        throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                    }
                                }

                                otherDescriptions.add(description);
                            }
                            else
                            {
                                List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                                otherDescriptions.add(description);
                                shiftMap.put(singleShift, otherDescriptions);
                            }
                        }
                    }
                    else
                    {
                        if (shiftMap.containsKey(requiredShifts))
                        {
                            List<OperationDescription<?>> otherDescriptions = shiftMap.get(requiredShifts);
                            for (OperationDescription<?> otherDescription : otherDescriptions)
                            {
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                            otherDescriptions.add(description);
                            shiftMap.put(requiredShifts, otherDescriptions);
                        }
                    }
                }
            }

            for (MacroOperationDescription description : buttonMap.getMacroOperationSchema())
            {
                MacroOperation operation = description.getOperation();
                if (!macroOperations.add(operation))
                {
                    throw new RuntimeException("Two descriptions used for macro operation " + operation.toString());
                }

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

                List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>> operationContextList;
                if (!mapping.containsKey(button))
                {
                    operationContextList = new ArrayList<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>(operationContexts.length);
                    for (int i = 0; i < operationContexts.length; i++)
                    {
                        operationContextList.add(new HashMap<EnumSet<Shift>, List<OperationDescription<?>>>());
                    }

                    mapping.put(button, operationContextList);
                }
                else
                {
                    operationContextList = mapping.get(button);
                }

                for (OperationContext relevantContext : ensureNotNull(description.getRelevantContexts()))
                {
                    HashMap<EnumSet<Shift>, List<OperationDescription<?>>> shiftMap = operationContextList.get(relevantContext.ordinal());

                    EnumSet<Shift> requiredShifts = description.getRequiredShifts();
                    if (requiredShifts == null)
                    {
                        for (Shift shift : Shift.values())
                        {
                            EnumSet<Shift> singleShift = EnumSet.of(shift);
                            if (shiftMap.containsKey(singleShift))
                            {
                                List<OperationDescription<?>> otherDescriptions = shiftMap.get(singleShift);
                                for (OperationDescription<?> otherDescription : otherDescriptions)
                                {
                                    if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                    {
                                        throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                    }
                                }

                                otherDescriptions.add(description);
                            }
                            else
                            {
                                List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                                otherDescriptions.add(description);
                                shiftMap.put(singleShift, otherDescriptions);
                            }
                        }
                    }
                    else
                    {
                        if (shiftMap.containsKey(requiredShifts))
                        {
                            List<OperationDescription<?>> otherDescriptions = shiftMap.get(requiredShifts);
                            for (OperationDescription<?> otherDescription : otherDescriptions)
                            {
                                if (ButtonMapVerifier.isOverlappingRange(description, otherDescription))
                                {
                                    throw new RuntimeException("Disagreement between " + operation.toString() + " and " + otherDescription.getOperation().toString());
                                }
                            }

                            otherDescriptions.add(description);
                        }
                        else
                        {
                            List<OperationDescription<?>> otherDescriptions = new ArrayList<OperationDescription<?>>();
                            otherDescriptions.add(description);
                            shiftMap.put(requiredShifts, otherDescriptions);
                        }
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

                List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>> operationContextList;
                if (!mapping.containsKey(button))
                {
                    operationContextList = new ArrayList<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>>(operationContexts.length);
                    for (int i = 0; i < operationContexts.length; i++)
                    {
                        operationContextList.add(new HashMap<EnumSet<Shift>, List<OperationDescription<?>>>());
                    }

                    mapping.put(button, operationContextList);
                }
                else
                {
                    operationContextList = mapping.get(button);
                }

                for (OperationContext relevantContext : ensureNotNull(description.getRelevantContexts()))
                {
                    HashMap<EnumSet<Shift>, List<OperationDescription<?>>> shiftMap = operationContextList.get(relevantContext.ordinal());

                    if (shiftMap.isEmpty())
                    {
                        shiftMap.put(EnumSet.of(description.getShift()), null);
                    }
                    else
                    {
                        for (EnumSet<Shift> key : shiftMap.keySet())
                        {
                            List<OperationDescription<?>> value = shiftMap.get(key);
                            if (value == null || value.size() == 0)
                            {
                                throw new RuntimeException("conflcit between shift " + description.getShift().toString() + " and " + key.toString());
                            }

                            throw new RuntimeException("conflict between shift " + description.getShift().toString() + " and operation " + value.get(0).getOperation().toString());
                        }
                    }
                }
            }

            if (printMapping)
            {
                List<ButtonCombination> buttonCombinationKeys = new ArrayList<ButtonCombination>(mapping.keySet());
                buttonCombinationKeys.sort(new ButtonCombinationComparator());

                System.out.println("Button Mapping:");
                System.out.println("===================================");

                // print in order of operation context, then button combination, then shift
                for (int i = 0; i < operationContexts.length; i++)
                {
                    System.out.println(
                        String.format(
                            "Operation Context: %s",
                            operationContexts[i].toString()));
                    System.out.println("===================================");

                    for (ButtonCombination buttonCombination : buttonCombinationKeys)
                    {
                        List<HashMap<EnumSet<Shift>, List<OperationDescription<?>>>> operationContextList = mapping.get(buttonCombination);

                        HashMap<EnumSet<Shift>, List<OperationDescription<?>>> shiftMap = operationContextList.get(i);;
                        List<EnumSet<Shift>> shiftKeys = new ArrayList<EnumSet<Shift>>(shiftMap.keySet());
                        shiftKeys.sort(new ShiftComparator());
                        for (EnumSet<Shift> shift : shiftKeys)
                        {
                            String shiftString = "None";
                            if (shift.equals(EnumSet.noneOf(Shift.class)))
                            {
                                shiftString = "None";
                            }
                            else
                            {
                                shiftString = shift.toString();
                            }

                            List<OperationDescription<?>> operationDescriptions = shiftMap.get(shift);
                            if (operationDescriptions == null)
                            {
                                continue;
                            }

                            for (OperationDescription<?> operationDescription : operationDescriptions)
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

                    System.out.println("===================================");
                }
            }
        }
    }

    private static boolean isOverlappingRange(OperationDescription<?> one, OperationDescription<?> two)
    {
        return ButtonMapVerifier.isOverlappingRange(
            one.getUserInputDeviceRangeMin(),
            one.getUserInputDeviceRangeMax(),
            two.getUserInputDeviceRangeMin(),
            two.getUserInputDeviceRangeMax());
    }

    private static boolean isOverlappingRange(double oneMin, double oneMax, double twoMin, double twoMax)
    {
        // verify range overlap (either at one end, the other end, or all-encompasing):
        return (twoMin >= oneMin && twoMin <= oneMax) ||
            (twoMax >= oneMin && twoMax <= oneMax) ||
            (oneMin >= twoMin && oneMin <= twoMax) ||
            (oneMax >= twoMin && oneMax <= twoMax);
    }

    private static EnumSet<OperationContext> ensureNotNull(EnumSet<OperationContext> relevantContexts)
    {
        if (relevantContexts == null)
        {
            return EnumSet.allOf(OperationContext.class);
        }

        return relevantContexts;
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

    private static class ShiftComparator implements Comparator<EnumSet<Shift>>
    {
        @Override
        public int compare(EnumSet<Shift> o1, EnumSet<Shift> o2)
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

            return o1.toString().compareTo(o2.toString());
        }
    }
}