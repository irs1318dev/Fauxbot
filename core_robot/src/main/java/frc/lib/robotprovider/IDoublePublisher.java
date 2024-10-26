package frc.lib.robotprovider;

/**
 * Represents a Double publisher that can write to Network Tables
 */
public interface IDoublePublisher
{
    /**
     * Set the current value of the entry
     * @param value to set
     */
    void set(double value);

    /**
     * Set the default value for the entry
     * @param defaultValue to use
     */
    void setDefault(double defaultValue);
}
