package frc.robot.driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.ClassPath;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IOperationModifier;
import frc.lib.driver.descriptions.AnalogOperationDescription;
import frc.lib.driver.descriptions.DigitalOperationDescription;
import frc.lib.driver.states.AnalogOperationState;
import frc.lib.driver.states.DigitalOperationState;
import frc.lib.helpers.ExceptionHelpers;
import frc.robot.driver.controltasks.WaitTask;

public class ControlTaskTests
{
    @Test
    public void verifyControlTaskReturnAffectedOperations()
    {
        Set<Class<?>> tasks = null;
        try
        {
            tasks =
                ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().equalsIgnoreCase("frc.robot.driver.controltasks"))
                    .map(clazz -> clazz.load())
                    .filter(clazz -> IControlTask.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers()))
                    .collect(Collectors.toSet());
        }
        catch (Exception e)
        {
            Assertions.fail("Failed to load classes from frc.robot.driver.controltasks package: " + e.getMessage());
        }

        for (Class<?> task : tasks)
        {
            IControlTask controlTask = null;
            for (Constructor<?> constructor : task.getConstructors())
            {
                if (constructor.getParameterCount() == 0)
                {
                    try
                    {
                        controlTask = (IControlTask)constructor.newInstance();
                    }
                    catch (Exception e)
                    {
                        // ignore...
                    }

                    continue;
                }

                Parameter[] parameters = constructor.getParameters();
                Object[] params = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++)
                {
                    Parameter parameter = parameters[i];
                    if (parameter.getType().equals(Integer.class) ||
                        parameter.getType().equals(int.class))
                    {
                        params[i] = 0;
                    }
                    else if (parameter.getType().equals(Long.class) ||
                        parameter.getType().equals(long.class))
                    {
                        params[i] = 0L;
                    }
                    else if (parameter.getType().equals(Double.class) ||
                        parameter.getType().equals(double.class))
                    {
                        params[i] = 0.0;
                    }
                    else if (parameter.getType().equals(Boolean.class) ||
                        parameter.getType().equals(boolean.class))
                    {
                        params[i] = false;
                    }
                    else if (parameter.getType().equals(String.class))
                    {
                        params[i] = "";
                    }
                    else if (parameter.getType().equals(DigitalOperation.class))
                    {
                        params[i] = DigitalOperation.ExampleA;
                    }
                    else if (parameter.getType().equals(AnalogOperation.class))
                    {
                        params[i] = AnalogOperation.ExampleOne;
                    }
                    else if (parameter.getType().isEnum())
                    {
                        params[i] = parameter.getType().getEnumConstants()[0];
                    }
                    else if (parameter.getType().equals(IControlTask.class))
                    {
                        params[i] = new WaitTask(0.0);
                    }
                    else if (parameter.getType().equals(IControlTask.class.arrayType()) ||
                        (parameter.isVarArgs() && parameter.getType().equals(IControlTask.class)))
                    {
                        params[i] = new IControlTask[0];
                    }
                    else
                    {
                        params[i] = null;
                    }

                    System.out.println("Parameter (" + i + "): " + parameter.getType() + ", value: " + params[i]);
                }

                try
                {
                    controlTask = (IControlTask)constructor.newInstance(params);
                }
                catch (Exception e)
                {
                    // ignore...
                }
            }

            if (controlTask == null)
            {
                Assertions.fail("Failed to create instance of task: " + task.getSimpleName() + ", no suitable constructor found.");
                continue;
            }

            EnumSet<AnalogOperation> affectedAnalogOperations = controlTask.getAffectedAnalogOperations();
            EnumSet<DigitalOperation> affectedDigitalOperations = controlTask.getAffectedDigitalOperations();

            Assertions.assertNotNull(affectedAnalogOperations, "Task " + task.getSimpleName() + " returns no affected analog operations.");
            Assertions.assertNotNull(affectedDigitalOperations, "Task " + task.getSimpleName() + " returns no affected digital operations.");

            EnumMap<AnalogOperation, AnalogOperationState> analogMap = new EnumMap<AnalogOperation, AnalogOperationState>(AnalogOperation.class);
            for (AnalogOperation operation : affectedAnalogOperations)
            {
                AnalogOperationState state = new AnalogOperationState(new AnalogOperationDescription(operation));
                state.setIsInterrupted(true);
                analogMap.put(operation, state);
            }

            EnumMap<DigitalOperation, DigitalOperationState> digitalMap = new EnumMap<DigitalOperation, DigitalOperationState>(DigitalOperation.class);
            for (DigitalOperation operation : affectedDigitalOperations)
            {
                DigitalOperationState state = new DigitalOperationState(new DigitalOperationDescription(operation));
                state.setIsInterrupted(true);
                digitalMap.put(operation, state);
            }

            DummyContextModifier contextModifier = new DummyContextModifier(digitalMap, analogMap);

            controlTask.initialize(contextModifier, null);

            try
            {
                controlTask.end();
            }
            catch (Exception e)
            {
                Assertions.fail("Failed to handle task: " + task.getSimpleName() + ", exception: " + ExceptionHelpers.exceptionString(e));
            }
        }
    }

    class DummyContextModifier implements IOperationModifier
    {
        private final EnumMap<DigitalOperation, DigitalOperationState> digitalMap;
        private final EnumMap<AnalogOperation, AnalogOperationState> analogMap;
        public DummyContextModifier(
            EnumMap<DigitalOperation, DigitalOperationState> digitalMap,
            EnumMap<AnalogOperation, AnalogOperationState> analogMap)
        {
            this.digitalMap = digitalMap;
            this.analogMap = analogMap;
        }

        @Override
        public void setContext(OperationContext context)
        {
        }

        @Override
        public boolean getDigital(DigitalOperation digitalOperation)
        {
            DigitalOperationState state = this.digitalMap.get(digitalOperation);
            return state.getState();
        }

        @Override
        public double getAnalog(AnalogOperation analogOperation)
        {
            AnalogOperationState state = this.analogMap.get(analogOperation);
            return state.getState();
        }

        @Override
        public OperationContext getContext()
        {
            return OperationContext.General;
        }

        @Override
        public void setAnalogOperationInterrupt(AnalogOperation operation, boolean interrupted)
        {
            AnalogOperationState operationState = this.analogMap.get(operation);
            operationState.setIsInterrupted(interrupted);
        }

        @Override
        public void setDigitalOperationInterrupt(DigitalOperation operation, boolean interrupted)
        {
            DigitalOperationState operationState = this.digitalMap.get(operation);
            operationState.setIsInterrupted(interrupted);
        }

        @Override
        public void setAnalogOperationValue(AnalogOperation operation, double value)
        {
            AnalogOperationState operationState = this.analogMap.get(operation);
            operationState.setInterruptState(value);
        }

        @Override
        public void setDigitalOperationValue(DigitalOperation operation, boolean value)
        {
            DigitalOperationState operationState = this.digitalMap.get(operation);
            operationState.setInterruptState(value);
        }
    }
}