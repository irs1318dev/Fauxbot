package frc.lib.robotprovider;

/**
 * Represents a Boolean Entry that can be written to/read from Network Tables
 */
public interface IBooleanEntry
{
    /**
     * Retrieve the current value
     * @return current value
     */
    boolean get();

    /**
     * Set the current value of the entry
     * @param value to set
     */
    void set(boolean value);

    /**
     * Set the default value for the entry
     * @param defaultValue to use
     */
    void setDefault(boolean defaultValue);
}
