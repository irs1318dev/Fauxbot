package frc.lib.driver;

import frc.robot.driver.OperationContext;

public interface IContextModifier
{
    /**
     * Updates the driver to be in the specified context.
     * @param context to apply
     */
    public void setContext(OperationContext context);
}
