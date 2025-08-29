package frc.lib.filters;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;

/**
 * A slew-rate limiting filter, where it will adjust the output value based on the constraints so that the value doesn't increase/decrease at too fast a rate
 */
public class SlewRateLimiter implements ISimpleFilter
{
    private final ITimer timer;

    private final double initialValue;
    private double maxPositiveRate;
    private double maxNegativeRate;

    private double prevTime;
    private double prevValue;

    /**
     * Rate limiter to avoid a value increasing or decreasing at too fast a rate
     * @param timer to calculate change between updates
     * @param maxNegativeRate to determine how to cap value decreases between updates
     * @param maxPositiveRate to determine how to cap value increases between updates
     * @param initialValue to use when starting/resetting
     */
    public SlewRateLimiter(ITimer timer, double maxNegativeRate, double maxPositiveRate, double initialValue)
    {
        ExceptionHelpers.Assert(maxPositiveRate >= 0.0, "Expect maxPostivieRate to be positive (non-negative): %f", maxPositiveRate);
        ExceptionHelpers.Assert(maxNegativeRate <= 0.0, "Expect maxNegativeRate to be negative (non-positive): %f", maxNegativeRate);

        this.timer = timer;
        this.initialValue = initialValue;

        this.maxNegativeRate = maxNegativeRate;
        this.maxPositiveRate = maxPositiveRate;

        this.prevValue = this.initialValue;
        this.prevTime = 0.0;
    }

    /**
     * Updates the filter and returns the filtered value
     * @param value raw, without any filtering
     * @return filtered value
     */
    public double update(double value)
    {
        double currTime = this.timer.get();
        double delta = currTime - this.prevTime;

        double newValue;
        if (Double.isNaN(this.prevValue))
        {
            newValue = value;
        }
        else
        {
            newValue =
                Helpers.enforceRange(
                    value,
                    this.prevValue + delta * this.maxNegativeRate,
                    this.prevValue + delta * this.maxPositiveRate);
        }

        this.prevValue = newValue;
        this.prevTime = currTime;
        return this.prevValue;
    }

    /**
     * Retrieve the most recent filtered value
     * @return the filtered value after the last update
     */
    public double getValue()
    {
        return this.prevValue;
    }

    /**
     * Resets this filter to accomodate a gap in time
     */
    public void reset()
    {
        this.prevValue = this.initialValue;
        this.prevTime = 0.0;
    }

    /**
     * Updates the limiter to use the newly-provided rates
     * @param maxNegativeRate to determine how to cap value decreases between updates
     * @param maxPositiveRate to determine how to cap value increases between updates
     */
    public void configureRate(double maxNegativeRate, double maxPositiveRate)
    {
        ExceptionHelpers.Assert(maxPositiveRate >= 0.0, "Expect maxPostivieRate to be positive (non-negative): %f", maxPositiveRate);
        ExceptionHelpers.Assert(maxNegativeRate <= 0.0, "Expect maxNegativeRate to be negative (non-positive): %f", maxNegativeRate);

        this.maxNegativeRate = maxNegativeRate;
        this.maxPositiveRate = maxPositiveRate;
    }
}
