package frc.lib.robotprovider;

/**
 * Represents a String Entry that can be written to/read from Network Tables
 */
public interface IStringEntry
{
    /**
     * Retrieve the current value
     * @return current value
     */
    String get();

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
