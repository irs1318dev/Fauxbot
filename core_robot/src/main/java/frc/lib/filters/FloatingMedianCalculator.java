package frc.lib.filters;

import java.util.Arrays;

import frc.lib.robotprovider.ITimer;

/**
 * A floating-median calculator filter, where it will find the median value over the look-back period to return the filtered value
 */
public class FloatingMedianCalculator extends FloatingCalculatorBase implements ISimpleFilter
{
    protected final double[] sortedSamples;

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingMedianCalculator(ITimer timer, double duration, double samplesPerSecond)
    {
        this(timer, -Double.MAX_VALUE, Double.MAX_VALUE, duration, samplesPerSecond);
    }

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param minValue the max value to accept as an input
     * @param maxValue the max value to accept as an input
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingMedianCalculator(ITimer timer, double minValue, double maxValue, double duration, double samplesPerSecond)
    {
        super(timer, minValue, maxValue, duration, samplesPerSecond);

        this.sortedSamples = new double[this.totalSamples];
    }

    @Override
    protected double updateValue(double oldValue, double newValue)
    {
        return this.filteredValue;
    }

    @Override
    protected boolean shouldRecalculate(double currTime)
    {
        return true;
    }

    @Override
    protected double recalculateValue()
    {
        // this isn't terribly efficient.  There's probably a more efficient algorithm for updating median besides this and the "needsRecalculation" optimization
        System.arraycopy(this.samples, 0, this.sortedSamples, 0, this.totalSamples);
        Arrays.sort(this.sortedSamples);

        if (this.totalSamples % 2 == 0)
        {
            return (this.sortedSamples[this.totalSamples / 2] + this.sortedSamples[this.totalSamples / 2 - 1]) / 2.0;
        }
        else
        {
            return this.sortedSamples[this.totalSamples / 2];
        }
    }
}
