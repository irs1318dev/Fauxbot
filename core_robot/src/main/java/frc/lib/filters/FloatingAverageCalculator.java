package frc.lib.filters;

import frc.lib.robotprovider.ITimer;

public class FloatingAverageCalculator
{
    private final ITimer timer;

    private final double duration;
    private final double samplesPerSecond;
    private final double sampleDuration;

    private final int totalSamples;
    private final double[] samples;

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
        this.timer = timer;
        this.duration = duration;
        this.samplesPerSecond = samplesPerSecond;
        this.sampleDuration = 1.0 / samplesPerSecond;

        this.totalSamples = (int)(this.duration * this.samplesPerSecond);
        this.samples = new double[this.totalSamples];
    }

    /*
     * Provides a sample to add to the array
     */
    public double update(double value)
    {
        double currTime = this.timer.get();

        int prevIndex = (int)(this.prevTime * this.samplesPerSecond) % this.totalSamples;
        int currIndex = (int)(currTime * this.samplesPerSecond) % this.totalSamples;

        int slots = currIndex - prevIndex + 1;
        if (slots < 0)
        {
            slots += this.totalSamples;
        }

        for (int i = 1; i < slots; i++)
        {
            int index = (prevIndex + i) % this.totalSamples;
            this.floatingAverage += ((value - this.samples[index]) * this.sampleDuration) / this.duration;
            this.samples[index] = value;
        }

        this.prevTime = currTime;

        return this.floatingAverage;
    }

    public void reset()
    {
        this.floatingAverage = 0.0;
        for (int i = 0; i < this.totalSamples; i++)
        {
            this.samples[i] = 0.0;
        }
    }
}
