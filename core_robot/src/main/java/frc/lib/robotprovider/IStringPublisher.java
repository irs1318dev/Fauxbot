package frc.lib.robotprovider;

/**
 * Represents a String publisher that can write to Network Tables
 */
public interface IStringPublisher
{
    /**
     * Set the current value of the entry
     * @param value to set
     */
    void set(String value);

    /**
     * Set the default value for the entry
     * @param defaultValue to use
     */
    void setDefault(String defaultValue);
}
