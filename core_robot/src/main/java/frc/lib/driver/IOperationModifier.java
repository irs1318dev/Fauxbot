package frc.lib.driver;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public interface IOperationModifier extends IContextModifier, IOperationRetriever
{
    /**
     * Sets the operation's interrupted state
     * 
     * @param operation   to set the interrupt state for
     * @param interrupted whether the operation is interrupted
     */
    public void setAnalogOperationInterrupt(AnalogOperation operation, boolean interrupted);

    /**
     * Sets the operation's interrupted state
     * 
     * @param operation   to set the interrupt state for
     * @param interrupted whether the operation is interrupted
     */
    public void setDigitalOperationInterrupt(DigitalOperation operation, boolean interrupted);

    /**
     * Sets the interrupted value for the operation state for a given analog operation to the provided value
     * 
     * @param operation to set the interrupt state for
     * @param value     to set as the interrupt
     */
    public void setAnalogOperationValue(AnalogOperation operation, double value);

    /**
     * Sets the interrupted value for the operation state for a given digital operation to the provided value
     * 
     * @param operation to set the interrupt state for
     * @param value     to set as the interrupt
     */
    public void setDigitalOperationValue(DigitalOperation operation, boolean value);
}
