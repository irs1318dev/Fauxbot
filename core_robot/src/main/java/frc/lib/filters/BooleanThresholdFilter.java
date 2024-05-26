package frc.lib.filters;

public class BooleanThresholdFilter
{
    private int updateThreshold;

    private int updates;
    private boolean currentValue;

    public BooleanThresholdFilter(int threshold)
    {
        this.updateThreshold = threshold;
        this.currentValue = false;
    }

    public boolean update(boolean value)
    {
        if (this.currentValue == value)
        {
            this.updates = 0;
        }
        else if (this.updates >= this.updateThreshold)
        {
            this.currentValue = value;
            this.updates = 0;
        }
        else
        {
            this.updates++;
        }

        return this.currentValue;
    }

    public void setThreshold(int threshold)
    {
        this.updateThreshold = threshold;
    }
}
