package frc.lib.filters;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;

/**
 * A floating calculator filter, where it will find a filtered value based on the look-back period.
 */
abstract class FloatingCalculatorBase implements ISimpleFilter
{
    protected double filteredValue;

    protected final int totalSamples;
    protected final double[] samples;
    protected final double minValue;
    protected final double maxValue;

    protected double lastRecalcTime;

    private final ITimer timer;

    private final double duration;
    private final double samplesPerSecond;
    protected final double sampleDurationRate; // 1 / (#samples*duration)

    private double prevTime;

    /**
     * Calculator to determine a filtered value across a certain look-back time period
     * @param minValue the min value to accept as an input
     * @param maxValue the max value to accept as an input
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    protected FloatingCalculatorBase(ITimer timer, double minValue, double maxValue, double duration, double samplesPerSecond)
    {
        ExceptionHelpers.Assert(minValue <= maxValue, "Expect minValue %f to be less than maxValue %f", minValue, maxValue);

        this.timer = timer;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.duration = duration;
        this.samplesPerSecond = samplesPerSecond;
        this.sampleDurationRate = 1.0 / (samplesPerSecond * duration);

        this.totalSamples = (int)(this.duration * this.samplesPerSecond);
        this.samples = new double[this.totalSamples];

        this.reset();
    }

    /**
     * Updates the filter and returns the filtered value
     * @param value raw, without any filtering
     * @return filtered value
     */
    public double update(double value)
    {
        double currTime = this.timer.get();

        // ignore sample values that are outside the realistic range
        if (value < this.minValue)
        {
            value = this.minValue;
        }
        else if (value > this.maxValue)
        {
            value = this.maxValue;
        }

        int prevIndex = (int)(this.prevTime * this.samplesPerSecond) % this.totalSamples;
        int currIndex = (int)(currTime * this.samplesPerSecond) % this.totalSamples;

        int slots = currIndex - prevIndex + 1;
        if (slots < 0)
        {
            // wrap around the end of the samples array
            slots += this.totalSamples;
        }

        if (this.prevTime < 0.0)
        {
            if (this.filteredValue != 0.0)
            {
                this.reset();
            }

            prevIndex = 0;
            currIndex = 0;
            slots = this.totalSamples + 1;
        }

        for (int i = 1; i < slots; i++)
        {
            int index = (prevIndex + i) % this.totalSamples;
            this.filteredValue = this.updateValue(this.samples[index], value);
            this.samples[index] = value;
        }

        if (this.shouldRecalculate(currTime))
        {
            this.filteredValue = this.recalculateValue();
            this.lastRecalcTime = currTime;
        }

        // Accommodate -0.0, which is less than 0.0, before asserting we are within the valid range
        if (this.minValue == 0.0 && this.filteredValue < 0.0 && this.filteredValue > -0.00001)
        {
            this.filteredValue = 0.0;
        }

        ExceptionHelpers.Assert(Helpers.withinRange(this.filteredValue, this.minValue, this.maxValue), "How was our floating average (mean) outside our supported value range? %f between [%f, %f]", this.filteredValue, this.minValue, this.maxValue);

        this.prevTime = currTime;

        return this.filteredValue;
    }

    protected abstract double updateValue(double oldValue, double newValue);
    protected abstract boolean shouldRecalculate(double currTime);
    protected abstract double recalculateValue();

    /**
     * Retrieve the most recent filtered value
     * @return the filtered value after the last update
     */
    public double getValue()
    {
        return this.filteredValue;
    }

    /**
     * Resets this filter to accomodate a gap in time
     */
    public void reset()
    {
        this.lastRecalcTime = -1.0;
        this.prevTime = -1.0;
        this.filteredValue = 0.0;
        for (int i = 0; i < this.totalSamples; i++)
        {
            this.samples[i] = 0.0;
        }
    }

    // for testing:
    // public void setValue(double value)
    // {
    //     this.filteredValue = value;
    // }
}
