package frc.lib.filters;

public interface ISimpleFilter
{
    /*
     * re-asses the value to use based on a new input
     */
    double update(double value);
    double getValue();
    void reset();
}
