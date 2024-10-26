package frc.lib.filters;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;
import frc.lib.robotprovider.ITimer;

/**
 * A floating-average calculator filter, where it will collect the weighted-average of the raw value over the look-back period to return the filtered value
 */
public class FloatingAverageCalculator implements ISimpleFilter
{
    private static final double RECALC_PERIOD = 1.0;
    private final ITimer timer;

    private final double maxValue;
    private final double duration;
    private final double samplesPerSecond;
    private final double sampleDurationRate; // 1 / (#samples*duration)

    private final int totalSamples;
    private final double[] samples;

    private double lastRecalcTime;
    private double prevTime;
    private double floatingAverage;

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingAverageCalculator(ITimer timer, double duration, double samplesPerSecond)
    {
        this(timer, Double.MAX_VALUE, duration, samplesPerSecond);
    }

    /**
     * Average calculator to determine the mean value across a certain look-back time period
     * @param maxValue the max value to accept as an input
     * @param timer to calculate elapsed time between updates
     * @param duration over which to maintain the average
     * @param samplesPerSecond number of samples to keep per second
     */
    public FloatingAverageCalculator(ITimer timer, double maxValue, double duration, double samplesPerSecond)
    {
        this.timer = timer;
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

        // ignore sample values that are too high to be realistic
        if (!Helpers.WithinRange(value, -this.maxValue, this.maxValue))
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
            if (this.floatingAverage != 0.0)
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
            this.floatingAverage += (value - this.samples[index]) * this.sampleDurationRate;
            this.samples[index] = value;
        }

        if (!Helpers.WithinRange(this.floatingAverage, -this.maxValue, this.maxValue) ||
            currTime >= this.lastRecalcTime + FloatingAverageCalculator.RECALC_PERIOD)
        {
            ExceptionHelpers.Assert(Helpers.WithinRange(this.floatingAverage, -this.maxValue, this.maxValue), "How was our floating average above max value? %f", this.floatingAverage);

            // not sure how this was possible.  Let's recalculate
            double total = 0.0;
            for (double sample : this.samples)
            {
                total += sample;
            }

            this.floatingAverage = total * this.sampleDurationRate;
            this.lastRecalcTime = currTime;
        }

        this.prevTime = currTime;

        return this.floatingAverage;
    }

    /**
     * Retrieve the most recent filtered value
     * @return the filtered value after the last update
     */
    public double getValue()
    {
        return this.floatingAverage;
    }

    /**
     * Resets this filter to accomodate a gap in time
     */
    public void reset()
    {
        this.lastRecalcTime = -1.0;
        this.prevTime = -1.0;
        this.floatingAverage = 0.0;
        for (int i = 0; i < this.totalSamples; i++)
        {
            this.samples[i] = 0.0;
        }
    }

    // for testing:
    // public void setValue(double value)
    // {
    //     this.floatingAverage = value;
    // }
}
