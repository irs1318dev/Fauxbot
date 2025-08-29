package frc.lib.filters;

/**
 * Provides a simple threshold that will maintain the current value until the value has been consistently different for the certain number of times.
 * Similar to the WPILib "Debouncer", but uses a count threshold (update cycles, typically 20ms each) instead of a time-based threshold.
 */
public class BooleanThresholdFilter
{
    private int updateThreshold;

    private int updates;
    private boolean currentValue;

    /**
     * Initializes a new instance of the BooleanThresholdFilter class.
     * @param threshold number of times the value must be different before changing
     */
    public BooleanThresholdFilter(int threshold)
    {
        this.updateThreshold = threshold;
        this.currentValue = false;
    }

    /**
     * Updates the filter and returns the filtered value
     * @param value without any filtering
     * @return filtered value
     */
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

    /**
     * Change the threhold to use for this filter going forward
     * @param threshold to use
     */
    public void setThreshold(int threshold)
    {
        this.updateThreshold = threshold;
    }
}
