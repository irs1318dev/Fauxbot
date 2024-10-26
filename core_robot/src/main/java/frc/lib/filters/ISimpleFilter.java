package frc.lib.filters;

/**
 * Interface for a simple filter of values converting a raw value into a filtered value
 */
public interface ISimpleFilter
{
    /**
     * Updates the filter and returns the filtered value
     * @param value raw, without any filtering
     * @return filtered value
     */
    double update(double value);

    /**
     * Retrieve the most recent filtered value
     * @return the filtered value after the last update
     */
    double getValue();

    /**
     * Resets this filter to accomodate a gap in time
     */
    void reset();
}
