package frc.lib.filters;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;

public class SlewRateLimiter
{
    private final double maxPositiveRate;
    private final double maxNegativeRate;
    private final double initialValue;

    private final ITimer timer;

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
        ExceptionHelpers.Assert(maxPositiveRate >= 0.0, "Expect maxPostivieRate to be positive (non-negative)");
        ExceptionHelpers.Assert(maxNegativeRate <= 0.0, "Expect maxNegativeRate to be negative (non-positive)");

        this.timer = timer;
        this.maxNegativeRate = maxNegativeRate;
        this.maxPositiveRate = maxPositiveRate;
        this.initialValue = initialValue;

        this.prevValue = this.initialValue;
        this.prevTime = 0.0;
    }

    /*
     * re-asses the value to use based on a new input
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
                Helpers.EnforceRange(
                    value,
                    this.prevValue + delta * this.maxNegativeRate,
                    this.prevValue + delta * this.maxPositiveRate);
        }

        this.prevValue = newValue;
        this.prevTime = currTime;
        return this.prevValue;
    }

    public void reset()
    {
        this.prevValue = this.initialValue;
        this.prevTime = 0.0;
    }
}
