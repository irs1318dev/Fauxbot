package frc.lib.filters;

/**
 * A fading memory filter, where the filter will somewhat remember past values and gradually settle closer to the raw value
 * This is similar to WPILib's "LinearFilter" class.
 */
public class FadingMemoryFilter implements ISimpleFilter
{
    private final double kO;
    private final double kN;

    private double currentValue;

    /**
     * Initializes a new instance of the FadingMemoryFilter class.
     * @param kO the ratio to use for the old value
     * @param kN the ratio to use for the new value
     */
    public FadingMemoryFilter(double kO, double kN)
    {
        this(kO, kN, 0.0);
    }

    /**
     * Initializes a new instance of the FadingMemoryFilter class.
     * @param kO the ratio to use for the old value
     * @param kN the ratio to use for the new value
     * @param startingValue the starting value to use
     */
    public FadingMemoryFilter(double kO, double kN, double startingValue)
    {
        this.kO = kO;
        this.kN = kN;

        this.currentValue = startingValue;
    }

    /**
     * Updates the filter and returns the filtered value
     * @param value raw, without any filtering
     * @return filtered value
     */
    public double update(double value)
    {
        if (!Double.isNaN(this.currentValue))
        {
            this.currentValue = this.currentValue * this.kO + value * this.kN;
        }
        else
        {
            this.currentValue = value * this.kN;
        }

        return this.currentValue;
    }

    /**
     * Retrieve the most recent filtered value
     * @return the filtered value after the last update
     */
    public double getValue()
    {
        return this.currentValue;
    }

    /**
     * Resets this filter to accomodate a gap in time
     */
    public void reset()
    {
        this.currentValue = 0.0;
    }
}
