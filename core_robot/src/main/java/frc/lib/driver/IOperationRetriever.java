package frc.lib.driver;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.OperationContext;

public interface IOperationRetriever
{
    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * 
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(DigitalOperation digitalOperation);

    /**
     * Get a double between -1.0 and 1.0 indicating the current value of the analog operation
     * 
     * @param analogOperation to get
     * @return the current value of the analog operation
     */
    public double getAnalog(AnalogOperation analogOperation);

    /**
     * Retrieves the current operation context.
     * 
     * @returns the current operation context
     */
    public OperationContext getContext();
}
