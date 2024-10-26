package frc.lib.robotprovider;

/**
 * Represents a Integer Entry that can be written to/read from Network Tables
 */
public interface IIntegerEntry
{
    /**
     * Retrieve the current value
     * @return current value
     */
    long get();

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
