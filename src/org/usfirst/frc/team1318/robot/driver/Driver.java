package org.usfirst.frc.team1318.robot.driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.driver.descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.driver.states.AnalogOperationState;
import org.usfirst.frc.team1318.robot.driver.states.DigitalOperationState;
import org.usfirst.frc.team1318.robot.driver.states.OperationState;

import com.google.inject.Injector;

/**
 * Driver that represents something that operates the robot.  This is either autonomous or teleop/user driver.
 *
 */
public abstract class Driver
{
    protected final Injector injector;
    protected final Map<Operation, OperationState> operationStateMap;

    /**
     * Initializes a new Driver
     */
    protected Driver(Injector injector, IButtonMap buttonMap)
    {
        this.injector = injector;

        Map<Operation, OperationDescription> operationSchema = buttonMap.getOperationSchema();
        this.operationStateMap = new HashMap<Operation, OperationState>(operationSchema.size());
        for (Operation operation : operationSchema.keySet())
        {
            this.operationStateMap.put(operation, OperationState.createFromDescription(operationSchema.get(operation)));
        }
    }

    /**
     * Tell the driver that some time has passed
     */
    public abstract void update();

    /**
     * Tell the driver that operation is stopping
     */
    public abstract void stop();

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(Operation digitalOperation)
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
    public double getAnalog(Operation analogOperation)
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
