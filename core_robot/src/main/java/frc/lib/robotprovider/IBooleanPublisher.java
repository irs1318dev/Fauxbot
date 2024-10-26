package frc.lib.robotprovider;

/**
 * Represents a Boolean publisher that can write to Network Tables
 */
public interface IBooleanPublisher
{
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
