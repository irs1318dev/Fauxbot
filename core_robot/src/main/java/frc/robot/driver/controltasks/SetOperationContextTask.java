package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.OperationContext;

public final class SetOperationContextTask extends UpdateCycleTask
{
    private final OperationContext context;

    public SetOperationContextTask(OperationContext context)
    {
        super(1);

        this.context = context;
    }

    /**
     * Retrieve the set of analog operations that this task affects.
     * 
     * @return set of analog operations that this task affects.
     */
    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations()
    {
        return EnumSet.noneOf(AnalogOperation.class);
    }

    /**
     * Retrieve the set of digital operations that this task affects.
     * 
     * @return set of digital operations that this task affects.
     */
    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations()
    {
        return EnumSet.noneOf(DigitalOperation.class);
    }

    @Override
    public void begin()
    {
        super.begin();

        this.setOperationContext(this.context);
    }
}
