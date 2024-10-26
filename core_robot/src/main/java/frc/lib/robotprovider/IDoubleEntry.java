package frc.lib.robotprovider;

/**
 * Represents a Double Entry that can be written to/read from Network Tables
 */
public interface IDoubleEntry
{
    /**
     * Retrieve the current value
     * @return current value
     */
    double get();

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
