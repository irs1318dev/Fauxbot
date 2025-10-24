package frc.lib.filters;

import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;

/**
 * A floating-average calculator filter, where it will collect the weighted-average of the raw value over the look-back period to return the filtered value
 */
public class FloatingAverageCalculator extends FloatingCalculatorBase implements ISimpleFilter
{
    private static final double RECALC_PERIOD = 1.0;

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingAverageCalculator(ITimer timer, double duration, double samplesPerSecond)
    {
        this(timer, -Double.MAX_VALUE, Double.MAX_VALUE, duration, samplesPerSecond);
    }

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param maxValue the max value to accept as an input
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingAverageCalculator(ITimer timer, double minValue, double maxValue, double duration, double samplesPerSecond)
    {
        super(timer, minValue, maxValue, duration, samplesPerSecond);
    }

    @Override
    protected double updateValue(double oldValue, double newValue)
    {
        return this.filteredValue + (newValue - oldValue) * this.sampleDurationRate;
    }

    @Override
    protected boolean shouldRecalculate(double currTime)
    {
        return !Helpers.withinRange(this.filteredValue, this.minValue, this.maxValue) ||
            currTime >= this.lastRecalcTime + FloatingAverageCalculator.RECALC_PERIOD;
    }

    @Override
    protected double recalculateValue()
    {
        double total = 0.0;
        for (double sample : this.samples)
        {
            total += sample;
        }

        return total * this.sampleDurationRate;
    }
}
