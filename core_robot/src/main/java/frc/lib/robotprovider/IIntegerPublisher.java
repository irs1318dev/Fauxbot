package frc.lib.robotprovider;

/**
 * Represents a Integer publisher that can write to Network Tables
 */
public interface IIntegerPublisher
{
    /**
     * Set the current value of the entry
     * @param value to set
     */
    void set(long value);

    /**
     * Set the default value for the entry
     * @param defaultValue to use
     */
    void setDefault(long defaultValue);
}
